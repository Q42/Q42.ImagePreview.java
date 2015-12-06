package com.q42.imagepreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public final class ImagePreview {

    private static final byte[] SOF0PATTERN = {(byte) 0xFF, (byte) 0xC0};
    private static byte[] HEADER = null;

    /**
     * Private constructor
     * There is no need for this class to be extended or instantiated from the outside
     */
    private ImagePreview() {}

    /**
     * Returns an Android Drawable object gained from inserting the header into this
     * static class using the function setHeader(String) and inserting the body
     * into this function.
     *
     * @param body       {String} the Base64 encoded body of the image
     * @param context    {Context} the context from which to create a drawable
     * @param blurRadius {Float} Optional blur amount
     * @return {Drawable} The Android Drawable containing the image
     * @throws IOException
     */
    @SuppressLint("NewApi")
    public static Drawable drawableFromString(@NonNull String body, Context context, Float blurRadius) throws IOException, IllegalArgumentException {

        final Bitmap preview = bitmapFromString(body);

        if(preview == null) {
            return null;
        }

        if (blurRadius != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {

            final Bitmap blurred = Bitmap.createBitmap(preview);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpOut = Allocation.createFromBitmap(rs, blurred);

            theIntrinsic.setRadius(blurRadius);
            theIntrinsic.setInput(Allocation.createFromBitmap(rs, preview));
            theIntrinsic.forEach(tmpOut);

            tmpOut.copyTo(blurred);

            return new BitmapDrawable(context.getResources(), blurred);
        }

        return new BitmapDrawable(context.getResources(), preview);
    }

    public static Drawable drawableFromString(@NonNull String body, Context context) throws IOException, IllegalArgumentException {
        return drawableFromString(body, context, null);
    }

    /**
     * Returns an Android Bitmap object gained from inserting the header into this
     * static class using the function setHeader(String) and inserting the body
     * into this function.
     *
     * @param body {String} the Base64 encoded body of the image
     * @return {Bitmap} The Android Bitmap containing the image
     * @throws IOException
     */
    public static Bitmap bitmapFromString(@NonNull String body) throws IOException, IllegalArgumentException {

        final byte[] byteImage = byteImageFromBody(body);
        if (byteImage == null) {
            return null;
        }

        return BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
    }

    /**
     * Insert the Base64 encoded body of the image and get back the base64 encoded
     * image including the header.
     *
     * @param inputBody {String} The body of the image base64 encoded
     * @return {byte[]} The image base64 encoded with the header included or null if something failed
     * @throws IOException
     */
    public static byte[] byteImageFromBody(@NonNull String inputBody) throws IOException, IllegalArgumentException {

        if (HEADER == null || inputBody == null) {
            return null;
        }

        final int indexC0 = ImagePreview.indexOf(HEADER, SOF0PATTERN);
        if (indexC0 == -1) {
            return null;
        }

        final int headerSizeIndexStart = indexC0 + 5;
        final byte[] body = Base64.decode(inputBody, Base64.NO_WRAP);

        if(body == null || body.length < 5) {
            return null;
        }

        final ByteArrayOutputStream finalArray = new ByteArrayOutputStream();

        finalArray.write(Arrays.copyOfRange(getHeader(), 0, headerSizeIndexStart));
        finalArray.write(Arrays.copyOfRange(body, 1, 5));
        finalArray.write(Arrays.copyOfRange(getHeader(), headerSizeIndexStart, getHeader().length));
        finalArray.write(Arrays.copyOfRange(body, 5, body.length));

        return finalArray.toByteArray();
    }

    /**
     * Get the base64 decoded header string previously set
     * May return null if no header is set yet.
     *
     * @return {byte[]} the header as a byte array
     */
    public static byte[] getHeader() {
        return HEADER;
    }

    /**
     * Set the header which is then decoded into a
     * Base64 byte array used to parse the image eventually
     *
     * @param header {String} the still encoded Base64 string image header
     */
    public static void setHeader(@NonNull String header) {
        HEADER = Base64.decode(header, Base64.NO_WRAP);
    }

    /**
     * Function found on the interwebs: http://stackoverflow.com/a/21341168
     * Used to find the index of a byte array in a byte array.
     *
     * @param outerArray   {byte[]} The array to search in
     * @param smallerArray {byte[]} The array to search for
     * @return The position or -1 if not found
     */
    public static int indexOf(byte[] outerArray, byte[] smallerArray) {

        if (outerArray == null || smallerArray == null || outerArray.length == 0 ||
                smallerArray.length == 0 || outerArray.length < smallerArray.length) {
            // Guard against input that might throw  nasty runtime exceptions!
            return -1;
        }

        for (int i = 0; i < outerArray.length - smallerArray.length + 1; ++i) {
            boolean found = true;
            for (int j = 0; j < smallerArray.length; ++j) {
                if (outerArray[i + j] != smallerArray[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }

        return -1;
    }
}

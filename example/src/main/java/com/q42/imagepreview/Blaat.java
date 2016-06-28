package com.q42.imagepreview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by thijs on 28-06-16.
 */
public class Blaat {
    void test() {
        Bitmap bitmap = ImagePreview.previewBitmap(context, "Your Base64 encoded preview");
        Drawable drawable = ImagePreview.previewDrawable(context, "Your Base64 encoded preview");
    }
}


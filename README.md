# ImagePreview

This is the client library for rendering the image previews.

Look at [this blog post](http://q42.com/blog/post/133591843068/imagepreview-library) for more details.

You can find the [.NET server component on Github](https://github.com/Q42/Q42.ImagePreview.swift).

[![Build Status][travis-image]][travis-url][![Build version][bintray-image]][bintray-url]

## Installation

ImagePreview is available through jCenter. To install it, simply add the following line to your build.gradle dependencies:

```
compile 'com.q42:image-preview:0.1.0'
```

## Usage

### Getting and setting a header

```java
if (ImagePreview.getHeader() == null) {
    ImagePreview.setHeader("YOUR STRING HEADER HERE");
}
```

### Getting a (blurry) drawable

```java
// With 2f (float) amount of blur
ImagePreview.drawableFromString("YOUR IMAGE STRING", context, 2f);
// or without blur
ImagePreview.drawableFromString("YOUR IMAGE STRING", context);
```

### Getting the clean bitmap

There is no blurring going on here. Furthermore, this method is used internally for the (blurry) drawable.
But if you wish to get the freshly generated bitmap before any processing, then here you go.

```java
ImagePreview.bitmapFromString("YOUR IMAGE STRING");
```

## Example

This is one way to go about and use it. 

```java
    private Drawable getPreviewDrawable(String imageThumb) throws IOException {

        if (ImagePreview.getHeader() == null) {
            String imageThumbHeader = getContext().getString(R.string.preview_image_header);
            ImagePreview.setHeader(imageThumbHeader);
        }
        return ImagePreview.drawableFromString(imageThumb, context, 2f);
    }
```

## Authors

* Tim van Steenis, tims@q42.nl
* Jimmy Aupperlee, jimmy@q42.nl (port to Android)

## License

ImagePreview is available under the MIT license. See the LICENSE file for more info.

[travis-url]: https://travis-ci.org/Q42/Q42.ImagePreview.java
[travis-image]: http://img.shields.io/travis/Q42/Q42.ImagePreview.java.svg
[bintray-url]: https://bintray.com/shapoc/maven/image-preview/view
[bintray-image]: https://img.shields.io/bintray/v/shapoc/maven/image-preview.svg
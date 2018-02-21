# ImagePreview

This is the client library for rendering the image previews.

Look at [this blog post](https://blog.q42.nl/the-imagepreview-library-render-previews-with-only-200-bytes-of-image-data-312fb281d081) for more details.

You can find the [.NET server component on Github](https://github.com/Q42/Q42.ImagePreview.swift).

## Installation

ImagePreview is available through jitpack. To install follow instructions here: https://jitpack.io/#Q42/Q42.ImagePreview.java

## Usage

### Kotlin
You can call a extension method on the `Context`
```kotlin
val bitmap = previewBitmap("Your Base64 encoded preview")
image_view.setImageBitmap(bitmap)
```
or 
```kotlin
val drawable = previewDrawable("Your Base64 encoded preview")
```

*These methods will never fail. If anything goes wrong they return `null`*

### Java
In Java you can call
```
Bitmap bitmap = ImagePreview.previewBitmap(context, "Your Base64 encoded preview");
Drawable drawable = ImagePreview.previewDrawable(context, "Your Base64 encoded preview");
```

## Authors
* Thijs Suijten, thijs@q42.nl (port to Android - Kotlin)
* Tim van Steenis, tims@q42.nl
* Jimmy Aupperlee, jimmy@q42.nl (port to Android)

## License

ImagePreview is available under the MIT license. See the LICENSE file for more info.

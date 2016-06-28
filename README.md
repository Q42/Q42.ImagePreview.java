# ImagePreview

This is the client library for rendering the image previews.

Look at [this blog post](http://q42.com/blog/post/133591843068/imagepreview-library) for more details.

You can find the [.NET server component on Github](https://github.com/Q42/Q42.ImagePreview.swift).

[![Build Status][travis-image]][travis-url][![Build version][bintray-image]][bintray-url]

## Installation

ImagePreview is available through jCenter. To install it, simply add the following line to your build.gradle dependencies:

```
compile 'com.q42:image-preview:0.2.0'
```

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

[travis-url]: https://travis-ci.org/Q42/Q42.ImagePreview.java
[travis-image]: http://img.shields.io/travis/Q42/Q42.ImagePreview.java.svg
[bintray-url]: https://bintray.com/shapoc/maven/image-preview/view
[bintray-image]: https://img.shields.io/bintray/v/shapoc/maven/image-preview.svg
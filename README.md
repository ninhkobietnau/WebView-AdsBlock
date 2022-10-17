# WebView-AdsBlock
Block ads in WebView easily for Android

## How do I use it?
### Step 1

Add the JitPack repository to your build.gradle(project level) file at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
 ```

### Step 2

Add the dependency in build.gradle(app level) file:

```gradle
dependencies {
    implementation 'com.github.ninhkobietnau:WebView-AdsBlock:1.0.0'
}
```

### Step 3

Initialize the AdsBlock in your activity or fragment where you create the WebView.

```kotlin
AdsBlock.instance.initAdsBlock(this)
```

### Step 4

Extend WebViewClientAdsBlock() for your WebView like this:

```kotlin
webView.webViewClient = object : WebViewClientAdsBlock() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }
    
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }
}
```


Now you can run your app to see if the ads are blocked. You can always use other WebView's features.


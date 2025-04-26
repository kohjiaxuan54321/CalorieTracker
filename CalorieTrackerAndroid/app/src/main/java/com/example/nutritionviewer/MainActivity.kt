package com.example.nutritionviewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.nutritionviewer.ui.theme.NutritionViewerTheme

class MainActivity : ComponentActivity() {
    // will hold the callback from WebView when it wants a file
    private var uploadMessage: ValueCallback<Array<Uri>>? = null

    // launcher for Android's file picker
    private val pickFiles =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val uris = result.data?.clipData?.let { clip ->
                Array(clip.itemCount) { i -> clip.getItemAt(i).uri }
            } ?: result.data?.data?.let { arrayOf(it) }

            uploadMessage?.onReceiveValue(uris)
            uploadMessage = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NutritionViewerTheme {
                WebViewScreen("http://34.46.78.153:8000/")  // use your Mac’s LAN IP
            }
        }
    }

    @Composable
    fun WebViewScreen(url: String) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.allowFileAccess = true
                    settings.allowContentAccess = true

                    webViewClient = WebViewClient()  // keep navigation in-app

                    webChromeClient = object : WebChromeClient() {
                        // Android 5.0+ file chooser
                        override fun onShowFileChooser(
                            webView: WebView?,
                            filePathCallback: ValueCallback<Array<Uri>>?,
                            fileChooserParams: FileChooserParams?
                        ): Boolean {
                            // hold onto the callback
                            uploadMessage = filePathCallback
                            // fire Android's picker for images (camera/gallery)
                            val intent = fileChooserParams?.createIntent()?.apply {
                                // optionally limit to images:
                                type = "image/*"
                                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            }
                            pickFiles.launch(intent)
                            return true
                        }
                    }

                    loadUrl(url)
                }
            },
            update = { it.loadUrl(url) },
            modifier = Modifier.fillMaxSize()
        )
    }
}
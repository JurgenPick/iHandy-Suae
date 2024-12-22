package com.nssus.ihandy.ui.basecomposable

import android.graphics.Bitmap
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import com.nssus.ihandy.R
import com.nssus.ihandy.ui.theme.ButtonSky
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.SilverGray

@Composable
fun DisplayWebViewButton(
    @StringRes buttonTextId: Int = R.string.common_open_web_view_button,
    url: String = "https://www.google.co.th"
){
    var showDialog by remember { mutableStateOf(false) }

    BaseButton(
        text = stringResource(id = buttonTextId),
        onButtonClick = { showDialog = true }
    )

    if (showDialog) {
        FullScreenWebViewDialog(
            url = url,
            onDismiss = { showDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenWebViewDialog(url: String, onDismiss: () -> Unit) {
    var webView: WebView? = remember { null }
    var canGoBack by remember { mutableStateOf(false) }
    var currentUrl by remember { mutableStateOf(url) }
    var currentTitle by remember { mutableStateOf("") } // State for the title

    // Track changes to `canGoBack` when navigating in WebView
    LaunchedEffect(webView) {
        while (true) {
            canGoBack = webView?.canGoBack() == true
            kotlinx.coroutines.delay(100) // Polling for back state changes
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
//        onDismissRequest = { if (canGoBack) webView?.goBack() else onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // WebView Controller Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SilverGray),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back and Refresh Button
//                Row {
//                    if (canGoBack) {
//                        IconButton(
//                            modifier = Modifier.size(36.dp),
//                            onClick = { webView?.goBack() },
//                            enabled = canGoBack
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.KeyboardArrowLeft,
//                                contentDescription = "Back Icon",
//                                tint = Color.Black
//                            )
//                        }
//                    }
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = { webView?.reload() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Icon",
                            tint = Color.Black
                        )
                    }
//                }
                // Title and Url name
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentTitle, // Display the title here
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = FontStyles.txt14,
                        fontWeight = FontWeight.Bold
                    )
//                    Text(
//                        text = currentUrl, // Display the url here
//                        overflow = TextOverflow.Ellipsis,
//                        maxLines = 1,
//                        style = FontStyles.txt14,
//                        color = MainGray
//                    )
                }
                // Close WebView Dialog Button
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )
                }
            }
            // WebView Content
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webView = this

                        settings.javaScriptEnabled = true
                        settings.builtInZoomControls = true
                        settings.cacheMode = WebSettings.LOAD_DEFAULT

                        // Custom WebViewClient to track URL and title changes
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                currentUrl = url ?: ""
                                currentTitle = "Loading..."
                            }

                            override fun onPageFinished(view: WebView, url: String?) {
                                super.onPageFinished(view, url)
                                // Fetch the title using JavaScript
                                view.evaluateJavascript("document.title") { title ->
                                    currentTitle = title.trim('"') // Remove surrounding quotes
                                }
                            }
                        }

                        loadUrl(url)
                    }
                },
                update = { webViewInstance ->
                    webView = webViewInstance
                }
            )
        }
    }

}

@Composable
fun DisplayWebViewFloatingButton(
    @StringRes buttonTextId: Int = R.string.shipping_plant_2_title,
    url: String = "http://10.1.4.20:10064/thge21_web/yardhandy/thgeees11/thgeees11init"
){
    var showDialog by remember { mutableStateOf(false) }

    FloatingActionButton(
        modifier = Modifier.offset(x = (-20).dp, y = (-15).dp),
        onClick = { showDialog = true },
        containerColor = ButtonSky
    ) {
        Text(

            text = stringResource(id = buttonTextId),
            style = FontStyles.txt20,
            color = Color.White
        )
    }

    if (showDialog) {
        FullScreenWebViewDialog(
            url = url,
            onDismiss = { showDialog = false }
        )
    }


}
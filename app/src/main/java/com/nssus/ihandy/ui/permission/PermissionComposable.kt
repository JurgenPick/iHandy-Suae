package com.nssus.ihandy.ui.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

@Composable
fun CheckAndRequestPermission(
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val context = LocalContext.current
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        // Already granted
    } else {
        // Request a permission
        SideEffect {
            launcher.launch(permission)
        }
    }
}

@Composable
@Preview
fun CheckAndRequestPermission_Preview() {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        //do something
        //it == true, permission granted
    }

    CheckAndRequestPermission(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
        launcher = launcher,
    )
}
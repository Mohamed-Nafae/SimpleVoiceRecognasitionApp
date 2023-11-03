package com.BM.voicerecognasition

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.BM.voicerecognasition.ui.theme.Pink80
import com.BM.voicerecognasition.ui.theme.Purple40
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun FeatureThatRequiresAutoRecordPermission() {

    Column(Modifier.fillMaxSize().padding(100.dp), Arrangement.Center) {
        androidx.compose.material.Text(
            "Pls, give the permission of the Audio Record because the app need it.",
            color = Color.Red,
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun test(){

}



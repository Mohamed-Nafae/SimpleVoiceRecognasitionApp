
package com.BM.voicerecognasition

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.BM.voicerecognasition.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState




@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun recognasition_inteface(
    start_Recognisation: () -> Unit,
    stop_Recognisation:()->Unit
) {
    if (navActions.value == WORDS_EMAIL_SEARCH) send()
    else if (navActions.value == WORDS_NOTE_SEARCH) write()
    else if (navActions.value ==  WORDS_SONG_SEARCH) playSong()
    val recordAudioStatePermission = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECORD_AUDIO
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    var start_RecoState by remember {
        mutableStateOf(false)
    }




    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    recordAudioStatePermission.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }

        }
    )



    if (recordAudioStatePermission.allPermissionsGranted) {

        val descriptiontext by remember {
            description_text
        }
        val resultText by remember{
            result_text
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Pink80,
                            Purple40
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = descriptiontext,modifier = Modifier.padding(20.dp))
            Text(text = resultText,modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))

            Button(modifier = Modifier
                .shadow(
                    5.dp,
                    shape = RoundedCornerShape(25.dp)
                )
                .size(120.dp, height = 50.dp),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    start_RecoState = !start_RecoState
                    if (start_RecoState) {
                        start_Recognisation()
                        text_Btn.value = "Terminate"
                    } else{
                        stop_Recognisation()
                        text_Btn.value ="Start"
                    }
                }
            ) {
                Text(
                    text = text_Btn.value,
                    style = MaterialTheme.typography.titleLarge,
                    color = PurpleGrey80,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else FeatureThatRequiresAutoRecordPermission()

}


@Preview(showBackground = true)
@Composable
fun showScreen() {
}


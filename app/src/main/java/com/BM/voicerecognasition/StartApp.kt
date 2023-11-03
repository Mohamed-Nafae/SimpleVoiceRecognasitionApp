package com.BM.voicerecognasition

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.BM.voicerecognasition.ui.theme.*

@Composable
fun startapp(
    alphaAnim:Float,
    modifier: Modifier,
    painter: Painter,
    description:String,
    navigate: ()-> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painter,
            contentDescription = description,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 5.dp)
                .alpha(alphaAnim)
        )
        Text(
            text = "Speech Recognition",
            style = MaterialTheme.typography.headlineSmall,
            color = PurpleGrey80,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
    }
    navigate
}
@Preview(showBackground = true)
@Composable
fun DefaultPreviews() {
    VoiceRecognasitionTheme {
        startapp(alphaAnim = 1f
            ,modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Pink80,
                        Purple40
                    )
                )
            )
            ,
            painter = painterResource(id = R.drawable.start_image_app),
            description = "start app icon"
        ){
        }
    }
}



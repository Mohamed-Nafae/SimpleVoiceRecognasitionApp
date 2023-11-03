package com.BM.voicerecognasition


import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.BM.voicerecognasition.ui.theme.Pink80
import com.BM.voicerecognasition.ui.theme.Purple40
import com.BM.voicerecognasition.ui.theme.VoiceRecognasitionTheme
import edu.cmu.pocketsphinx.*
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException
import com.BM.voicerecognasition.ui.theme.*








class MainActivity : ComponentActivity(),RecognitionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        captions = HashMap()

        setContent {
            VoiceRecognasitionTheme {

                captions!![KWS_SEARCH] = stringResource(id = R.string.kws_caption)
                captions!![MENU_SEARCH] = stringResource(id = R.string.menu_caption)
                captions!![WORDS_EMAIL_SEARCH] = stringResource(id = R.string.worlds_email_search)
                captions!![WORDS_NOTE_SEARCH] = stringResource(id = R.string.worlds_note_search)
                captions!![WORDS_SONG_SEARCH] = stringResource(id = R.string.phone_caption)

                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash_screen") {
                    composable("home_screen") {
                        recognasition_inteface(
                            {
                                description_text.value="Preparing the recognizer"
                                runRecognizerSetup()},
                            {onStop()
                                navActions.value = ""
                                result_text.value = ""
                            }
                        )
                    }
                    composable("splash_screen") {
                        var startAnimation by rememberSaveable() {
                            mutableStateOf(false)
                        }
                        val alphaAnim = animateFloatAsState(
                            targetValue = if (startAnimation) 1f else 0f,
                            animationSpec = tween(
                                durationMillis = 3000
                            )
                        )
                        LaunchedEffect(key1 = true) {
                            startAnimation = true
                            navController.popBackStack()
                            delay(4000)
                            navController.navigate("home_screen")
                        }
                        startapp(
                            alphaAnim = alphaAnim.value,
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
                            painter = painterResource(id = R.drawable.start_image_app),
                            description = "start app icon"
                        ) {
                            navController.navigate("home_screen")
                        }
                    }
                }
            }
        }

    }



    private fun runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        object : AsyncTask<Void?, Void?, Exception?>() {
            override fun doInBackground(vararg p0: Void?): Exception? {
                try {
                    val assets: Assets = Assets(this@MainActivity)


                    val assetsDir: File = assets.syncAssets()
                    setupRecognizer(assetsDir)
                } catch (e: IOException) {
                    return e
                }
                return null
            }

            override fun onPostExecute(result: Exception?) {
                if (result != null) {
                    description_text.value = "Failed to init recognizer " + result.message

                } else {
                    switchSearch(KWS_SEARCH)
                }
            }
        }.execute()
    }




    @Throws(IOException::class)
    private fun setupRecognizer(assetsDir: File) {

        recognizer = SpeechRecognizerSetup
            .defaultSetup()
            .setAcousticModel(
                File(
                    assetsDir, "en-us-ptm"
                )
            )
            .setDictionary(
                File(
                    assetsDir,
                    "cmudict-en-us.dict"
                )
            )
            .setRawLogDir(assetsDir)
            .getRecognizer()

        recognizer?.addListener(this)


        // Create keyword-activation search.
        recognizer?.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE)

        // Create your custom grammar-based search
        val menuGrammar = File(assetsDir, "menu.gram")
        recognizer?.addGrammarSearch(MENU_SEARCH, menuGrammar)
        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */


        // Create grammar-based search for digit recognition

        // Create grammar-based search for digit recognition
        val digitsGrammar = File(assetsDir, "words.gram")
        recognizer?.addGrammarSearch(WORDS_EMAIL_SEARCH, digitsGrammar)

        // Create language model search

        // Create language model search
        val languageModel = File(assetsDir, "weather.dmp")
        recognizer?.addNgramSearch(WORDS_NOTE_SEARCH, languageModel)

        // Phonetic search

        // Phonetic search
        val phoneticModel = File(assetsDir, "en-phone.dmp")
        recognizer?.addAllphoneSearch(WORDS_SONG_SEARCH, phoneticModel)
    }


    override fun onStop() {
        super.onStop()
        description_text.value = "Hello in The App"
        text_Btn.value = "Start"
        result_text.value = ""

        if (recognizer != null) {
            recognizer!!.cancel()
            recognizer!!.shutdown()
        }
    }
    override fun onPartialResult(hypothesis: Hypothesis?) {
        if (hypothesis == null) return
        val text = hypothesis.hypstr
        when{
            text.equals(KEYPHRASE) -> switchSearch(MENU_SEARCH)
            text.equals(WORDS_EMAIL_SEARCH) || text.endsWith(subjectEmail) || text.endsWith(emailBody) ->
                switchSearch(WORDS_EMAIL_SEARCH)
            text.equals(WORDS_NOTE_SEARCH) -> switchSearch(WORDS_NOTE_SEARCH)
            else -> result_text.value = text
        }
    }

    override fun onResult(hypothesis: Hypothesis?) {
        if (hypothesis != null) {
            val text = hypothesis.hypstr
            makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            when {
                text.endsWith(subjectEmail)-> {
                    sender_email.value = text.removeSuffix(subjectEmail).replace(" ","") + "@gmail.com"
                    result_text.value = "the email send to : "+sender_email.value
                }
                text.endsWith(emailBody)->{
                    subject_email.value = text.removeSuffix(emailBody)
                    result_text.value = "The title of email : " + subject_email.value
                }
                text.endsWith(confirmationEmail) ->{
                    body_email.value = text.removeSuffix(confirmationEmail)
                    result_text.value = "the email body : "+body_email.value
                    navActions.value = WORDS_EMAIL_SEARCH
                }
                text.endsWith("good") ->{
                    note_text.value = text
                    navActions.value = WORDS_NOTE_SEARCH
                }
                text.equals(WORDS_SONG_SEARCH) -> navActions.value = WORDS_SONG_SEARCH
            }
        }
    }

    override fun onBeginningOfSpeech() {}

    override fun onEndOfSpeech() {
        if (recognizer!!.searchName != KWS_SEARCH) {
            switchSearch(KWS_SEARCH)
            navActions.value =""
            result_text.value = ""
        }
    }

    private fun switchSearch(searchName: String) {
        recognizer!!.stop()
        if (searchName == KWS_SEARCH)
            recognizer!!.startListening(searchName)
        else
            recognizer!!.startListening(
                searchName,
                10000
            )
            description_text.value = captions?.get(searchName)!!
    }

    override fun onError(error: java.lang.Exception) {
        description_text.value = error.message!!
    }
    override fun onTimeout() {
        switchSearch(KWS_SEARCH)
    }

}






@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VoiceRecognasitionTheme {

    }
}
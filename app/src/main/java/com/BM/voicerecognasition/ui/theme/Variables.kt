package com.BM.voicerecognasition.ui.theme

import androidx.compose.runtime.mutableStateOf
import edu.cmu.pocketsphinx.SpeechRecognizer


internal const val KWS_SEARCH = "wakeup"
internal const val WORDS_SONG_SEARCH = "play song"
internal const val WORDS_EMAIL_SEARCH = "send email"
internal const val WORDS_NOTE_SEARCH = "write a note"
internal const val MENU_SEARCH = "menu"
internal var captions: HashMap<String, String>? = null
internal var recognizer: SpeechRecognizer? = null
internal const val KEYPHRASE = "start"
internal var subjectEmail = "email subject"
internal var emailBody = "email body"
internal var confirmationEmail = "confirmation"


/* composobale variables*/



internal var description_text = mutableStateOf("Hello in The App")
internal var text_Btn = mutableStateOf("Start")
internal var result_text = mutableStateOf("")
internal var navActions= mutableStateOf("")
internal var sender_email = mutableStateOf(""+"@gmail.com")
internal var subject_email = mutableStateOf("test")
internal var body_email = mutableStateOf("test")
internal var note_text = mutableStateOf("This is the content of my note")

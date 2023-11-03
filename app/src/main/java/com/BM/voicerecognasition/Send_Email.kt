package com.BM.voicerecognasition

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore

import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity

import com.BM.voicerecognasition.ui.theme.*
import java.io.File

@Composable
fun send() {

    val i = Intent(Intent.ACTION_SEND)

    // on below line we are passing email address,
    // email subject and email body
    val emailAddress = arrayOf(sender_email.value)
    i.putExtra(Intent.EXTRA_EMAIL, emailAddress)
    i.putExtra(Intent.EXTRA_SUBJECT, subject_email.value)
    i.putExtra(Intent.EXTRA_TEXT, body_email.value)

    // on below line we are
    // setting type of intent
    i.setType("message/rfc822")

    // on the below line we are starting our activity to open email application.
    LocalContext.current.startActivity(Intent.createChooser(i, "Choose an Email client : "))
}
@Composable
fun write() {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, "My Note")
    intent.putExtra(Intent.EXTRA_TEXT, note_text.value)
    LocalContext.current.startActivity(intent)
}
@Composable
fun playSong(){
//    val songName = "Onepiece.mp3"
//    val songPath = getSongPathByName(songName)
    val intent = Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER)
//    val songUri: Uri = Uri.parse(songPath)
//    intent.setDataAndType(songUri, "audio/mp3")
    LocalContext.current.startActivity(intent)
}
fun getSongPathByName(songName: String): String {
    val externalStorageDirectory = Environment.getExternalStorageDirectory().toString()
    val file = File(externalStorageDirectory)
    val files = file.listFiles()
    for (file in files) {
        if (file.isFile && file.name == songName) {
            return file.absolutePath
        }
    }
    return ""
}
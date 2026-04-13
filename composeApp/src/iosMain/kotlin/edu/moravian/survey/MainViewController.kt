package edu.moravian.survey

import androidx.compose.ui.window.ComposeUIViewController
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.moravian.survey.data.SurveyDatabase
import edu.moravian.survey.data.getSurveyDatabase
import platform.Foundation.NSHomeDirectory

fun MainViewController() = ComposeUIViewController { App(getSurveyDatabase(getDatabaseBuilder())) }

// Allows for the app to run in iOS emulator
fun getDatabaseBuilder(): RoomDatabase.Builder<SurveyDatabase> {
    val dbFilePath = NSHomeDirectory() + "/Documents/survey.db"

    return Room.databaseBuilder<SurveyDatabase>(
        name = dbFilePath,
    )
}

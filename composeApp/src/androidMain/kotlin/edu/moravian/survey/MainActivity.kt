package edu.moravian.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import edu.moravian.survey.data.SurveyDatabase
import edu.moravian.survey.data.getSurveyDatabase
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(getSurveyDatabase(getDatabaseBuilder(this)))
        }
    }
}

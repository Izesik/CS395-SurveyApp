package edu.moravian.survey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// / This is the main entity for a survey. It contains the date and time of the survey, as well as the overall score.
@Entity
data class SurveyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateTime: Long,
    val score: Int,
)

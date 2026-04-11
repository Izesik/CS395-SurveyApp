package edu.moravian.survey.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * When asking the database for a survey, it will automatically bundle
 * the SurveyEntity with all of its matching QuestionResultEntities.
 */
data class SurveyWithQuestions(
    @Embedded
    val survey: SurveyEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "surveyId"
    )
    val questionResults: List<QuestionResultEntity>
)
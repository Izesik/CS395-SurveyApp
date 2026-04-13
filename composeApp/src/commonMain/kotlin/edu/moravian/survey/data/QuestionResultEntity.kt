package edu.moravian.survey.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Suppress("ktlint:standard:kdoc")
/**
 * This is the entity for a question result. It contains the surveyId, questionId, and the answer.
 * The answer can be an Int, String, or Set<Int>, depending on the question type.
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SurveyEntity::class,
            parentColumns = ["id"],
            childColumns = ["surveyId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("surveyId")],
)

/**
 * The QuestionResultEntity class represents the result of a single question in a survey.
 * The questionId identifies which question this result corresponds to, and the answer can be
 * stored in different formats depending on the type of question.
 */
data class QuestionResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val surveyId: Long = 0,
    val questionId: String,
    val answerInt: Int? = null,
    val answerString: String? = null,
    val answerSet: Set<Int>? = null,
)

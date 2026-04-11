package edu.moravian.survey.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SurveyEntity::class,
            parentColumns = ["id"],
            childColumns = ["surveyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class QuestionResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val surveyId: Long = 0,
    val questionId: String,
    val answerInt: Int? = null,
    val answerString: String? = null,
    val answerSet: Set<Int>? = null
)
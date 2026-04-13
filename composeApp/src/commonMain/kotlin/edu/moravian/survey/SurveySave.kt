package edu.moravian.survey

import edu.moravian.survey.data.QuestionResultEntity
import edu.moravian.survey.data.SurveyDao
import edu.moravian.survey.data.SurveyEntity

/**
 * Saves the current survey result to the repository. This should be called when the user completes
 * the survey.
 */
suspend fun SurveyQuestions.save(dao: SurveyDao) {
    val surveyEntity = SurveyEntity(
        dateTime = currentTimeMillis(),
        score = this.score,
    )

    val questionResults = this.mapNotNull { question ->
        val answer = question.answer ?: return@mapNotNull null

        var ansInt: Int? = null
        var ansSet: Set<Int>? = null
        var ansString: String? = null

        when (answer) {
            is Int -> {
                ansInt = answer
            }

            is Set<*> -> {
                @Suppress("UNCHECKED_CAST")
                (answer as Set<Int>).let { ansSet = it }
            }

            is Pair<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                ansSet = answer.first as Set<Int>
                ansString = answer.second as String
            }
        }

        QuestionResultEntity(
            questionId = question.id,
            answerInt = ansInt,
            answerSet = ansSet,
            answerString = ansString,
        )
    }

    // Calls the helper we made to save everything at once
    dao.insertSurveyWithQuestions(surveyEntity, questionResults)
}

/**
 * Loads the survey result with the given ID from the repository and maps it back to a Survey.
 */
suspend fun Survey.load(surveyId: Long): Survey {
    // TODO: complete (may need to add parameter(s))
    return this
}

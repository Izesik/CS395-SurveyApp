package edu.moravian.survey.data

import edu.moravian.survey.Survey
import edu.moravian.survey.SurveyQuestions
import edu.moravian.survey.currentTimeMillis
import edu.moravian.survey.score

/**
 * Saves the current survey result to the database.
 */
suspend fun SurveyQuestions.save(dao: SurveyDao) {
    val surveyEntity = SurveyEntity(
        dateTime = currentTimeMillis(),
        score = this.score
    )

    val questionResults = this.mapNotNull { question ->
        val answer = question.answer ?: return@mapNotNull null

        var ansInt: Int? = null
        var ansSet: Set<Int>? = null
        var ansString: String? = null

        when (answer) {
            is Int -> ansInt = answer
            is Set<*> -> @Suppress("UNCHECKED_CAST") (answer as Set<Int>).let { ansSet = it }
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
            answerString = ansString
        )
    }

    // Calls the helper we made to save everything at once
    dao.insertSurveyWithQuestions(surveyEntity, questionResults)
}

/**
 * Loads the survey result with the given ID from the repository and maps it back to a Survey.
 */
suspend fun Survey.load(surveyId: Long, dao: SurveyDao): Survey {
    // We will leave this blank for now just so the app runs without crashing
    return this
}
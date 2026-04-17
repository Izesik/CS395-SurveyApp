package edu.moravian.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.moravian.survey.data.SurveyDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SurveyViewModel(
    private val surveyDatabase: SurveyDatabase,
) : ViewModel() {
    // Holds the current state of the survey being taken
    private val _currentSurvey = MutableStateFlow(AMISOS_R_SURVEY)
    val currentSurvey: StateFlow<Survey> = _currentSurvey.asStateFlow()

    init {
        startNewSurvey()
    }

    // Checks whether there is a recent survey in the database.
    private fun startNewSurvey() {
        viewModelScope.launch {
            val dao = surveyDatabase.surveyDao()
            val recentDbEntry = dao.getAllSurveys().firstOrNull()

            val freshSurvey = if (recentDbEntry != null) {
                // Load the old survey state
                val oldSurvey = AMISOS_R_SURVEY.load(recentDbEntry.survey.id, dao)
                val idsToKeep = setOf("sounds", "emotions")

                // Map over the blank template. If the ID is in our list, swap it
                AMISOS_R_SURVEY.map { blankElement ->
                    if (blankElement.id in idsToKeep) {
                        oldSurvey[blankElement.id] ?: blankElement
                    } else {
                        blankElement
                    }
                }
            } else {
                AMISOS_R_SURVEY
            }

            _currentSurvey.value = freshSurvey
        }
    }

    fun updateSurvey(updatedSurvey: Survey) {
        _currentSurvey.value = updatedSurvey
    }

    // Called when the "Submit" button is pressed
    fun submitSurvey(onCompleted: () -> Unit) {
        viewModelScope.launch {
            val questionsToSave = _currentSurvey.value.questions
            questionsToSave.save(surveyDatabase.surveyDao())
            onCompleted()
        }
    }

    // Checks if all questions have been answered
    fun isSurveyComplete(): Boolean = _currentSurvey.value.questions.all { it.answer != null }
}

package edu.moravian.survey

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import surveytaker.composeapp.generated.resources.Res
import surveytaker.composeapp.generated.resources.answer_all
import surveytaker.composeapp.generated.resources.submit

/**
 * The destination for the survey screen that can be filled out.
 */
@Serializable
data object SurveyScreen

/**
 * Displays the survey screen, which consists of a column with the survey view and a submit button.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SurveyScreen(
    viewModel: SurveyViewModel,
    onCompleted: () -> Unit,
) {
    val currentSurvey by viewModel.currentSurvey.collectAsState()
    var showErrors by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        SurveyView(
            survey = currentSurvey,
            showErrors = showErrors,
            onAnswer = { updatedSurvey ->
                viewModel.updateSurvey(updatedSurvey)
            },
        )
        // Show an error message if the user tries to submit without completing the survey
        if (showErrors) {
            Text(
                stringResource(Res.string.answer_all),
                color = MaterialTheme.colorScheme.error,
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = {
                val isComplete = viewModel.isSurveyComplete()
                if (isComplete) {
                    viewModel.submitSurvey(onCompleted)
                } else {
                    showErrors = true
                }
            },
        ) {
            Text(stringResource(Res.string.submit))
        }
    }
}

/**
 * Displays the given survey in a scrollable column. The survey will be rendered using the
 * [Survey.Render] function, and the column will have a border around it. The [onAnswer] callback
 * will be called whenever the user answers a question, and it will be passed the updated survey.
 * The [showErrors] parameter will be passed to the [Survey.Render] function to indicate whether
 * errors should be shown for unanswered questions.
 */
@Composable
fun ColumnScope.SurveyView(
    survey: Survey,
    showErrors: Boolean = false,
    onAnswer: ((Survey) -> Unit)? = null,
) {
    survey.Render(
        Modifier
            .weight(1f)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.shapes.medium,
            ).padding(10.dp)
            .fillMaxWidth(),
        showErrors,
        onAnswer,
    )
}

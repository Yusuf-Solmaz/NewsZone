package com.yms.presentation.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yms.domain.model.user_preferences.UserPreferencesLanguage
import com.yms.presentation.R
import com.yms.presentation.settings.viewmodel.SettingsEvent
import com.yms.presentation.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel(),onChangeCategory: () -> Unit) {

    val languageState = viewModel.languageState.collectAsStateWithLifecycle()
    val darkModeState = viewModel.darkModeState.collectAsStateWithLifecycle()

    var isDialogEnabled by remember { mutableStateOf(false) }
    var isThemeSheetVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        HorizontalDivider(Modifier.fillMaxWidth())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = dimensionResource(R.dimen.padding_big))
                .background(MaterialTheme.colorScheme.background) // Aynı animasyon
        ) {
            Text(
                text = "General",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )

            SettingsRow(
                icon = painterResource(R.drawable.ic_language),
                title = stringResource(R.string.language),
                subtitle = stringResource(R.string.language_subtitle),
                settingValue = languageState.value.language,
                background = MaterialTheme.colorScheme.background,
                onclick = {
                    isDialogEnabled = true
                }
            )

            SettingsRow(
                icon = painterResource(R.drawable.ic_dark_mode),
                title = stringResource(R.string.night_mode),
                subtitle = stringResource(R.string.appearence_subtitle),
                settingValue = if (darkModeState.value.isDarkMode) stringResource(R.string.dark) else stringResource(
                    R.string.light
                ),
                background = MaterialTheme.colorScheme.background,
                onclick = {
                    isThemeSheetVisible = true
                }
            )

            SettingsRow(
                icon = painterResource(R.drawable.ic_change),
                title = stringResource(R.string.change_choosen_category_title),
                subtitle = stringResource(R.string.change_choosen_category),
                background = MaterialTheme.colorScheme.background,
                onclick = onChangeCategory
            )

            if (isThemeSheetVisible) {
                ThemeSelectionSheet(
                    isDarkTheme = darkModeState.value.isDarkMode,
                    onThemeSelected = {
                        isDarkMode ->
                        viewModel.onEvent(SettingsEvent.SaveDarkMode(isDarkMode))
                    },
                    onDismiss = { isThemeSheetVisible = false }
                )
            }

            if (isDialogEnabled) {
                LanguageSelectionDialog(
                    currentLanguage = languageState.value.language,
                    onLanguageSelected = { selectedLanguage ->
                        isDialogEnabled = false
                        viewModel.onEvent(SettingsEvent.SaveLanguage(selectedLanguage))

                    },
                    onDismiss = { isDialogEnabled = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionSheet(
    isDarkTheme: Boolean,
    onThemeSelected: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val themeOptions = listOf(
        "Light Theme" to false,
        "Dark Theme" to true
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.night_mode),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            themeOptions.forEach { (text, themeValue) ->
                ThemeOptionRow(
                    text = text,
                    isSelected = isDarkTheme == themeValue,
                    onClick = {
                        onThemeSelected(themeValue)
                        scope.launch {
                            delay(500)
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeOptionRow(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(dimensionResource(R.dimen.padding_big)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        if (isSelected) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onLanguageSelected: (UserPreferencesLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    val availableLanguages = UserPreferencesLanguage.entries
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Select Language") },
        text = {
            Column {
                availableLanguages.forEach { userPreferencesLanguage ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(userPreferencesLanguage) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentLanguage == userPreferencesLanguage.language,
                            onClick = { onLanguageSelected(userPreferencesLanguage) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userPreferencesLanguage.language.uppercase())
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Close")
            }
        }
    )
}

@Composable
fun SettingsRow(
    background: Color,
    icon: Painter,
    title: String,
    subtitle: String,
    settingValue: String? = null,
    onclick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    val animateSurfaceColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surfaceContainer,
        animationSpec = tween(durationMillis = 500) // Animasyon süresi
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
                vertical = dimensionResource(R.dimen.padding_medium)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(background),
            shape = RoundedCornerShape(70.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onclick?.invoke()
                        }
                    )
                    .background(animateSurfaceColor)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.weight(1f))

                if (settingValue != null) {
                    Text(
                        text = settingValue,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium),
                top = dimensionResource(R.dimen.padding_small)
            )
        )
    }
}
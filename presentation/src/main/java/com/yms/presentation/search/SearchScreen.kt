package com.yms.presentation.search

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yms.presentation.R
import com.yms.presentation.items.ArticleCard
import com.yms.theme.onBackgroundLight
import com.yms.theme.outlineLight
import com.yms.theme.primaryLight
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val searchedNews = viewModel.pagedSearchNews.collectAsLazyPagingItems()

    val searchOptions by viewModel.searchOptions.collectAsState()

    val sortByOptions = listOf("Relevancy", "Popularity", "PublishedAt")
    val searchInOptions = listOf("Title", "Description", "Content")

    var isFilterOpen by remember { mutableStateOf(false) }

    LaunchedEffect(searchedNews.loadState) {
        if (searchedNews.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (searchedNews.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {

        SearchBarWithDoneAction(
            onFilterIconClick = {
                isFilterOpen = true
                scope.launch { sheetState.show() }
            },
            saveQuery = { query ->
                viewModel.updateSearchOptions {
                    copy(query = query)
                }
            },
            onSearchClick = {
                viewModel.search()
            },
            searchQuery = searchOptions.query
        )

        if (searchedNews.itemCount == 0) {
            Text(text = "No results found", modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally))
        }
        else {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(searchedNews.itemCount) { index ->
                    val article = searchedNews[index]

                    if (article != null) {
                        Log.d("NewsHomeScreen", "Article: ${article.urlToImage}")
                        ArticleCard(
                            articleData = article,
                            navigateToArticleDetailScreen = {}

                        )
                    }
                }
                item {
                    if (searchedNews.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }

    if (isFilterOpen) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch { sheetState.hide() }
                isFilterOpen = false
            }) {
            FilterContent(
                sortByOptions = sortByOptions,
                searchInOptions = searchInOptions,
                searchOptions = searchOptions,
                updateSearchOptions = { update -> viewModel.updateSearchOptions(update) }
            )
        }
    }
}

@Composable
private fun FilterContent(
    sortByOptions: List<String>,
    searchInOptions: List<String>,
    searchOptions: SearchOptions,
    updateSearchOptions: (SearchOptions.() -> SearchOptions) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Sort By", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        sortByOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        updateSearchOptions {
                            copy(sortBy = option)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = searchOptions.sortBy == option,
                    onClick = {
                        updateSearchOptions {
                            copy(sortBy = option)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Search In", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        searchInOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updatedSearchIn = if (searchOptions.searchIn.contains(option)) {
                            searchOptions.searchIn - option
                        } else {
                            searchOptions.searchIn + option
                        }
                        updateSearchOptions {
                            copy(searchIn = updatedSearchIn)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = searchOptions.searchIn.contains(option),
                    onCheckedChange = { isChecked ->
                        val updatedSearchIn = if (isChecked) {
                            searchOptions.searchIn + option
                        } else {
                            searchOptions.searchIn - option
                        }
                        updateSearchOptions {
                            copy(searchIn = updatedSearchIn)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        DatePickerSection(
            title = "From",
            selectedDate = searchOptions.fromDate,
            onDateSelected = { selectedDate ->
                updateSearchOptions {
                    copy(fromDate = selectedDate)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        DatePickerSection(
            title = "To",
            selectedDate = searchOptions.toDate,
            onDateSelected = { selectedDate ->
                updateSearchOptions {
                    copy(toDate = selectedDate)
                }
            }
        )
    }
}

@Composable
private fun DatePickerSection(
    title: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    Text(text = title, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    DatePicker(selectedDate = selectedDate, onDateSelected = onDateSelected)
}

@Composable
fun SearchBarWithDoneAction(
    onSearchClick: () -> Unit,
    onFilterIconClick: () -> Unit,
    saveQuery: (String) -> Unit,
    searchQuery: String
) {
    var query = searchQuery
    var isFocused by remember { mutableStateOf(false) } // Odak durumunu takip et


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = if (isFocused) 1.dp else 0.dp,
                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(50)
            )
            .background(
                MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    saveQuery(query)
                },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        focusState->
                        isFocused = focusState.isFocused
                    }
                ,
                placeholder = { Text("Search", color = Color.Gray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedTextColor = onBackgroundLight,
                    focusedPlaceholderColor = outlineLight,
                    cursorColor = primaryLight
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick()
                    }
                )
            )

            Icon(
                painter = painterResource(R.drawable.ic_tune),
                contentDescription = "Filter Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onFilterIconClick() }
            )
            Icon(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onSearchClick() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(selectedDate: LocalDate?, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    selectedDate?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendar.set(it.year, it.monthValue - 1, it.dayOfMonth)
        }
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = selectedDate?.toString() ?: "Select Date",
                modifier = Modifier
                    .clickable {
                        datePickerDialog.show()
                    }
                    .padding(8.dp)
            )
        }
    }
}

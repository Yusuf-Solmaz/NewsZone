package com.yms.presentation.article_detail


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yms.presentation.R
import com.yms.utils.SharedArticleState

@Composable
fun ArticleDetailScreen(onBack: () -> Unit, sharedArticleState: SharedArticleState, viewModel: ArticleDetailViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val article = sharedArticleState.article

    val insertState = viewModel.state.collectAsState()
    val isSaved = viewModel.isArticleSaved.collectAsState()

    DisposableEffect(Unit) {
        systemUiController.isStatusBarVisible = false
        onDispose {
            systemUiController.isStatusBarVisible = true
        }
    }

    LaunchedEffect(key1 = article) {
        if (article != null){
            viewModel.onEvent(ArticleDetailEvent.IsArticleSaved(article.url))
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        when (val state = insertState.value) {
            is InsertNewsState.Success -> {
                Toast.makeText(context, "Article saved successfully!", Toast.LENGTH_SHORT).show()
            }
            is InsertNewsState.Error -> {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            is InsertNewsState.Idle -> Unit
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (article != null) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = "Article Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(16.dp)){
                Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(Alignment.Center)
                                .clickable {
                                    onBack()
                                }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        AnimatedBookmarkIcon(
                            isSaved = isSaved.value,
                            delete = { viewModel.onEvent(ArticleDetailEvent.DeleteArticle(article?.url ?: "")) },
                            add = {  article?.let {
                                viewModel.onEvent(ArticleDetailEvent.InsertArticle(it))
                            } }
                        )


                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 0f
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {

                    Text(
                        text = article?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article?.timeAgo ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Row (verticalAlignment = Alignment.CenterVertically){

                        Text(
                            text = article?.sourceDto?.name ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Source",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article?.author ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }

                Icon(
                    painter = painterResource(R.drawable.ic_open_in_new),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Open in browser",
                    modifier = Modifier.size(24.dp).clickable(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article?.url))
                            context.startActivity(intent)
                        }
                    )
                )

            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth())

            val articleContent = (article?.content ?: "") + LoremIpsum(200).values.joinToString(" ")
            val cleanedContent = articleContent.replace(Regex("\\s+"), " ")
            val scrollState = rememberScrollState()

            Log.d("ArticleDetailScreen", "ArticleContent: $cleanedContent")

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState) ) {
                Text(
                    text = cleanedContent,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                    lineHeight = 20.sp
                )
            }

        }
    }
}

@Composable
fun AnimatedBookmarkIcon(
    isSaved: Boolean,
    delete: () -> Unit,
    add: () -> Unit
) {

    val tint by animateColorAsState(
        targetValue = if (isSaved) MaterialTheme.colorScheme.primary else Color.White,
        animationSpec = tween(durationMillis = 300), label = "Bookmark Icon"
    )

    IconButton(
        onClick = {
            if (isSaved) delete() else add()
        },
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bookmark),
            contentDescription = "Bookmark",
            tint = tint
        )
    }
}

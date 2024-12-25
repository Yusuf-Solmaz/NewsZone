package com.yms.presentation.article_detail


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.SourceData
import com.yms.presentation.R
import com.yms.utils.ArticleState

@Composable
fun ArticleDetailScreen(sharedState: ArticleState, viewModel: ArticleDetailViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val article = sharedState.article

    val insertState = viewModel.state.collectAsState()

    DisposableEffect(Unit) {
        systemUiController.isStatusBarVisible = false
        onDispose {
            systemUiController.isStatusBarVisible = true
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
                                color = Color.Black.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_send),
                            contentDescription = "Share",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.Center).clickable(
                                onClick = {
                                    if (article != null){
                                        viewModel.onEvent(ArticleDetailEvent.InsertArticle(article))
                                    }
                                }
                            )
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

@Preview(showBackground = true)
@Composable
fun ArticleDetailScreenPreview() {
    ArticleDetailScreen(sharedState = ArticleState(
        article = ArticleData(
            id = 1,
            author = "John Doe",
            content = "This is a detailed content of the article. It covers various aspects of the topic in depth.",
            description = "A brief description of the article to provide an overview.",
            publishedAt = "2024-12-23T10:00:00Z",
            sourceDto = SourceData(
                id = "source-1",
                name = "Example News"
            ),
            title = "Sample Article Title",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg",
            timeAgo = "2 hours ago"
        )
    ))
}
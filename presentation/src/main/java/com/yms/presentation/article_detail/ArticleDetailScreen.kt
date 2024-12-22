package com.yms.presentation.article_detail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yms.utils.ArticleState


@Composable
fun ArticleDetailScreen(sharedState: ArticleState) {
    val article = sharedState.article

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ){

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(topStart = 160.dp, topEnd = 160.dp))
                .background(Color.Green)
                .align(Alignment.BottomCenter)
        ){

        }



    }
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailScreenPreview() {
    ArticleDetailScreen(sharedState = ArticleState())
}
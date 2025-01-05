package com.yms.utils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.yms.presentation.R

@Composable
fun ShimmerNewsZoneHome(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp)
                    .shimmerEffect()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_shape_big)))
                    .height(230.dp)
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(10.dp)
                        .shimmerEffect()
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(20.dp)
                        .shimmerEffect()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
                    .height(20.dp)
                    .shimmerEffect()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = dimensionResource(R.dimen.padding_small))
            ) {
                repeat(8) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.padding_small)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_shape_big)))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(10.dp)
                                    .shimmerEffect()
                                    .padding(bottom = dimensionResource(R.dimen.padding_medium))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(top = dimensionResource(R.dimen.padding_medium))
                                    .height(8.dp)
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }
        }
    } else {
        contentAfterLoading()
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
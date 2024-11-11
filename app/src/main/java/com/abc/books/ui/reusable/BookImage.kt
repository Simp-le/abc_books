package com.abc.books.ui.reusable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.abc.books.R
import kotlin.math.min

@Composable
fun BookImage(
    source: String, modifier: Modifier = Modifier,
    doAnimation: Boolean = false
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(source.ifEmpty { R.drawable.ic_image }).build(),
        contentDescription = null,
        filterQuality = FilterQuality.None,
        contentScale = ContentScale.Crop,
        modifier = modifier
    ) {
        val state = painter.state
        val transition by animateFloatAsState(
            animationSpec = spring(),
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )
        val saturationValue by animateFloatAsState(
            animationSpec = tween(durationMillis = 2000),
            targetValue = if (transition == 1f) 1f else 0f,
            label = ""
        )

        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is AsyncImagePainter.State.Success -> {
                if (doAnimation) SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .scale(.8f + (.2f * transition))
                        .alpha(min(a = 1f, b = transition / .3f)),
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(saturationValue)
                    })
                ) else SubcomposeAsyncImageContent()
            }

            else -> {
                SubcomposeAsyncImageContent(
                    painter = painterResource(R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}
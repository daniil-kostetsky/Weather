package com.example.weather.presentation.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weather.R
import com.example.weather.presentation.tempToFormattedString
import com.example.weather.ui.theme.CardGradients
import com.example.weather.ui.theme.Gradient

@Composable
fun FavouriteContent(component: FavouriteComponent) {

    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            SearchCard(
                onClick = { component.onClickSearch() }
            )
        }
        itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id }
        ) { index, item ->
            CityCard(
                cityItem = item,
                index = index,
                onClick = { component.onCityItemClick(item.city) }
            )
        }
        item {
            AddFavouriteCityCard(
                onClick = { component.onCLickAddFavourite() }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCard(
    cityItem: FavouriteStore.State.CityItem,
    index: Int,
    onClick: () -> Unit
) {
    val gradient = getGradientByIndex(index)
    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                spotColor = gradient.shadowColor.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier

                .background(
                    brush = gradient.primaryGradient,
                    shape = MaterialTheme.shapes.extraLarge
                )

                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.extraLarge
                )
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryGradient,
                        center = Offset(
                            x = center.x - size.width / 2,
                            y = center.y - size.height / 3
                        ),
                        radius = size.maxDimension / 2
                    )
                }
                .clickable { onClick() }
                .padding(24.dp)
        ) {

            when (val weatherState = cityItem.weatherState) {
                FavouriteStore.State.WeatherState.Error -> {}
                FavouriteStore.State.WeatherState.Initial -> {}
                is FavouriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        model = weatherState.iconUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 24.dp),
                        text = weatherState.tempC.tempToFormattedString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
                    )
                }
                FavouriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
private fun AddFavouriteCityCard(
    onClick: () -> Unit
) {
    val shape = MaterialTheme.shapes.extraLarge

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 196.dp)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = shape
                )
                .clip(shape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchCard(
    onClick: () -> Unit
) {

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                spotColor = Color(0xFF5271C2).copy(alpha = 0.35f)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(CircleShape)
                .background(Brush.horizontalGradient(
                    listOf(
                        Color(0xFF5271C2),
                        Color(0xFF7E9BE0)
                    )
                ))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.25f),
                    shape = CircleShape
                )
                .clickable(onClick = onClick)
                .padding(start = 6.dp, end = 6.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.search),
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradients.gradients
    return gradients[index % gradients.size]
}
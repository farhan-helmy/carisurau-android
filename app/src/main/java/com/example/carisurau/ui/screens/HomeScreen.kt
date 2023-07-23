package com.example.carisurau.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.carisurau.R
import com.example.carisurau.model.Surau
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    carisurauUiState: CarisurauUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (carisurauUiState) {
        is CarisurauUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CarisurauUiState.Success -> PhotosGridScreen(
            carisurauUiState.suraus, modifier = modifier.fillMaxWidth()
        )

        is CarisurauUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        text = "loading"
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */ 
@Composable
fun ErrorScreen(retryAction: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "error", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("retry")
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun PhotosGridScreen(suraus: List<Surau>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(220.dp),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = suraus, key = { surau -> surau.uniqueName }) { surau ->
            SurauCard(
                surau,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun SurauCard(surau: Surau, modifier: Modifier = Modifier) {
    val surauPhoto = surau.surauPhotos?.getOrNull(0)?.filePath

    val imageRequest = if (!surauPhoto.isNullOrBlank()) {
        ImageRequest.Builder(context = LocalContext.current)
            .data(surauPhoto)
            .crossfade(true)
            .build()
    } else {
        null
    }
    Column {
        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                AsyncImage(
                    model = imageRequest,
                    error = painterResource(R.drawable.carisurau_com),
                    placeholder = painterResource(R.drawable.carisurau_com),
                    contentDescription = "surauphoto",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.width(160.dp)) {
                // Your Surau name text (wrap if necessary)
                Text(
                    text = surau.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "${surau.state}",
                    style = TextStyle(fontSize = 14.sp),
                )

            }
        }
    }
}
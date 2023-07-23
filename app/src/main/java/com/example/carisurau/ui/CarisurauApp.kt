package com.example.carisurau.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.carisurau.ui.screens.HomeScreen
import com.example.carisurau.R
import com.example.carisurau.ui.screens.CarisurauViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarisurauApp() {
    val navigationBarItems = remember {
        NavigationBarItems.values()
    }
    var selectedIndex by remember { mutableStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).padding(all = 4.dp),
        topBar = { CarisurauApp(scrollBehavior = scrollBehavior) },
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(64.dp),
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = MaterialTheme.colorScheme.primary,
                ballColor = MaterialTheme.colorScheme.secondary
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex = item.ordinal },
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "bottom bar icon",
                            modifier = Modifier.size(26.dp),
                            tint = if(selectedIndex == item.ordinal) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
        ) {
            val carisurauViewModel: CarisurauViewModel =
                viewModel(factory = CarisurauViewModel.Factory)
            HomeScreen(
                carisurauUiState = carisurauViewModel.carisurauUiState,
                retryAction = carisurauViewModel::getSuraus
            )
        }
    }
}

enum class NavigationBarItems(val icon: ImageVector){
    Home(icon = Icons.Default.Home),
    Add(icon = Icons.Default.Add),
    Profile(icon = Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarisurauApp(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "carisurau",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        modifier = modifier
    )
}

/*
@Composable
fun AnimatedNavigationBar(
    
)
*/





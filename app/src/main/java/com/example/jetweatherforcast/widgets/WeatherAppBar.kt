package com.example.jetweatherforcast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforcast.model.Favorite
import com.example.jetweatherforcast.navigation.WeatherScreens
import com.example.jetweatherforcast.screen.favorites.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more icon")
                }
            } else Box {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onButtonClicked.invoke() }
                )
            }
            if (isMainScreen) {
                val isAlreadyFavList =
                    favoriteViewModel.favList.collectAsState().value.filter { item ->
                        item.city == title.split(" ")[0]
                    }
                if (isAlreadyFavList.isNullOrEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val data = title.split(" ")
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = data[0],
                                        country = data[1]
                                    )
                                ).run { showIt.value = true }

                            }, tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
            } else {
                showIt.value = false
                Box {}
            }

            ShowToast(context = context, showIt)
        },
        modifier = Modifier
            .shadow(elevation = elevation)
            .padding(top = 20.dp)
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value)
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
// use when for handle clicks and navigate

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(color = Color.White),
        ) {

            DropdownMenuItem(
                text = {
                    Text("Favorites", modifier = Modifier.clickable {
                        navController.navigate(WeatherScreens.FavoriteScreen.name)
                    })
                },
                onClick = {
                    expanded = false
                    showDialog.value = false
                },
                leadingIcon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) }
            )
            DropdownMenuItem(
                text = {
                    Text("About", modifier = Modifier.clickable {
                        navController.navigate(WeatherScreens.AboutScreen.name)
                    })
                },
                onClick = {
                    expanded = false
                    showDialog.value = false
                },
                leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) }
            )
            DropdownMenuItem(
                text = {
                    Text("Settings", modifier = Modifier.clickable {
                        navController.navigate(WeatherScreens.SettingsScreen.name)
                    })

                },
                onClick = {
                    expanded = false
                    showDialog.value = false
                },
                leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
            )
        }
    }
}

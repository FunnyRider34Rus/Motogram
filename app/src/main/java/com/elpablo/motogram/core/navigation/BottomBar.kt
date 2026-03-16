package com.elpablo.motogram.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.elpablo.motogram.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotogramBottomBar(navController: NavController, scrollBehavior: BottomAppBarScrollBehavior) {
    val bottomBarScreens = listOf(
        MotogramBottomBarItem.DASHBOARD,
        MotogramBottomBarItem.RIDE,
        MotogramBottomBarItem.CHATS,
        MotogramBottomBarItem.SETTINGS
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = bottomBarScreens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomAppBar(
            modifier = Modifier
                .padding(horizontal = 64.dp, vertical = 32.dp)
                .graphicsLayer(
                    shape = RoundedCornerShape(32.dp), clip = true
                ),
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            scrollBehavior = scrollBehavior
        ) {
            bottomBarScreens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MotogramBottomBarItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        icon = {
            Icon(
                //modifier = Modifier.size(32.dp),
                painter = painterResource(screen.icon),
                contentDescription = stringResource(screen.label)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        //label =  { Text(text = stringResource(screen.label)) },
        alwaysShowLabel = false,
        onClick = {
            if (screen.route != currentDestination?.route) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.secondary,
            indicatorColor = Color.Transparent
        )
    )
}

sealed class MotogramBottomBarItem(
    val route: String,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int
) {
    data object DASHBOARD : MotogramBottomBarItem(
        route = "dashboard",
        label = R.string.screen_dashboard_title,
        icon = R.drawable.ic_dashboard_outline_24
    )
    data object RIDE : MotogramBottomBarItem(
        route = "ride",
        label = R.string.screen_ride_title,
        icon = R.drawable.ic_location_map_outline_24
    )
    data object CHATS : MotogramBottomBarItem(
        route = "chats",
        label = R.string.screen_chats_title,
        icon = R.drawable.ic_messages_outline_24
    )
    data object SETTINGS : MotogramBottomBarItem(
        route = "settings",
        label = R.string.screen_settings_title,
        icon = R.drawable.ic_settings_outline_24
    )
}
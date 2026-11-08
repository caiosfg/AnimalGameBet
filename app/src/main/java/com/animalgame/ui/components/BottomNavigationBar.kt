package com.animalgame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconName: String
) {
    object Home : BottomNavItem("home", "Home", "market")
    object Bicho : BottomNavItem("bicho", "Deu no Poste", "shiba")
    object Explorer : BottomNavItem("explorer", "Explorer", "swap")
    object Bookmark : BottomNavItem("bookmark", "Watchlist", "favorite")
    object Profile : BottomNavItem("profile", "Profile", "profile")
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bicho,
        BottomNavItem.Explorer,
        BottomNavItem.Bookmark,
        BottomNavItem.Profile
    )

    val context = LocalContext.current

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val resId = context.resources.getIdentifier(
                item.iconName,
                "drawable",
                context.packageName
            )

            val activeColor = CryptoGreen
            val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    if (resId != 0) {
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = item.title,
                            colorFilter = ColorFilter.tint(
                                if (isSelected) activeColor else inactiveColor
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = item.title.take(1),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) activeColor else inactiveColor
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) activeColor else inactiveColor,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = activeColor.copy(alpha = 0.15f)
                )
            )
        }
    }
}

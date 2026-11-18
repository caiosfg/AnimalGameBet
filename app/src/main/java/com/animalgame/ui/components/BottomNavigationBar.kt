package com.animalgame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    object Home : BottomNavItem("home", "Início", "market")
    object Bicho : BottomNavItem("bicho", "Deu no Poste", "trade")
    object Registrar : BottomNavItem("registrar", "Apostas", "shiba")
    object Profile : BottomNavItem("profile", "Perfil", "profile")
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
        BottomNavItem.Registrar,
        BottomNavItem.Profile
    )

    val context = LocalContext.current
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            val resId = context.resources.getIdentifier(
                item.iconName,
                "drawable",
                context.packageName
            )

            val activeColor = CryptoGreen
            val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant

            Tab(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) activeColor else inactiveColor,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

package com.animalgame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
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

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) activeColor.copy(alpha = 0.15f) else Color.Transparent)
                        .clickable { onNavigate(item.route) }
                        .padding(vertical = 8.dp),
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

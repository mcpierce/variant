package org.comixedproject.variant.ui.main

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.ui.bottomNavigationItems

@Composable
fun VariantNavigationBar(selectedIndex: Int, onClick: (Int) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                ),
                label = {
                    Text(
                        stringResource(id = bottomNavigationItem.label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                icon = {
                    Icon(
                        bottomNavigationItem.icon,
                        contentDescription = stringResource(id = bottomNavigationItem.label)
                    )
                },
                selected = (selectedIndex == index),
                onClick = { onClick(index) })
        }
    }
}

@Preview
@Composable
fun VariantNavigationBarPreview() {
    VariantTheme {
        VariantNavigationBar(selectedIndex = 0, onClick = {})
    }
}
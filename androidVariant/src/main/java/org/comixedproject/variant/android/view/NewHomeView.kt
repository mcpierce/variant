/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2026, The ComiXed Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package org.comixedproject.variant.android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme

enum class Destinations(val label: Int, val icon: Int) {
    FILES(R.string.comicsDestinationLabel, R.drawable.ic_comic_library),
    SERVER(R.string.browseServerDestinationLabel, R.drawable.ic_browse_library),
    SETTINGS(R.string.settingsDestinationLabel, R.drawable.ic_settings)
}

@Composable
fun NewHomeView() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val selectedItem = remember { mutableStateOf(Destinations.FILES) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerState) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(Modifier.height(12.dp))
                    Destinations.entries.forEach { destination ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painterResource(destination.icon),
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(destination.label)) },
                            selected = destination == selectedItem.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = destination
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        )
                    }
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.focusRequester(focusRequester),
                    onClick = { scope.launch { drawerState.open() } }) {
                    Text("Click to Open")
                }
            }
        }
    )

    LaunchedEffect(drawerState.isClosed) {
        if (drawerState.isClosed) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
@Preview
fun NewHomePreview() {
    VariantTheme {
        NewHomeView()
    }
}
/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.comixedproject.variant.model.serverTemplate
import org.comixedproject.variant.ui.Screen
import org.comixedproject.variant.ui.main.VariantNavigationBar
import org.comixedproject.variant.ui.server.browse.ServerDetail
import org.comixedproject.variant.ui.server.detail.EditServer
import org.comixedproject.variant.ui.server.list.ServerList
import org.comixedproject.variant.viewmodel.ServerListViewModel
import org.comixedproject.variant.viewmodel.ServerViewModel
import org.koin.androidx.compose.getViewModel

/**
 * The main activity screen for the application.
 *
 * @author Darryl L. Pierce
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen(
    navController: NavHostController
) {
    val serverListViewModel: ServerListViewModel = getViewModel()
    val serverViewModel: ServerViewModel = getViewModel()
    val selectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        content = { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = Screen.ServerList.route
            ) {
                composable(Screen.ServerList.route) {
                    ServerList(
                        serverListViewModel.serverList,
                        onAdd = { navController.navigate(Screen.ServerAdd.route) },
                        onEdit = { entry ->
                            serverListViewModel.currentServer = entry
                            navController.navigate(Screen.ServerEdit.route)
                        },
                        onOpen = { entry ->
                            serverListViewModel.currentServer = entry
                            serverViewModel.server = entry
                            navController.navigate(Screen.ServerOpen.route)
                        })
                }
                composable(Screen.ServerAdd.route) {
                    EditServer(
                        serverTemplate,
                        onSave = { name, url, username, password, serverColor ->
                            serverListViewModel.createServer(
                                name,
                                url,
                                username,
                                password,
                                serverColor
                            )
                            navController.navigate(Screen.ServerList.route)
                        }, onCancel = { navController.navigate(Screen.ServerList.route) })
                }
                composable(Screen.ServerEdit.route) {
                    EditServer(serverListViewModel.currentServer!!,
                        onSave = { name, url, username, password, serverColor ->
                            serverListViewModel.updateServer(
                                serverListViewModel.currentServer!!.copy(
                                    name = name,
                                    url = url,
                                    username = username,
                                    password = password,
                                    serverColor = serverColor
                                )
                            )
                            navController.navigate(Screen.ServerList.route)
                        }, onCancel = { navController.navigate(Screen.ServerList.route) })
                }
                composable(Screen.ServerOpen.route) {
                    ServerDetail(server = serverViewModel.server!!,
                        onBack = { navController.navigate(Screen.ServerList.route) })
                }
            }
        },
        bottomBar = {
            VariantNavigationBar(
                selectedIndex = selectedIndex.intValue,
                onClick = { index -> selectedIndex.intValue = index })
        })
}

@Preview
@Composable
fun MainActivityScreenPreview() {
    VariantTheme {
        MainActivityScreen(rememberNavController())
    }
}
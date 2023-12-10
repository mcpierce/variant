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

package org.comixedproject.variant.ui.server.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.model.Server

/**
 * Displays the list of serversm, and a button to add a new entry.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerList(
    serverList: List<Server>, onAdd: () -> Unit, onEdit: (Server) -> Unit, onOpen: (Server) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add a new server"
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(serverList) { entry ->
                ServerListEntry(entry = entry,
                    onClick = { server -> onOpen(server) },
                    onEdit = { server -> onEdit(server) })
            }
        }
    }
}

@Preview
@Composable
fun ServerListScreenAndroidPreview() {
    val serverList: List<Server> = listOf(
        Server(
            "",
            "Server 1",
            "http://comixedproject.org:7171/opds",
            "username",
            "password"
        ),
        Server(
            "",
            "Server 2",
            "http://comixedproject.org:7171/opds",
            "username",
            "password"
        ),
    )
    VariantTheme {
        ServerList(serverList, onAdd = {}, onEdit = {}, onOpen = {})
    }
}
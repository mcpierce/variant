/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2025, The ComiXed Project
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

package org.comixedproject.variant.android.view.server

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.SERVER_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.DismissBackground
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.platform.Log

private const val TAG = "ServerListItemView"

@Composable
fun ServerListItemView(
    server: Server,
    onEditServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit,
    onServerClicked: (Server) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        val currentItem by rememberUpdatedState(server)
        val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = {
                when (it) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        onDeleteServer(currentItem)
                    }

                    SwipeToDismissBoxValue.EndToStart -> {
                        onEditServer(currentItem)
                    }

                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                }
                return@rememberSwipeToDismissBoxState true
            },
            positionalThreshold = { it * .25f }
        )

        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { DismissBackground(dismissState) },
            content = {
                ElevatedCard(
                    colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clickable {
                                Log.debug(TAG, "Clicked on ${server.name}")
                                onServerClicked(server)
                            }
                    ) {

                        Text(
                            text = "${server.name}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "${server.url}", style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Left,
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${server.username}", style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Left
                        )
                    }
                }
            })
    }
}

@Composable
@Preview
fun ServerListItemView_preview() {
    VariantTheme {
        ServerListItemView(
            SERVER_LIST.get(0),
            onEditServer = { _ -> },
            onDeleteServer = { _ -> },
            onServerClicked = { _ -> })
    }
}
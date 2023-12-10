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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.comixedproject.variant.R
import org.comixedproject.variant.data.IdGenerator
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.ServerColorChoice
import org.comixedproject.variant.ui.server.detail.ServerColor
import org.comixedproject.variant.ui.server.fromHex

/**
 * Displays a single server in the list of servers.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerListEntry(entry: Server, onClick: (Server) -> Unit, onEdit: (Server) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .clickable { onClick(entry) }
                .fillMaxWidth()
        ) {
            Box(
                modifier =
                Modifier
                    .background(color = Color.White)
                    .padding(16.dp)
            )
            {
                Row(modifier = Modifier.fillMaxWidth())
                {
                    ServerColor(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = ServerColorChoice.fromHex(entry.serverColor),
                        size = 40.dp,
                        border = 1.dp
                    )
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1.0f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = entry.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = entry.url)
                        if (entry.lastAccessedOn != null) {
                            val now =
                                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                                    .toInstant(
                                        TimeZone.currentSystemDefault()
                                    )
                            val days =
                                entry.lastAccessedOn!!.toInstant(TimeZone.currentSystemDefault())
                                    .minus(now).inWholeDays
                            val text =
                                if (days == 0L) stringResource(id = R.string.last_accessed_today_text) else stringResource(
                                    id = R.string.last_accessed_text,
                                    days
                                )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            entry.username, style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable { onEdit(entry) },
                        imageVector = Icons.Filled.MoreVert, contentDescription =
                        stringResource(id = R.string.server_action_menu_description)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ServerDetailCardLastAccessedTodayAndroidPreview() {
    val entry = Server(
        IdGenerator().toString(),
        "Preview Server",
        "http://www.comixedproject.org:7171/opds",
        "comixedreader@localhost",
        "comixedreader",
    )
    entry.lastAccessedOn = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    ServerListEntry(
        entry = entry, onClick = {}, onEdit = {}
    )
}

@Preview
@Composable
fun ServerDetailCardNeverAccessedAndroidPreview() {
    ServerListEntry(
        entry = Server(
            IdGenerator().toString(),
            "Preview Server",
            "http://www.comixedproject.org:7171/opds",
            "comixedreader@localhost",
            "comixedreader"
        ),
        onClick = {},
        onEdit = {}
    )
}
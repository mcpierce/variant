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

package org.comixedproject.variant.ui.server.browse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.R
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.data.IdGenerator
import org.comixedproject.variant.model.Server

@Composable
fun ServerDetail(
    server: Server,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.return_to_server_list_description)
                    )
                }
            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Text(text = server.name, style = MaterialTheme.typography.headlineLarge)
                Text(text = server.url, style = MaterialTheme.typography.bodyMedium)
                Text(text = server.username, style = MaterialTheme.typography.bodyMedium)
            }
        })
}

@Preview
@Composable
fun ServerDetailPreview() {
    VariantTheme {
        ServerDetail(
            server = Server(
                IdGenerator().toString(),
                "Preview Server",
                "http://www.comixedproject.org:7171/opds",
                "comixedreader@localhost",
                "comixedreader"
            ),
            onBack = {}
        )
    }
}
/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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

package org.comixedproject.variant.shared.data

import org.comixedproject.variant.db.ServersDb
import org.comixedproject.variant.shared.model.server.Server

class ServerRepository(
    private val databaseHelper: DatabaseHelper,
) {
    val servers: List<Server>
        get() = databaseHelper.loadServers().map(ServersDb::map)

    fun loadServer(id: Long): Server {
        val record = databaseHelper.loadServer(id)
        return Server(
            serverId = record.serverId,
            name = record.name,
            url = record.url,
            username = record.username,
            password = record.password
        )
    }

    fun saveServer(server: Server) {
        if (server.serverId == null) {
            databaseHelper.createServer(server.name, server.url, server.username, server.password)
        } else {
            databaseHelper.updateServer(
                server.serverId,
                server.name,
                server.url,
                server.username,
                server.password,
            )
        }
    }

    fun deleteServer(server: Server) {
        server.serverId?.let { serverId -> databaseHelper.deleteServer(serverId) }
    }
}

fun ServersDb.map() =
    Server(
        serverId = this.serverId,
        name = this.name,
        url = this.url,
        username = this.username,
        password = this.password,
    )

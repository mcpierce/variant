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

package org.comixedproject.variant.shared.viewmodel

import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Log
import org.comixedproject.variant.shared.repositories.ServerLinkRepository

private val TAG = "ServerLinkViewModel"

/**
 * <code>ServerLinkViewModel</code> provides a shared view model for working with instances of {@link ServerLink}.
 *
 * @author Darryl L. Pierce
 */
class ServerLinkViewModel(
    val serverLinkRepository: ServerLinkRepository,
    val serverViewModel: ServerViewModel
) : ViewModel() {
    private val _serverLinkList = MutableStateFlow<List<ServerLink>>(viewModelScope, emptyList())
    val serverLinkList = _serverLinkList.asStateFlow()

    private val _parentLink = MutableStateFlow<ServerLink?>(viewModelScope, null)
    val parentLink = _parentLink.asStateFlow()

    private val _currentDirectory = MutableStateFlow<String>(viewModelScope, "")
    val currentDirectory = _currentDirectory.asStateFlow()
    val currentServerLink: ServerLink? = null

    private var currentServer: Server? = null

    /**
     * Checks if there are any links for the given server and directory.
     *
     * @param server the server
     * @param directory the directory
     * @return true if there are links in the database, false otherwise
     */
    fun hasLinks(server: Server, directory: String): Boolean {
        return serverLinkRepository.serverLinks
            .filter { link -> link.serverId == server.serverId }
            .filter { link -> link.directory == directory }
            .isNotEmpty()
    }

    /**
     * Loads the links already in the database.
     *
     * @param server the server
     * @param directory the directory
     */
    fun loadLinks(server: Server, directory: String) {
        this.currentServer = server
        val links = serverLinkRepository.loadLinksForDirectory(server, directory)
        var parent = serverLinkRepository.loadParentLink(server, directory)
        _parentLink.tryEmit(parent)
        _currentDirectory.tryEmit(directory)
        _serverLinkList.tryEmit(links)
    }

    /**
     * Saves links to the database.
     *
     * @param server the server
     * @param directory the directory
     * @param serverLinks the links
     */
    fun saveLinks(server: Server, directory: String, serverLinks: List<ServerLink>) {
        Log.debug(TAG, "Marking server as accessed: ${server.name}")
        this.serverViewModel.markServerAsAccessed(server)
        Log.debug(
            TAG,
            "Marking parent link as accessed: server=${server.serverId} directory=${directory}"
        )
        this.serverLinkRepository.markParentLinkAsAccessed(server, directory)
        Log.debug(TAG, "Saving ${serverLinks.size} server link(s)")
        this.serverLinkRepository.saveLinksForServer(server, directory, serverLinks)
        loadLinks(server, directory)
    }

    private fun findLink(server: Server, directory: String): ServerLink? {
        return serverLinkRepository.serverLinks.filter { it.serverId == server.serverId }
            .filter { it.downloadLink == directory }.firstOrNull()
    }
}
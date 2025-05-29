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

package org.comixedproject.variant.viewmodel

import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.RawFile
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.database.repository.DirectoryRepository
import org.comixedproject.variant.database.repository.ServerRepository
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.BrowsingState
import org.comixedproject.variant.model.state.DownloadingState
import org.comixedproject.variant.opds.ReaderAPI
import org.comixedproject.variant.platform.Log

private const val TAG = "ServerViewModel"

open class ServerViewModel(
    val serverRepository: ServerRepository,
    val directoryRepository: DirectoryRepository
) : ViewModel() {
    private var _libraryDirectory = ""
    var libraryDirectory: String
        get() = _libraryDirectory
        set(directory) {
            _libraryDirectory = directory
        }

    private val _serverList =
        MutableStateFlow<List<Server>>(viewModelScope, serverRepository.servers)

    @NativeCoroutinesState
    val serverList: StateFlow<List<Server>> = _serverList.asStateFlow()

    private val _editingServer = MutableStateFlow<Server?>(viewModelScope, null)

    @NativeCoroutinesState
    val editingServer: StateFlow<Server?> = _editingServer.asStateFlow()

    private val _browsingState = MutableStateFlow<BrowsingState?>(viewModelScope, null)

    @NativeCoroutinesState
    val browsingState: StateFlow<BrowsingState?> = _browsingState.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(viewModelScope, false)

    @NativeCoroutinesState
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _downloadingState =
        MutableStateFlow<MutableList<DownloadingState>>(viewModelScope, mutableListOf())

    @NativeCoroutinesState
    val downloadingState: StateFlow<MutableList<DownloadingState>> = _downloadingState.asStateFlow()

    fun editServer(server: Server?) {
        server?.let {
            Log.debug(TAG, "Editing server: ${it.name}")
        }
        viewModelScope.launch {
            _editingServer.emit(server)
        }
    }

    @NativeCoroutines
    suspend fun saveServer(server: Server) {
        Log.debug(TAG, "Saving server: ${server.name} ${server.url}")
        serverRepository.save(server)
        doReloadServers()
    }

    private suspend fun doReloadServers() {
        Log.debug(TAG, "Loading server list")
        val servers = serverRepository.servers
        _serverList.emit(servers)
    }

    @NativeCoroutines
    suspend fun loadDirectory(server: Server, path: String, reload: Boolean) {
        var contents = directoryRepository.loadDirectoryContents(server, path)
        if (contents.isEmpty() || reload) {
            _loading.emit(true)

            val url = URLBuilder(server.url).takeFrom(path).build()
            Log.debug(
                TAG,
                "Loading directory contents: server=${server.name} url=${url} reload=${reload}"
            )
            contents = ReaderAPI.loadDirectory(server, url).contents.map {
                DirectoryEntry(
                    directoryId = it.directoryId,
                    serverId = server.serverId!!,
                    title = it.title,
                    path = it.path,
                    parent = path,
                    entryType = it.entryType,
                    coverUrl = it.coverUrl
                )
            }
            directoryRepository.saveDirectoryContents(server, path, contents)
        }

        val directory = directoryRepository.findDirectory(path)

        _browsingState.emit(
            BrowsingState(
                server,
                path,
                directory?.title ?: "",
                directory?.parent ?: "",
                contents
            )
        )
        _loading.emit(true)
    }

    @NativeCoroutines
    suspend fun downloadFile(server: Server, path: String, filename: String) {
        Log.debug(TAG, "Starting download: ${server.name} path=${path}")

        val downloadingState = DownloadingState(server, path, 0, 0)
        val state = _downloadingState.value

        state.add(downloadingState)
        _downloadingState.emit(state)

        val url = URLBuilder(server.url).takeFrom(path).build()
        val file = File("${_libraryDirectory}/${filename}")
        val output = RawFile(file, FileMode.Write)

        ReaderAPI.downloadComic(server, url, output, onProgress = { received, total ->
            viewModelScope.launch(Dispatchers.IO) {
                val downloadingState = DownloadingState(server, path, received, total)
                val state =
                    _downloadingState.value.filter { !(it.server.serverId == server.serverId && it.path == path) }
                        .toMutableList()
                state.add(downloadingState)
                _downloadingState.emit(state)
                Log.debug(TAG, "====")
                state.forEach { Log.debug(TAG, "${it.path} ${it.received} ${it.total}") }
                Log.debug(TAG, "====")
            }
        })

        output.close()
        Log.debug(TAG, "Download complete")

        val finalState =
            _downloadingState.value.filter { !(it.server.serverId == server.serverId && it.path == path) }
                .toMutableList()
        _downloadingState.emit(finalState)
    }

    @NativeCoroutines
    suspend fun stopBrowsing() {
        Log.debug(TAG, "Clearing the server browsing state")
        _browsingState.emit(null)
    }
}
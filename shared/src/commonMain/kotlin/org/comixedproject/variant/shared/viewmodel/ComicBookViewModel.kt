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
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import korlibs.io.net.URL
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.shared.managers.ComicBookManager
import org.comixedproject.variant.shared.model.comic.ComicBook
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.net.createHttpClientFor
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.repositories.ComicBookRepository

private val TAG = "ComicBookViewModel"

/**
 * <code>ComicBookViewModel</code> provides a shared view model for working with comic books.
 *
 * @author Darryl L. Pierce
 */
class ComicBookViewModel(
    val comicBookRepository: ComicBookRepository,
    val comicBookManager: ComicBookManager
) : ViewModel() {
    private val _comicBook = MutableStateFlow<ComicBook?>(viewModelScope, null)
    val comicBook = _comicBook.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(viewModelScope, false)
    val isLoading = _isLoading.asStateFlow()

    private val _downloadTotal = MutableStateFlow<Long>(viewModelScope, 0L)
    val downloadTotal = _downloadTotal.asStateFlow()

    private val _downloadCurrent = MutableStateFlow<Long>(viewModelScope, 0L)
    val downloadCurrent = _downloadCurrent.asStateFlow()


    suspend fun loadComicBook(server: Server, directory: String) {
        val url = URL(directory)
        var filename = url.fullUrl.substring(url.fullUrl.lastIndexOf('/') + 1)
        val httpClient = createHttpClientFor(server, directory)
        _isLoading.tryEmit(true)
        val httpResponse = httpClient.get(directory) {
            onDownload { received, total ->
                Logger.d(TAG, "Downloaded ${received} of ${total} for ${directory}")
                _downloadCurrent.tryEmit(received)
                _downloadTotal.tryEmit(total)
            }
        }

        Logger.d(TAG, "Response: ${httpResponse.status}")
        if (httpResponse.status.value in 200..299) {
            val responseBody: ByteReadChannel = httpResponse.bodyAsChannel()
            Logger.d(TAG, "Saving comic content to file")
            val localFilename = comicBookManager.saveComicBook(filename, responseBody)
            Logger.d(TAG, "Saving details for comic: ${localFilename}")
            val comic = ComicBook(null, server.serverId!!, localFilename)
            comicBookRepository.saveComicBook(comic)
        }

        _isLoading.tryEmit(false)
        _downloadCurrent.tryEmit(0L)
        _downloadTotal.tryEmit(0L)
    }
}
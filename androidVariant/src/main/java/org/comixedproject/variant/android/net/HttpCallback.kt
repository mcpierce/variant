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

package org.comixedproject.variant.android.net

import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.net.encodeCredentials
import org.readium.r2.shared.util.Try
import org.readium.r2.shared.util.http.DefaultHttpClient
import org.readium.r2.shared.util.http.HttpError
import org.readium.r2.shared.util.http.HttpRequest
import org.readium.r2.shared.util.http.HttpResponse
import org.readium.r2.shared.util.http.HttpTry

/**
 * <code>HttpCallback</code> provides a callback to be used by Readium's HTTP client.
 *
 * @author Darryl L. Pierce
 */
class HttpCallback(val server: Server) :
    DefaultHttpClient.Callback {
    override suspend fun onStartRequest(request: HttpRequest): HttpTry<HttpRequest> {
        val credentials = encodeCredentials(server.username, server.password)
        val builder = request.buildUpon()
        builder.addHeader("Authorization", "Basic $credentials")
        return Try.success(builder.build())
    }

    override suspend fun onRequestFailed(request: HttpRequest, error: HttpError) {
        super.onRequestFailed(request, error)
    }

    override suspend fun onFollowUnsafeRedirect(
        request: HttpRequest,
        response: HttpResponse,
        newRequest: HttpRequest
    ): HttpTry<HttpRequest> {
        return super.onFollowUnsafeRedirect(request, response, newRequest)
    }

    override suspend fun onRecoverRequest(
        request: HttpRequest,
        error: HttpError
    ): HttpTry<HttpRequest> {
        return super.onRecoverRequest(request, error)
    }

    override suspend fun onResponseReceived(request: HttpRequest, response: HttpResponse) {
        super.onResponseReceived(request, response)
    }
}

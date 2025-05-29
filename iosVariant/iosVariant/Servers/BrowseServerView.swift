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

import SwiftUI
import Variant

struct BrowseServerView: View {
    let server: Server
    let path: String
    let title: String
    let parentPath: String
    let contents: [DirectoryEntry]
    let downloadingState: [DownloadingState]
    let isRefreshing: Bool
    let onLoadDirectory: (String, Bool) -> Void
    let onDownloadFile: (String, String) -> Void
    let onStopBrowsing: () -> Void

    @State var selected: DirectoryEntry?
    
    var directory: Bool {
        return true
    }

    var body: some View {
        List(contents, id: \.directoryId, selection: $selected) { entry in
            if entry.directory {
                DirectoryItemView(
                    entry: entry,
                    onLoadDirectory: onLoadDirectory
                )
            } else {
                FileItemView(
                    entry: entry,
                    downloadingState: downloadingState,
                    onDownloadFile: onDownloadFile
                )
            }
        }
    }
}

#Preview {
    BrowseServerView(
        server: SERVER_LIST[0],
        path: SERVER_LIST[0].url,
        title: "",
        parentPath: "",
        contents: DIRECTORY_LIST,
        downloadingState: [],
        isRefreshing: false,
        onLoadDirectory: { _, _ in },
        onDownloadFile: { _, _ in },
        onStopBrowsing: {}

    )
}

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

import SwiftUI
import Variant

struct ServerDetailView: View {
  var server: Server
  var onBrowseServer: (Server) -> Void
  var onEditServer: (Server) -> Void
  var onDeleteServer: (Server) -> Void

  var body: some View {
    VStack {
      Text(server.name).font(.headline)
      Text(server.url).font(.subheadline)
      Text(server.username).font(.body)
      Spacer()
      Text("Bottom of the screen")
    }
    .toolbar {
      Button(action: {}) {
        Image(systemName: "pencil")
      }
      .accessibilityLabel("Edit \(server.name)")
    }
  }
}

#Preview {
  ServerDetailView(
    server: Server(
      id: "1",
      name: "Server 1",
      url: "http://www.comixedproject.org:7171/opds",
      username: "reader@comixedproject.org",
      password: "my!password"),
    onBrowseServer: { _ in },
    onEditServer: { _ in },
    onDeleteServer: { _ in }
  )
}
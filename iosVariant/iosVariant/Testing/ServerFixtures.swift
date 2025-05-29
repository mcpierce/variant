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

import Variant

public var SERVER_LIST = [
    Server(
        serverId: 1,
        name: "Home Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 2,
        name: "Shared Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 3,
        name: "Buddy's Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 4,
        name: "Testing Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 5,
        name: "Some Other Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
]

public var DIRECTORY_LIST = [
    DirectoryEntry(
        directoryId: "1",
        serverId: 1,
        title: "All Comics",
        path: "/api/v1/all",
        parent: "/api/v1/root",
        directory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "2",
        serverId: 1,
        title: "Unread Comics",
        path: "/api/v1/all?unread=true",
        parent: "/api/v1/root",
        directory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "3",
        serverId: 1,
        title: "Collections",
        path: "/api/v1/collections",
        parent: "/api/v1/root",
        directory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "4",
        serverId: 1,
        title: "Reading Lists",
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        directory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "5",
        serverId: 1,
        title: "Smart Lists",
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        directory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "11",
        serverId: 1,
        title: "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        directory: false,
        coverUrl: ""
    ),
    DirectoryEntry(
        directoryId: "12",
        serverId: 1,
        title: "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        directory: false,
        coverUrl: ""
    ),
]

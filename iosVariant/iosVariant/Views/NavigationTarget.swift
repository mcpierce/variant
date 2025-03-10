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

import Foundation

enum NavigationTarget: String, CaseIterable, Hashable {
  case Comics
  case Servers
  case Settings
}

func navigationTargetTitle(target: NavigationTarget) -> String {
  return switch target {
  case .Comics: "Comics"
  case .Servers: "Servers"
  case .Settings: "Settings"
  }
}

func navigationTargetSubtitle(target: NavigationTarget) -> String {
  return switch target {
  case .Comics: "View and read comics books"
  case .Servers: "View, edit, and browse servers"
  case .Settings: "Application settings"
  }
}

func navigationTargetIcon(target: NavigationTarget) -> String {
  return switch target {
  case .Comics: "Comics"
  case .Servers: "Servers"
  case .Settings: "Settings"
  }
}

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

import KMPObservableViewModelSwiftUI
import SwiftUI
import shared

private let TAG = "ComicBookListItemView"

struct ComicBookListItemView: View {
    @ObservedObject var imageLoader: ImageLoader

    let comicBook: ComicBook
    let selected: Bool

    var onClick: (ComicBook) -> Void

    init(
        comicBook: ComicBook,
        selected: Bool,
        onClick: @escaping (ComicBook) -> Void
    ) {
        self.comicBook = comicBook
        self.selected = selected
        self.onClick = onClick
        self.imageLoader = ImageLoader(comicBook: comicBook)
    }

    var borderWidth: CGFloat {
        if selected {
            return 5
        } else {
            return 0
        }
    }

    var body: some View {
        VStack(alignment: .leading) {
            if imageLoader.image != nil {
                Image(uiImage: imageLoader.image!)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 128)
            } else {
                Image(systemName: "placeholdertext.fill")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 128)
            }

            Text(MetadataAPI().displayableTitle(comicBook: comicBook))
                .font(.subheadline)
                .lineLimit(1)
                .clipped()
        }
        .onTapGesture {
            Log().debug(tag: TAG, message: "Comic book item tapped")
            onClick(self.comicBook)
        }
        .padding()
        .border(.red, width: borderWidth)
    }
}

#Preview {
    ComicBookListItemView(
        comicBook: COMIC_BOOK_LIST[0],
        selected: false,
        onClick: { _ in }
    )
}

#Preview("selected") {
    ComicBookListItemView(
        comicBook: COMIC_BOOK_LIST[0],
        selected: true,
        onClick: { _ in }
    )
}

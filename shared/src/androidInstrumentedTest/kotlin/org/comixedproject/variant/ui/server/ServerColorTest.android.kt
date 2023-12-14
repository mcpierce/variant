/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.ui.server

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.model.ServerColorChoice
import org.junit.Rule
import org.junit.Test

class ServerColorTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun testServerColorBoxShown() {
        val color = ServerColorChoice.fromHex(ServerColorChoice.COLORS[0].hex)
        composeTestRule.setContent {
            ServerColor(color = color, size = 20.dp, border = 1.dp)
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_COLOR_BOX).assertIsDisplayed()
    }
}
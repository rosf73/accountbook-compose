package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.woowacamp.android_accountbook_15.ui.theme.Purple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Palette(
    modifier: Modifier,
    colors: List<Long>,
    onColorSelect: (Long) -> Unit
) {
    val (selectedColor, setSelectedColor) = remember { mutableStateOf(colors[0]) }
    onColorSelect(selectedColor)

    LazyVerticalGrid(
        cells = GridCells.Fixed(10),
        modifier = modifier.selectableGroup()
    ) {
        items(colors) { color ->
            ColorBox(Modifier.size(24.dp).aspectRatio(1f), color, selectedColor) {
                setSelectedColor(color)
            }
        }
    }
    Divider(color = Purple, thickness = 1.dp)
}

@Composable
fun ColorBox(
    modifier: Modifier,
    color: Long,
    selectedColor: Long,
    onColorSelect: () -> Unit
) {
    val boxModifier = if (color == selectedColor) {
        modifier.padding(4.dp)
    } else {
        modifier.padding(8.dp)
    }
    Box(
        modifier = boxModifier
            .background(Color(color))
            .selectable(
                selected = color == selectedColor,
                role = Role.RadioButton,
                onClick = onColorSelect
            ),
    )
}
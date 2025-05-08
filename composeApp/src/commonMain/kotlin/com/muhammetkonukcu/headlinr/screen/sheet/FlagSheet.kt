package com.muhammetkonukcu.headlinr.screen.sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammetkonukcu.headlinr.model.FlagModel
import com.muhammetkonukcu.headlinr.theme.Blue500
import com.muhammetkonukcu.headlinr.util.GetFlags
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagSheet(onSelectedFlag: (String) -> Unit, onDismissSheet: () -> Unit) {
    val state = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = state,
        dragHandle = { CustomDragHandle() },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = { onDismissSheet.invoke() }
    ) {
        FlagLazyColumn(onClick = {
            onSelectedFlag.invoke(it)
            coroutineScope.launch {
                state.hide()
                onDismissSheet.invoke()
            }
        })
    }
}

@Composable
fun CustomDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = 32.dp,
    height: Dp = 4.dp,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    color: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
    Surface(
        modifier =
            modifier
                .padding(vertical = 12.dp)
                .semantics {
                    contentDescription = ""
                },
        color = color,
        shape = shape
    ) {
        Box(Modifier.size(width = width, height = height))
    }
}

@Composable
private fun FlagLazyColumn(onClick: (String) -> Unit) {
    val flags = GetFlags()
    var flagItems by remember { mutableStateOf(flags) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = flagItems, key = { it.countryCode }) { item ->
            FlagItem(
                item = item,
                onItemCheckedChange = { updatedItem ->
                    flagItems = flagItems.map {
                        if (it.countryCode == updatedItem.countryCode) updatedItem
                        else it
                    }
                    onClick.invoke(item.countryCode)
                }
            )
        }
    }
}

@Composable
private fun FlagItem(
    item: FlagModel,
    onItemCheckedChange: (FlagModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable {
                onItemCheckedChange(item.copy(isSelected = !item.isSelected))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.size(width = 80.dp, height = 50.dp),
                painter = painterResource(item.imageRes),
                contentDescription = stringResource(item.countryName),
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = stringResource(item.countryName),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        FlagCheckbox(
            checked = item.isSelected,
            onCheckedChange = {
                onItemCheckedChange(item.copy(isSelected = it))
            }
        )
    }
}

@Composable
private fun FlagCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val checkboxColors = CheckboxDefaults.colors(
        checkedColor = Blue500,
        uncheckedColor = MaterialTheme.colorScheme.tertiaryContainer,
        checkmarkColor = MaterialTheme.colorScheme.onBackground,
    )
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = checkboxColors
    )
}
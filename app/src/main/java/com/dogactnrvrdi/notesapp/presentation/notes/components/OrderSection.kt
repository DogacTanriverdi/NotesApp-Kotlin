package com.dogactnrvrdi.notesapp.presentation.notes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            CustomRadioButton(
                modifier = Modifier.clickable { onOrderChange(NoteOrder.Title(noteOrder.orderType)) },
                text = stringResource(id = R.string.title),
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            CustomRadioButton(
                modifier = Modifier.clickable { onOrderChange(NoteOrder.Date(noteOrder.orderType)) },
                text = stringResource(id = R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            CustomRadioButton(
                modifier = Modifier.clickable { onOrderChange(NoteOrder.Color(noteOrder.orderType)) },
                text = stringResource(id = R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            CustomRadioButton(
                modifier = Modifier.clickable { onOrderChange(noteOrder.copy(OrderType.Ascending)) },
                text = stringResource(id = R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            CustomRadioButton(
                modifier = Modifier.clickable { onOrderChange(noteOrder.copy(OrderType.Descending)) },
                text = stringResource(id = R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}
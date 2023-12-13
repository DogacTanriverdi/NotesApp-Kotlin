package com.dogactnrvrdi.notesapp.domain.use_case

import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType
import com.dogactnrvrdi.notesapp.domain.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> = flow {
        repo.getNotes().onEach { notes ->
            when (noteOrder.orderType) {

                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.created }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.created }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}
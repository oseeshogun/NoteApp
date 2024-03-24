package com.example.notesapp.features.notes.presentation.screens.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.usecases.NoteUseCases
import com.example.notesapp.features.notes.domain.utils.NoteOrder
import com.example.notesapp.features.notes.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    init {
        listenNotes(NoteOrder.Date(OrderType.Descending))
    }

    private val _state = mutableStateOf(NotesState())

    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                _state.value = state.value.copy(noteOrder = event.noteOrder)
                listenNotes(event.noteOrder)
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> _state.value = _state.value.copy(
                isOrderSectionVisible = !state.value.isOrderSectionVisible
            )
        }
    }


    private fun listenNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.streamNotesUseCase(noteOrder).onEach { notes ->
            _state.value = _state.value.copy(notes = notes, noteOrder = noteOrder)
        }
            .launchIn(viewModelScope)
    }
}
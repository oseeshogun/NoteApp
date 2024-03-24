package com.example.notesapp.features.notes.presentation.screens.add_edit_note

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.features.notes.domain.models.InvalidNoteException
import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.usecases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
        private set

    var noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
        private set

    var noteColor = mutableStateOf<Int>(Note.notColors.random().toArgb())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { it ->
            if (it != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(it)?.also { note ->
                        currentNoteId = note.id
                        noteTitle.value = noteTitle.value.copy(text = note.title, isHintVisible = false)
                        noteContent.value = noteContent.value.copy(text = note.content, isHintVisible = false)
                        noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                noteColor.value = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                noteContent.value = noteContent.value.copy(text = event.value)
            }

            is AddEditNoteEvent.EnteredTitle -> {
                noteTitle.value = noteTitle.value.copy(text = event.value)
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId,
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}
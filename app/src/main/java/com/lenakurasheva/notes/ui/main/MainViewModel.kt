package com.lenakurasheva.notes.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.lenakurasheva.notes.data.Repository
import com.lenakurasheva.notes.data.entity.Note
import com.lenakurasheva.notes.data.model.NoteResult
import com.lenakurasheva.notes.ui.base.BaseViewModel

class MainViewModel(val repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer{ result: NoteResult? ->
        result ?: return@Observer
        when (result){
            is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState(result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        repositoryNotes.observeForever(notesObserver)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}
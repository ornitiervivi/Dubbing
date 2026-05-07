package com.localdubber.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.usecase.CreateDubbingProject
import com.localdubber.domain.usecase.DeleteDubbingProject
import com.localdubber.domain.usecase.GetDubbingProjects
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val createProject: CreateDubbingProject, getProjects: GetDubbingProjects, private val deleteProject: DeleteDubbingProject) : ViewModel() {
    val projects: StateFlow<List<DubbingProject>> = getProjects().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun create(fileName: String, uri: String, onCreated: (Long) -> Unit) = viewModelScope.launch { onCreated(createProject(fileName, uri)) }
    fun delete(id: Long) = viewModelScope.launch { deleteProject(id) }
}

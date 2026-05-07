package com.localdubber.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingStep
import com.localdubber.domain.usecase.BuildDubbingTimeline
import com.localdubber.domain.usecase.GetDubbingProjectById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val getById: GetDubbingProjectById, private val timelineBuilder: BuildDubbingTimeline = BuildDubbingTimeline()) : ViewModel() {
    private val _project = MutableStateFlow<DubbingProject?>(null)
    val project: StateFlow<DubbingProject?> = _project
    val timeline: List<DubbingStep> = timelineBuilder()
    fun load(id: Long) = viewModelScope.launch { _project.value = getById(id) }
}

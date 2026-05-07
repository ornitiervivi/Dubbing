package com.localdubber.domain.model

data class DubbingProject(val id: Long, val fileName: String, val videoUri: String, val status: DubbingProjectStatus, val createdAt: Long, val updatedAt: Long)

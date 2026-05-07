package com.localdubber.domain.repository

import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingProjectStatus
import kotlinx.coroutines.flow.Flow

interface DubbingProjectRepository {
    fun getProjects(): Flow<List<DubbingProject>>
    suspend fun createProject(fileName: String, videoUri: String): Long
    suspend fun getProjectById(id: Long): DubbingProject?
    suspend fun updateStatus(id: Long, status: DubbingProjectStatus)
    suspend fun deleteProject(id: Long)
}

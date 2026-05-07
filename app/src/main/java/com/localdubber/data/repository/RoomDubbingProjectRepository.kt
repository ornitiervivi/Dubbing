package com.localdubber.data.repository

import com.localdubber.data.local.dao.DubbingProjectDao
import com.localdubber.data.local.entity.DubbingProjectEntity
import com.localdubber.data.mapper.toDomain
import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingProjectStatus
import com.localdubber.domain.repository.DubbingProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDubbingProjectRepository(private val dao: DubbingProjectDao) : DubbingProjectRepository {
    override fun getProjects(): Flow<List<DubbingProject>> = dao.getProjects().map { it.map(DubbingProjectEntity::toDomain) }
    override suspend fun createProject(fileName: String, videoUri: String): Long {
        val now = System.currentTimeMillis()
        return dao.insert(DubbingProjectEntity(fileName = fileName, videoUri = videoUri, status = DubbingProjectStatus.CREATED.name, createdAt = now, updatedAt = now))
    }
    override suspend fun getProjectById(id: Long): DubbingProject? = dao.getById(id)?.toDomain()
    override suspend fun updateStatus(id: Long, status: DubbingProjectStatus) { dao.getById(id)?.let { dao.update(it.copy(status = status.name, updatedAt = System.currentTimeMillis())) } }
    override suspend fun deleteProject(id: Long) = dao.deleteById(id)
}

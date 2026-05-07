package com.localdubber.domain.usecase

import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingProjectStatus
import com.localdubber.domain.repository.DubbingProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UseCasesTest {
    @Test
    fun createProjectStartsWithCreatedStatus() = runBlocking {
        val repo = FakeRepo()
        val id = CreateDubbingProject(repo)("file.mp4", "uri")
        assertEquals(DubbingProjectStatus.CREATED, repo.getProjectById(id)?.status)
    }

    @Test
    fun updateStatusChangesProjectStatus() = runBlocking {
        val repo = FakeRepo()
        val id = repo.createProject("file.mp4", "uri")
        UpdateDubbingProjectStatus(repo)(id, DubbingProjectStatus.RENDERING)
        assertEquals(DubbingProjectStatus.RENDERING, repo.getProjectById(id)?.status)
    }

    private class FakeRepo : DubbingProjectRepository {
        private val list = mutableListOf<DubbingProject>()
        private val flow = MutableStateFlow<List<DubbingProject>>(emptyList())
        override fun getProjects(): Flow<List<DubbingProject>> = flow
        override suspend fun createProject(fileName: String, videoUri: String): Long {
            val id = (list.size + 1).toLong(); val now = 1L
            list += DubbingProject(id, fileName, videoUri, DubbingProjectStatus.CREATED, now, now); flow.value = list.toList(); return id
        }
        override suspend fun getProjectById(id: Long): DubbingProject? = list.firstOrNull { it.id == id }
        override suspend fun updateStatus(id: Long, status: DubbingProjectStatus) { list.replaceAll { if (it.id == id) it.copy(status = status) else it }; flow.value = list.toList() }
        override suspend fun deleteProject(id: Long) { list.removeAll { it.id == id }; flow.value = list.toList() }
    }
}

package com.localdubber.domain.usecase

import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingProjectStatus
import com.localdubber.domain.model.DubbingStep
import com.localdubber.domain.repository.DubbingProjectRepository
import kotlinx.coroutines.flow.Flow

class CreateDubbingProject(private val repository: DubbingProjectRepository) { suspend operator fun invoke(fileName: String, videoUri: String) = repository.createProject(fileName, videoUri) }
class GetDubbingProjects(private val repository: DubbingProjectRepository) { operator fun invoke(): Flow<List<DubbingProject>> = repository.getProjects() }
class GetDubbingProjectById(private val repository: DubbingProjectRepository) { suspend operator fun invoke(id: Long) = repository.getProjectById(id) }
class UpdateDubbingProjectStatus(private val repository: DubbingProjectRepository) { suspend operator fun invoke(id: Long, status: DubbingProjectStatus) = repository.updateStatus(id, status) }
class DeleteDubbingProject(private val repository: DubbingProjectRepository) { suspend operator fun invoke(id: Long) = repository.deleteProject(id) }

class BuildDubbingTimeline {
    operator fun invoke(): List<DubbingStep> = listOf(DubbingStep.VIDEO_IMPORT, DubbingStep.AUDIO_EXTRACTION, DubbingStep.TRANSCRIPTION, DubbingStep.TRANSLATION, DubbingStep.VOICE_GENERATION, DubbingStep.RENDERING, DubbingStep.COMPLETED)
}

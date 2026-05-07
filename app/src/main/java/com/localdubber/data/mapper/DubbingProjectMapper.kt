package com.localdubber.data.mapper

import com.localdubber.data.local.entity.DubbingProjectEntity
import com.localdubber.domain.model.DubbingProject
import com.localdubber.domain.model.DubbingProjectStatus

fun DubbingProjectEntity.toDomain() = DubbingProject(id, fileName, videoUri, DubbingProjectStatus.valueOf(status), createdAt, updatedAt)
fun DubbingProject.toEntity() = DubbingProjectEntity(id, fileName, videoUri, status.name, createdAt, updatedAt)

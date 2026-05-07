package com.localdubber.core

import android.content.Context
import androidx.room.Room
import com.localdubber.data.local.LocalDubberDatabase
import com.localdubber.data.repository.RoomDubbingProjectRepository
import com.localdubber.domain.usecase.CreateDubbingProject
import com.localdubber.domain.usecase.DeleteDubbingProject
import com.localdubber.domain.usecase.GetDubbingProjectById
import com.localdubber.domain.usecase.GetDubbingProjects
import com.localdubber.domain.usecase.UpdateDubbingProjectStatus

class AppContainer(context: Context) {
    private val db = Room.databaseBuilder(context, LocalDubberDatabase::class.java, "localdubber.db").build()
    private val repository = RoomDubbingProjectRepository(db.dubbingProjectDao())
    val createDubbingProject = CreateDubbingProject(repository)
    val getDubbingProjects = GetDubbingProjects(repository)
    val getDubbingProjectById = GetDubbingProjectById(repository)
    val updateDubbingProjectStatus = UpdateDubbingProjectStatus(repository)
    val deleteDubbingProject = DeleteDubbingProject(repository)
}

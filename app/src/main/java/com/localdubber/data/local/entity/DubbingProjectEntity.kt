package com.localdubber.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dubbing_projects")
data class DubbingProjectEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val fileName: String, val videoUri: String, val status: String, val createdAt: Long, val updatedAt: Long)

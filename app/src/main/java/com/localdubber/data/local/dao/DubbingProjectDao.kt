package com.localdubber.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.localdubber.data.local.entity.DubbingProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DubbingProjectDao {
    @Query("SELECT * FROM dubbing_projects ORDER BY createdAt DESC")
    fun getProjects(): Flow<List<DubbingProjectEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: DubbingProjectEntity): Long
    @Query("SELECT * FROM dubbing_projects WHERE id = :id")
    suspend fun getById(id: Long): DubbingProjectEntity?
    @Update
    suspend fun update(project: DubbingProjectEntity)
    @Query("DELETE FROM dubbing_projects WHERE id = :id")
    suspend fun deleteById(id: Long)
}

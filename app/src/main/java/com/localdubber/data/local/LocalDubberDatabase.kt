package com.localdubber.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.localdubber.data.local.dao.DubbingProjectDao
import com.localdubber.data.local.entity.DubbingProjectEntity

@Database(entities = [DubbingProjectEntity::class], version = 1, exportSchema = false)
abstract class LocalDubberDatabase : RoomDatabase() { abstract fun dubbingProjectDao(): DubbingProjectDao }

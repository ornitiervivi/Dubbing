package com.localdubber.data.mapper

import com.localdubber.data.local.entity.DubbingProjectEntity
import com.localdubber.domain.model.DubbingProjectStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class DubbingProjectMapperTest {
    @Test
    fun mapsEntityToDomain() {
        val entity = DubbingProjectEntity(1, "v.mp4", "uri", "CREATED", 1, 1)
        assertEquals(DubbingProjectStatus.CREATED, entity.toDomain().status)
    }
}

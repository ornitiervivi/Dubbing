package com.localdubber.presentation.detail

import com.localdubber.domain.model.DubbingStep
import com.localdubber.domain.usecase.BuildDubbingTimeline
import org.junit.Assert.assertEquals
import org.junit.Test

class BuildDubbingTimelineTest {
    @Test
    fun timelineOrderIsCorrect() {
        assertEquals(listOf(DubbingStep.VIDEO_IMPORT, DubbingStep.AUDIO_EXTRACTION, DubbingStep.TRANSCRIPTION, DubbingStep.TRANSLATION, DubbingStep.VOICE_GENERATION, DubbingStep.RENDERING, DubbingStep.COMPLETED), BuildDubbingTimeline().invoke())
    }
}

package com.localdubber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.localdubber.core.AppContainer
import com.localdubber.presentation.navigation.LocalDubberNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = AppContainer(applicationContext)
        setContent { LocalDubberNavHost(container) }
    }
}

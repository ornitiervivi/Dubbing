package com.localdubber.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SimpleFactory<T : ViewModel>(private val create: () -> T) : ViewModelProvider.Factory {
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = create.invoke() as VM
}

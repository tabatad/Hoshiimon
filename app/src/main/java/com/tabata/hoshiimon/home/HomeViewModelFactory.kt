package com.tabata.hoshiimon.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tabata.hoshiimon.database.AppDao

class HomeViewModelFactory(
    private val database: AppDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.tabata.hoshiimon.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tabata.hoshiimon.database.AppDao

class HomeViewModel(
    private val database: AppDao,
    application: Application
) : AndroidViewModel(application) {
}
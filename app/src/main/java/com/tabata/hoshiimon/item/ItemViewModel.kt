package com.tabata.hoshiimon.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tabata.hoshiimon.database.AppDao
import com.tabata.hoshiimon.database.Item
import kotlinx.coroutines.launch

class ItemViewModel(
    private val database: AppDao,
    application: Application
) : AndroidViewModel(application) {

    private val _item = MutableLiveData<Item>()
    val item : LiveData<Item>
        get() = _item

    fun getItemByItemId(itemId: Long) {
        viewModelScope.launch {
            _item.value = database.getItemByItemId(itemId)
        }
    }
}
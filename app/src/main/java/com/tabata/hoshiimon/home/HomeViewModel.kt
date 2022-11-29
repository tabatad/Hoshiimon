package com.tabata.hoshiimon.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tabata.hoshiimon.database.AppDao
import com.tabata.hoshiimon.database.Group
import com.tabata.hoshiimon.database.Item
import com.tabata.hoshiimon.database.Value
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val database: AppDao,
    application: Application
) : AndroidViewModel(application) {

    init {
        Timber.plant(Timber.DebugTree())

        viewModelScope.launch {
            if (database.selectItem() == null) {
                setDefaultDB()
            }
        }
    }

    fun setDefaultDB() {
        viewModelScope.launch {
            database.itemInsert(Item(itemName = "12600k", price = 200))
            database.itemInsert(Item(itemName = "12900k", price = 500))
            database.itemInsert(Item(itemName = "5600X", price = 200))
            database.itemInsert(Item(itemName = "5950X", price = 600))
            database.itemInsert(Item(itemName = "DDR4 16GB", price = 100))
            database.itemInsert(Item(itemName = "DDR4 32GB", price = 200))
            database.itemInsert(Item(itemName = "DDR5 32GB", price = 300))
            database.itemInsert(Item(itemName = "Bed", price = 200))
            database.itemInsert(Item(itemName = "Sofa", price = 100))

            database.groupInsert(Group(groupName = "PC", higherGroupId = null))
            database.groupInsert(Group(groupName = "CPU", higherGroupId = 1))
            database.groupInsert(Group(groupName = "RAM", higherGroupId = 1))
            database.groupInsert(Group(groupName = "House", higherGroupId = null))

            database.valueInsert(Value(itemId = 1, groupId = 2))
            database.valueInsert(Value(itemId = 2, groupId = 2))
            database.valueInsert(Value(itemId = 3, groupId = 2))
            database.valueInsert(Value(itemId = 4, groupId = 2))
            database.valueInsert(Value(itemId = 5, groupId = 3))
            database.valueInsert(Value(itemId = 6, groupId = 3))
            database.valueInsert(Value(itemId = 7, groupId = 3))
            database.valueInsert(Value(itemId = 8, groupId = 4))
            database.valueInsert(Value(itemId = 9, groupId = 4))

            Timber.i("default insert completed")
        }
    }

    fun select() {
        viewModelScope.launch {
            val item = database.selectItem()
            if (item != null) {
                Timber.i("select completed name:${item.itemName} price:${item.price}")
            } else {
                Timber.i("select completed, but empty")
            }
        }
    }
}
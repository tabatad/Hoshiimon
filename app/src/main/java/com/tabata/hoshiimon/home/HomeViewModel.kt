package com.tabata.hoshiimon.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _groupDataSet = MutableLiveData<List<Group>>()
    val groupDataSet: LiveData<List<Group>>
        get() = _groupDataSet

    private val _itemDataSet = MutableLiveData<List<Item>>()
    val itemDataSet: LiveData<List<Item>>
        get() = _itemDataSet

    init {
        Timber.plant(Timber.DebugTree())

        viewModelScope.launch {
            if (database.selectItem() == null) {
                setDefaultDB()
            }
        }
    }

    fun getDefaultGroup() {
        viewModelScope.launch {
            _groupDataSet.value = database.getRootGroups()
        }
    }

    fun getHigherGroup(group: Group) {
        viewModelScope.launch {
            _groupDataSet.value = database.getLowerGroup(group.groupId)
        }
    }

    fun getItemsByGroupId(group: Group) {
        viewModelScope.launch {
            _itemDataSet.value = database.getItemsByGroupId(group.groupId)
        }
    }

    private fun setDefaultDB() {
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
}
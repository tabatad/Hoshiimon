package com.tabata.hoshiimon.home

import android.app.Application
import androidx.lifecycle.*
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

    companion object {
        const val ROOT = 1L
    }

    private val _groupDataSet = MutableLiveData<List<Group>>()
    val groupDataSet: LiveData<List<Group>>
        get() = _groupDataSet

    private val _itemDataSet = MutableLiveData<List<Item>>()
    val itemDataSet: LiveData<List<Item>>
        get() = _itemDataSet

    private val _currentGroup = MutableLiveData<Group>()
    val currentGroup: LiveData<Group>
        get() = _currentGroup

    init {
        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }

        viewModelScope.launch {
            if (database.selectItem() == null) {
                setDefaultDB()
            }
        }
    }

    fun setDefaultGroup() {
        setCurrentGroup(ROOT)
    }

    fun getLowerGroup(group: Group) {
        viewModelScope.launch {
            _groupDataSet.value = database.getLowerGroup(group.groupId)
        }
    }

    fun getHigherGroup() {
        viewModelScope.launch {
            _currentGroup.value = database.getHigherGroup(_currentGroup.value!!.higherGroupId)
        }
    }

    fun getItemsByGroupId(group: Group) {
        viewModelScope.launch {
            _itemDataSet.value = database.getItemsByGroupId(group.groupId)
        }
    }

    fun setCurrentGroup(group_id: Long) {
        viewModelScope.launch {
            _currentGroup.value = database.getGroupByGroupId(group_id)
        }
    }

    fun isRoot(): Boolean {
        return currentGroup.value?.groupId == 1L
    }

    private suspend fun setDefaultDB() {
        database.itemInsert(Item(itemName = "12600k", price = 200))
        database.itemInsert(Item(itemName = "12900k", price = 500))
        database.itemInsert(Item(itemName = "5600X", price = 200))
        database.itemInsert(Item(itemName = "5950X", price = 600))
        database.itemInsert(Item(itemName = "DDR4 16GB", price = 100))
        database.itemInsert(Item(itemName = "DDR4 32GB", price = 200))
        database.itemInsert(Item(itemName = "DDR5 32GB", price = 300))
        database.itemInsert(Item(itemName = "Bed", price = 200))
        database.itemInsert(Item(itemName = "Sofa", price = 100))

        database.groupInsert(Group(groupName = "Home", higherGroupId = null))
        database.groupInsert(Group(groupName = "PC", higherGroupId = 1))
        database.groupInsert(Group(groupName = "CPU", higherGroupId = 2))
        database.groupInsert(Group(groupName = "RAM", higherGroupId = 2))
        database.groupInsert(Group(groupName = "House", higherGroupId = 1))

        database.valueInsert(Value(itemId = 1, groupId = 3))
        database.valueInsert(Value(itemId = 2, groupId = 3))
        database.valueInsert(Value(itemId = 3, groupId = 3))
        database.valueInsert(Value(itemId = 4, groupId = 3))
        database.valueInsert(Value(itemId = 5, groupId = 4))
        database.valueInsert(Value(itemId = 6, groupId = 4))
        database.valueInsert(Value(itemId = 7, groupId = 4))
        database.valueInsert(Value(itemId = 8, groupId = 5))
        database.valueInsert(Value(itemId = 9, groupId = 5))

        Timber.i("default insert completed")
    }
}
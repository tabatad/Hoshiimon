package com.tabata.hoshiimon.database

import androidx.room.*

@Dao
interface AppDao {
    @Insert
    suspend fun itemInsert(item: Item)

    @Insert
    suspend fun groupInsert(group: Group)

    @Insert
    suspend fun valueInsert(value: Value)

    @Query("SELECT * FROM item LIMIT 1")
    suspend fun selectItem(): Item?

    @Query("SELECT * FROM 'group' WHERE higherGroupId IS NULL")
    suspend fun getRootGroups(): List<Group>

    @Query("SELECT * FROM 'group' WHERE higherGroupId = :groupId")
    suspend fun getHigherGroup(groupId: Long?): List<Group>
}
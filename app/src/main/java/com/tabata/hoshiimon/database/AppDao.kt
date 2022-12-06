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
    suspend fun getRootGroup(): Group

    @Query("SELECT * FROM 'group' WHERE higherGroupId = :groupId")
    suspend fun getLowerGroup(groupId: Long?): List<Group>

    @Query("SELECT * FROM 'group' WHERE groupId = :higherGroupId")
    suspend fun getHigherGroup(higherGroupId: Long?): Group

    @Query("SELECT itemId, itemName, price FROM item NATURAL JOIN 'group' NATURAL JOIN value WHERE groupId = :groupId")
    suspend fun getItemsByGroupId(groupId: Long?): List<Item>
}
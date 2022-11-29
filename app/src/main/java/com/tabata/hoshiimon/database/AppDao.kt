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

    @Query("SELECT * FROM item WHERE itemId = 1 LIMIT 1")
    suspend fun selectItem(): Item?
}
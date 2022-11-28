package com.tabata.hoshiimon.database

import androidx.room.*

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val itemId: Long,
    val itemName: String,
    val price: Long
)

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val groupId: Long,
    val groupName: String,
    val higherGroupId: Long
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = arrayOf("itemId"),
            childColumns = arrayOf("itemId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Group::class,
            parentColumns = arrayOf("groupId"),
            childColumns = arrayOf("groupId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Value(
    @PrimaryKey(autoGenerate = true) val valueId: Long,
    val itemId: Long,
    val groupId: Long
)

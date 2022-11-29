package com.tabata.hoshiimon.database

import androidx.room.*

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val itemId: Long = 0,
    val itemName: String?,
    val price: Long?
)

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val groupId: Long = 0,
    val groupName: String?,
    val higherGroupId: Long?
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
    ],
    indices = [
        Index("itemId"),
        Index("groupId")
    ]
)
data class Value(
    @PrimaryKey(autoGenerate = true) val valueId: Long = 0,
    val itemId: Long,
    val groupId: Long
)

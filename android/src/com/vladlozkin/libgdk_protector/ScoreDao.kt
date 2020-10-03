package com.vladlozkin.libgdk_protector

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score")
    fun getAll(): List<Score>

    @Query("SELECT * FROM score WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Score>

    @Query("SELECT * FROM score WHERE user_name LIKE :username")
    fun findByName(username: String): Score

    @Insert
    fun insertAll(vararg scores: Score)

    @Delete
    fun delete(score: Score)
}
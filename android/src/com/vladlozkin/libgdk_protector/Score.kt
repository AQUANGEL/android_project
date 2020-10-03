package com.vladlozkin.libgdk_protector

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey var id: Int = 0,
    var user_name : String? = null,
    var score : Int = 0
) {

}
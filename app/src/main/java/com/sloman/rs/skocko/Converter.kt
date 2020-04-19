package com.sloman.rs.skocko

import androidx.room.TypeConverter
import com.sloman.rs.skocko.Constants.SEPARATOR

class Converter {

    @TypeConverter
    fun parseListFromString(hit: String): List<Int> {
        val list = mutableListOf<Int>()

        val array = hit.split(SEPARATOR.toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun parseStringFromList(list: List<Int>): String {
        var s = ""
        for (i in list) s += "$i$SEPARATOR"
        return s
    }

}


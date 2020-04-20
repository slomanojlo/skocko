package com.sloman.rs.skocko.util

import androidx.room.TypeConverter
import com.sloman.rs.skocko.util.Constants.SEPARATOR

class Converter {

    /** Used for converting [String] to [List] when accessing DB */
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


    /** Used for converting [List] to [String] when accessing DB */
    @TypeConverter
    fun parseStringFromList(list: List<Int>): String {
        var s = ""
        for (i in list) s += "$i$SEPARATOR"
        return s
    }

}


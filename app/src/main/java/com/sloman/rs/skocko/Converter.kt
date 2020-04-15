package com.sloman.rs.skocko

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun gettingListFromString(hit: String): List<Int> {
        val list = mutableListOf<Int>()

        val array = hit.split(",".toRegex()).dropLastWhile {
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
    fun writingStringFromList(list: List<Int>): String {
        var s = ""
        for (i in list) s += "$i,"
        return s
    }

}
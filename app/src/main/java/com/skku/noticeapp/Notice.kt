package com.skku.noticeapp

import androidx.room.Entity

@Entity
class Notice(val no: String = "",
             val name: String = "",
             val link: String = "",
             val date: String = "",
             val read: String = "",
             val is_new: String = ""){

}
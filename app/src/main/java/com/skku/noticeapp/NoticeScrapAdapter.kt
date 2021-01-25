package com.skku.noticeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class NoticeScrapAdapter(val noticeList: ArrayList<Notice>) : RecyclerView.Adapter<NoticeScrapAdapter.CustomViewHolder>()
{
    lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeScrapAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scrap_list_item, parent, false)
        parentContext = parent.context
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: NoticeScrapAdapter.CustomViewHolder, position: Int) {
        holder.nameText.text = noticeList.get(position).name
        holder.readText.text = noticeList.get(position).read.toString()
        holder.readText1.text = "조회수:"

        holder.nameText.setOnClickListener {
            val webIntent = Intent(holder.itemView.context, WebActivity::class.java)
            webIntent.putExtra("link", noticeList.get(position).link)
            startActivity(holder.itemView.context, webIntent, null)
        }

        val innerDb = Room.databaseBuilder(
            parentContext.applicationContext,
            AppDatabase::class.java, "notice"
        ).allowMainThreadQueries().build()

        holder.scrapCancelButton.setOnClickListener {
            innerDb.noticeDao().delete(noticeList.get(position))
            noticeList.remove(noticeList.get(position))
            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "스크랩 취소", Toast.LENGTH_SHORT).show()
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.scrap_name_text)
        val readText = itemView.findViewById<TextView>(R.id.scrap_read_text)
        val scrapCancelButton = itemView.findViewById<Button>(R.id.scrap_cancel_button)
        val readText1 = itemView.findViewById<TextView>(R.id.scrap_read_text1)
    }

}
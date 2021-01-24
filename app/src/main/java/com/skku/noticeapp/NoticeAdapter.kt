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

class NoticeAdapter(val noticeList: ArrayList<Notice>) : RecyclerView.Adapter<NoticeAdapter.CustomViewHolder>()
{
    lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        parentContext = parent.context
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: NoticeAdapter.CustomViewHolder, position: Int) {
        holder.noText.text = noticeList.get(position).no.toString()
        holder.nameText.text = noticeList.get(position).name
        holder.readText.text = noticeList.get(position).read.toString()
        holder.dateText.text = noticeList.get(position).date
        holder.readText1.text = "조회수:"
        holder.dateText1.text = "등록일:"

        holder.nameText.setOnClickListener {
            val webIntent = Intent(holder.itemView.context, WebActivity::class.java)
            webIntent.putExtra("link", noticeList.get(position).link)
            startActivity(holder.itemView.context, webIntent, null)
        }

        val innerDb = Room.databaseBuilder(
            parentContext.applicationContext,
            AppDatabase::class.java, "notice"
        ).allowMainThreadQueries().build()

        holder.scrapButton.setOnClickListener {
            innerDb.noticeDao().insert(noticeList.get(position))
            Toast.makeText(holder.itemView.context, "스크랩 완료", Toast.LENGTH_SHORT).show()
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var view: View = itemView
        val noText = itemView.findViewById<TextView>(R.id.no_text)
        val nameText = itemView.findViewById<TextView>(R.id.scrap_name_text)
        val readText = itemView.findViewById<TextView>(R.id.scrap_read_text)
        val dateText = itemView.findViewById<TextView>(R.id.date_text)
        val scrapButton = itemView.findViewById<Button>(R.id.scrap_cancel_button)
        val readText1 = itemView.findViewById<TextView>(R.id.scrap_read_text1)
        val dateText1 = itemView.findViewById<TextView>(R.id.date_text1)
    }

}
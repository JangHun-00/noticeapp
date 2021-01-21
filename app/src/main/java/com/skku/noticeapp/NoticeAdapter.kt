package com.skku.noticeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoticeAdapter(val noticeList: ArrayList<Notice>) : RecyclerView.Adapter<NoticeAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: NoticeAdapter.CustomViewHolder, position: Int) {
        holder.noText.text = noticeList.get(position).no
        holder.nameText.text = noticeList.get(position).name
        holder.readText.text = noticeList.get(position).read
        holder.dateText.text = noticeList.get(position).date
        holder.readText1.text = "조회수:"
        holder.dateText1.text = "등록일:"
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noText = itemView.findViewById<TextView>(R.id.no_text)
        val nameText = itemView.findViewById<TextView>(R.id.name_text)
        val readText = itemView.findViewById<TextView>(R.id.read_text)
        val dateText = itemView.findViewById<TextView>(R.id.date_text)
        val scrapButton = itemView.findViewById<Button>(R.id.scrap_button)
        val readText1 = itemView.findViewById<TextView>(R.id.read_text1)
        val dateText1 = itemView.findViewById<TextView>(R.id.date_text1)
    }

}
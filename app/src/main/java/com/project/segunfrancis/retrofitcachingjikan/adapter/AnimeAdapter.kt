package com.project.segunfrancis.retrofitcachingjikan.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.segunfrancis.retrofitcachingjikan.R
import com.project.segunfrancis.retrofitcachingjikan.data.Episode
import kotlinx.android.synthetic.main.anime_item_list.view.*
import java.util.*

/**
 * Created by SegunFrancis
 */
class AnimeAdapter : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var data: List<Episode>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.anime_item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (data != null)
            data!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

    fun setData(data: List<Episode>?) {
        this.data = data
        notifyDataSetChanged()
    }

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Episode?) = with(itemView) {
            title.text = item?.title?.trim()
            date.text = item?.aired?.substring(0, item.aired.indexOf("T"))

            videoUrlButton.setOnClickListener {
                Toast.makeText(context, "${item?.videoUrl} clicked", Toast.LENGTH_LONG).show()
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(item?.videoUrl)
                context.startActivity(Intent.createChooser(intent, "Select Browser"))
            }
        }
    }
}
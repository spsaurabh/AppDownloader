package com.example.appdownloader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdownloader.databinding.DownloadOptionLayoutBinding
import com.example.appdownloader.model.SubCategory

class AppListAdapter(val context: Context,val downloadList:ArrayList<SubCategory>,
                     private var onItemClicked: ((movie: SubCategory) -> Unit)):
    RecyclerView.Adapter<AppListAdapter.DownloadViewHolder>() {
    class DownloadViewHolder(val binding: DownloadOptionLayoutBinding):
        RecyclerView.ViewHolder(binding.root){}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val binding = DownloadOptionLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DownloadViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return downloadList.size
    }
    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(context)
                .load(downloadList[position].icon)
                .into(ivIcon)
            tvTitle.text=downloadList[position].name
            tvInstallrange.text=downloadList[position].installedRange
            btnDownload.setOnClickListener {
                onItemClicked(downloadList[position])
            }
        }
    }
}
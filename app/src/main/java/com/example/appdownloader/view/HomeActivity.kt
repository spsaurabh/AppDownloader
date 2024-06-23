package com.example.appdownloader.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdownloader.adapter.AppListAdapter
import com.example.appdownloader.constants.ApiUrl
import com.example.appdownloader.databinding.ActivityHomeBinding
import com.example.appdownloader.model.AppMainObj
import com.example.appdownloader.model.SubCategory
import com.example.appdownloader.viewmodel.DownloadViewModel
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: DownloadViewModel
    private lateinit var binding:ActivityHomeBinding
    private lateinit var optArrayList : ArrayList<SubCategory>
    private lateinit var appRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this)[DownloadViewModel::class.java]
        appRecyclerView=binding.rvAppList
        optArrayList=ArrayList()
        appRecyclerView.layoutManager = LinearLayoutManager(this)
        appRecyclerView.adapter = AppListAdapter(this,optArrayList){
            if(it!=null){
                val packageName = "${it.appLink}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                // Check if Play Store app is installed
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    // Open the Play Store listing in a web browser (optional)
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                    startActivity(webIntent)
                }
            }else{
                Toast.makeText(this,"Something Wen't Wrong, Try Again Later",Toast.LENGTH_SHORT).show()
            }
        }
        initViews()
    }
    private fun initViews() {
        viewModel.hitHomePageApi(ApiUrl.API_HOME_PAGE)
        viewModel.apiObj.observe(this,Observer{
            if (it!=null){
                val getData = Gson().fromJson(it,AppMainObj::class.java)
                for(i in 0 until getData.appCenter.size){
                    if(getData.appCenter[i].name == "POPULAR GAMES"){
                        optArrayList.addAll(getData.appCenter[i].subCategory)
                    }
                }
                binding.rvAppList.adapter?.notifyDataSetChanged()
            }
        })
    }
}
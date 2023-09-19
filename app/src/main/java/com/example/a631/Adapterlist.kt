package com.example.a631

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a631.databinding.ItemRvBinding

class Adapterlist(var onClik: OnClik):ListAdapter<NewsUser,Adapterlist.VH>(MyDiffUtill()) {

    inner class VH(var itemview : ItemRvBinding):RecyclerView.ViewHolder(itemview.root){

        fun onBind(user: NewsUser){

            itemview.sarlavxa.text = user.sarlavxa
            itemview.matni.text = user.matni
            itemview.btn.setOnClickListener {
                onClik.click(user,itemview.root)
            }
            itemview.btnView.setOnClickListener {
                onClik.clickview(user)
            }
        }

    }

    class MyDiffUtill:DiffUtil.ItemCallback<NewsUser>(){
        override fun areItemsTheSame(oldItem: NewsUser, newItem: NewsUser): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsUser, newItem: NewsUser): Boolean {
           return  oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    interface OnClik{
        fun click(contact: NewsUser,btn: View){

        }
        fun clickview(contact: NewsUser){

        }
    }


}
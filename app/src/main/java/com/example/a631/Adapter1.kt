package com.example.a631

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a631.databinding.ItemRvBinding

class Adapter1(var contactlist:Userlist, var onClik: OnClik): RecyclerView.Adapter<Adapter1.rvadapterholder>() {
    inner class rvadapterholder(var itemview : ItemRvBinding):RecyclerView.ViewHolder(itemview.root){

        fun onbain(contact:NewsUser,position: Int){
            itemview.sarlavxa.text = contact.sarlavxa
            itemview.matni.text = contact.matni
            itemview.btn.setOnClickListener {
                onClik.click(contact,position,itemview.btn)
            }

            itemview.btnView.setOnClickListener {
                onClik.clickview(contact,position)
            }
//            itemview.image_item.setImageResource(contact.image!!)
//            Picasso.with(context).load(contact.image).into(itemview.image_item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvadapterholder {
        return rvadapterholder(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: rvadapterholder, position: Int) {
        var contact =contactlist.list!![position]
        holder.onbain(contact,position)

    }

    override fun getItemCount(): Int {
        return contactlist.list!!.size

    }

    interface OnClik{
        fun click(contact: NewsUser,position: Int,btn:View){

        }
        fun clickview(contact: NewsUser,position: Int){

        }
    }

}
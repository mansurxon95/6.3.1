package com.example.a631
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class P2Adapter(var list:ArrayList<Userlist>, fragmentManager: FragmentActivity):FragmentStateAdapter(fragmentManager){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var list1 = ArrayList<NewsUser>()
        var list2 = ArrayList<NewsUser>()
        var list3 = ArrayList<NewsUser>()
        if (list.isEmpty()){
           list1.add(NewsUser("","",0))
           list2.add(NewsUser("","",1))
           list3.add(NewsUser("","",2))
           list.add(Userlist(list1))
           list.add(Userlist(list2))
           list.add(Userlist(list3))
        }
      return  PageritemFragment.newInstance(list[position])
    }

}
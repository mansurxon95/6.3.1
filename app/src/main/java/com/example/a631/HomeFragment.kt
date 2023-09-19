package com.example.a631

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.a631.databinding.FragmentCustomDialogBinding
import com.example.a631.databinding.FragmentHomeBinding
import com.example.a631.db.MyDb
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.selects.select

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var pos: Int? = null
    lateinit var binding: FragmentHomeBinding
    lateinit var list2: ArrayList<Userlist>
    lateinit var list00: ArrayList<NewsUser>
    lateinit var list01: ArrayList<NewsUser>
    lateinit var list02: ArrayList<NewsUser>
    lateinit var myDb:MyDb
    lateinit var vpadapter:P2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        myDb = MyDb(binding.root.context)

       addtolbar()

        addtabdb()




//        list2.add(myDb.getallnews(0))
//            list2.add(myDb.getallnews(1))
//            list2.add(myDb.getallnews(2))


        getviev(getallnews())



        return binding.root
        }

    private fun getviev(getallnews:ArrayList<Userlist>) {
        if (getallnews.isEmpty()){
            binding.viewpagerView.visibility = View.INVISIBLE
        }else  {
            binding.viewpagerView.visibility = View.VISIBLE
        }

        vpadapter = P2Adapter(getallnews,requireActivity())
        binding.viewpagerView.adapter = vpadapter
        TabLayoutMediator(binding.tabView,binding.viewpagerView){tab, position ->
            tab.text = myDb.getalltab()[position]
        }.attach()
        binding.viewpagerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                pos = position
                super.onPageSelected(position)
            }
        })




    }


    private fun getallnews():ArrayList<Userlist> {
        list2 = ArrayList()
        list00 = ArrayList()
        list01 = ArrayList()
        list02 = ArrayList()

        try {
            for (i in myDb.getallnews2().list!!){
                if (i.tabid==0){
                    list00.add(i)
                }else if (i.tabid==1){
                    list01.add(i)
                }else if (i.tabid==2){
                    list02.add(i)
                }
            }

            list2.add(Userlist(list00))
            list2.add(Userlist(list01))
            list2.add(Userlist(list02))
        }catch (e:Exception){
            Toast.makeText(binding.root.context, "Malumotlar topilmadi", Toast.LENGTH_SHORT).show()
        }

        return list2
    }

    private fun addtabdb() {
        if (myDb.getalltab().isEmpty()){
            myDb.addtab("Asosiy")
            myDb.addtab("Dunyo")
            myDb.addtab("Ijtimoiy")
        }
    }

    private fun addtolbar() {
        var activity = activity as MainActivity

        binding.toolbar.title = "Bootcamp news"
        activity.setSupportActionBar(binding.toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addmenu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("@@@@", "onOptionsItemSelected: ${item.itemId}")
        if (item.itemId==R.id.btn_add){
            custdialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun custdialog() {
        myDb = MyDb(binding.root.context)
        var alertDialog = AlertDialog.Builder(binding.root.context)
        var lv = FragmentCustomDialogBinding.inflate(layoutInflater,null,false)

        alertDialog.setCancelable(false)
        alertDialog.setView(lv.root)
        var adapter = ArrayAdapter(binding.root.context,android.R.layout.simple_spinner_item,myDb.getalltab())
        lv.spinner.adapter = adapter

        alertDialog.setPositiveButton("Saqlash"
        ) { dialog, which ->

            var sarlavha = lv.sarlavha.text.toString()
            var matni = lv.matni.text.toString()
            var tabid = lv.spinner.selectedItemPosition

            if (sarlavha.trim()!=""&&matni.trim()!=""){
            myDb.addnews(NewsUser(sarlavha,matni,tabid))
            }else {
                Toast.makeText(
                    binding.root.context,
                    "Barcha maydonlarni to'ldirish shart",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Toast.makeText(binding.root.context, "Malumot saqlandi", Toast.LENGTH_SHORT).show()

            if (getallnews().isNotEmpty()){
                binding.viewpagerView.visibility = View.VISIBLE
            }

            getviev(getallnews())




            var count = "${getallnews()[0].list?.size}${getallnews()[1].list?.size}${getallnews()[2].list?.size}"
            Log.d("@@@@", "custdialog:1 ${count}")

            if (pos!!!=null&&count!="100"&&count!="010"&&count!="001"){
            binding.viewpagerView.setCurrentItem(pos!!)
        }


        }

        alertDialog.setNegativeButton("Bekor qilish"
        ) { dialog, which ->  }
        alertDialog.show()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

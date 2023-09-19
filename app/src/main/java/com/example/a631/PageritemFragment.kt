package com.example.a631

import android.app.AlertDialog
import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.example.a631.databinding.FragmentCustomDialogBinding
import com.example.a631.databinding.FragmentPageritemBinding
import com.example.a631.db.MyDb
import com.example.a631.db.MyDbservis

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [PageritemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageritemFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Userlist? = null
    lateinit var binding: FragmentPageritemBinding
    lateinit var popupMenu: PopupMenu
    lateinit var myDb: MyDb
    lateinit var adapterlist: Adapterlist
    lateinit var list: ArrayList<NewsUser>
//    lateinit var adapter: Adapter1

    constructor(parcel: android.os.Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Userlist
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPageritemBinding.inflate(layoutInflater, container, false)

        adapterlist = Adapterlist(object : Adapterlist.OnClik{

            override fun click(contact: NewsUser, btn: View) {
                popupMenu = PopupMenu(binding.root.context, btn)
                popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    if (menuItem.title == "O’zgartirish") {
                        custdialogadd(contact)
                    } else {
                        custdialog(contact)
                    }



                    true
                }
                popupMenu.show()

                super.click(contact, btn)
            }

            override fun clickview(contact: NewsUser) {
                var bundle = Bundle()
                bundle.putSerializable("contact", contact)
                findNavController().navigate(R.id.getFragment, bundle)

                super.clickview(contact)

            }

        })

//        adapter = Adapter1(param1!!, object : Adapter1.OnClik {
//            override fun click(contact: NewsUser, position: Int, btn: View) {
//                popupMenu = PopupMenu(binding.root.context, btn)
//                popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
//                popupMenu.setOnMenuItemClickListener { menuItem ->
//                    if (menuItem.title == "O’zgartirish") {
//                        custdialogadd(contact,position)
//                    } else {
//                        custdialog(contact)
//                    }
//
//
//
//                    true
//                }
//                popupMenu.show()
//
//                super.click(contact, position, btn)
//            }
//
//            override fun clickview(contact: NewsUser, position: Int) {
//                var bundle = Bundle()
//                bundle.putSerializable("contact", contact)
//                findNavController().navigate(R.id.getFragment, bundle)
//
//                super.clickview(contact, position)
//
//            }
//        })

        list = ArrayList()
        list = param1!!.list!!
        adapterlist.submitList(list)

        binding.rvView.adapter = adapterlist

        return binding.root
    }

    fun custdialogadd(contact: NewsUser) {
        myDb = MyDb(binding.root.context)
        var alertDialog = AlertDialog.Builder(binding.root.context)
        var lv = FragmentCustomDialogBinding.inflate(layoutInflater, null, false)
        lv.customText.text = "O’zgartirish"
        lv.sarlavha.hint = contact.sarlavxa
        lv.matni.hint = contact.matni

        alertDialog.setCancelable(false)
        alertDialog.setView(lv.root)
        var adapter = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_spinner_item,
            myDb.getalltab()
        )

        lv.spinner.adapter = adapter
        lv.spinner.setSelection(adapter.getPosition(myDb.getalltab()[contact.tabid!!]))

        alertDialog.setPositiveButton(
            "Saqlash"
        ) { dialog, which ->

            var sarlavha = lv.sarlavha.text.toString()
            var matni = lv.matni.text.toString()
            var tabid = lv.spinner.selectedItemPosition

            myDb.editnews(NewsUser(contact.id, sarlavha, matni, tabid))

            Toast.makeText(binding.root.context, "Malumot o'zgartirildi", Toast.LENGTH_SHORT).show()

            list.set(list.indexOf(contact),NewsUser(contact.id, sarlavha, matni, tabid))
            adapterlist.notifyDataSetChanged()

        }

        alertDialog.setNegativeButton(
            "Bekor qilish"
        ) { dialog, which -> }
        alertDialog.show()



    }

    fun custdialog(contact: NewsUser) {
        myDb = MyDb(binding.root.context)
        var alertDialog = AlertDialog.Builder(binding.root.context)
        alertDialog.setCancelable(false)
        alertDialog.setMessage("Xabarni o’chirmoqchimisiz?")

        alertDialog.setPositiveButton(
            "O’chirish"
        ) { dialog, which ->


            myDb.deletnews(contact)
            Toast.makeText(binding.root.context, "Malumot o'chirildi", Toast.LENGTH_SHORT).show()
            list.remove(contact)
            adapterlist.notifyDataSetChanged()

        }
        alertDialog.setNegativeButton("Bekor qilish") { dialog, which ->

        }
        alertDialog.show()


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment PageritemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Userlist) =
            PageritemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }


}
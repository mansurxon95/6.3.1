package com.example.a631

import android.R
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.example.a631.databinding.FragmentCustomDialogBinding
import com.example.a631.databinding.FragmentGetBinding
import com.example.a631.db.MyDb

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentGetBinding
    lateinit var myDb:MyDb

    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding= FragmentGetBinding.inflate(layoutInflater,container,false)

        var activity = activity as MainActivity

        binding.toolbar.title = "Bootcamp news"
        activity.setSupportActionBar(binding.toolbar)

        var arg = arguments?.getSerializable("contact") as NewsUser
        binding.tavsifView.text = arg.sarlavxa
        binding.matniView.text = arg.matni

        binding.btnEdit.setOnClickListener {

            custdialogadd(arg)

        }

        return binding.root
    }

    fun custdialogadd(contact:NewsUser) {
        myDb = MyDb(binding.root.context)
        var alertDialog = AlertDialog.Builder(binding.root.context)
        var lv = FragmentCustomDialogBinding.inflate(layoutInflater,null,false)
        lv.customText.text = "O’zgartirish"
        lv.sarlavha.hint = contact.sarlavxa
        lv.matni.hint = contact.matni

        alertDialog.setCancelable(false)
        alertDialog.setView(lv.root)
        var adapter = ArrayAdapter(binding.root.context, R.layout.simple_spinner_item,myDb.getalltab())
        lv.spinner.setSelection(adapter.getPosition(myDb.getalltab()[contact.tabid!!]))
        lv.spinner.adapter = adapter

        alertDialog.setPositiveButton("Saqlash"
        ) { dialog, which ->

            var sarlavha = lv.sarlavha.text.toString()
            var matni = lv.matni.text.toString()
            var tabid = lv.spinner.selectedItemPosition


            myDb.editnews(NewsUser(sarlavha,matni,tabid))

            Toast.makeText(binding.root.context, "Malumot o'zgartirildi", Toast.LENGTH_SHORT).show()
            binding.tavsifView.text = sarlavha
            binding.matniView.text = matni



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
         * @return A new instance of fragment GetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
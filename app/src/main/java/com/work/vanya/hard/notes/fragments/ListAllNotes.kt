package com.work.vanya.hard.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.work.vanya.hard.notes.R

import com.work.vanya.hard.notes.databinding.ListAllNotesFragmentBinding

class ListAllNotes : Fragment() {


     private var _binding : ListAllNotesFragmentBinding? = null
     private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    val arrayList = ArrayList<HashMap<String, String>>()


    private lateinit var myListMain : ListView

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = ListAllNotesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


       myListMain = root.findViewById(R.id.my_main_list)
             val navLos =  this.findNavController()

       val  share = requireParentFragment().requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)



            if(share.getString("main", "")!!.isNotEmpty()) {

                db.collection(share.getString("main", "")!!)
                    .get()
                    .addOnSuccessListener { result ->


                        if (arrayList.isNotEmpty()) {
                            arrayList.clear()
                        }


                        for (document in result) {

                            val map: HashMap<String, String> = HashMap()

                            map["Zagolovok"] = document.data["Big"].toString()
                            map["main_text"] = document.data["text_main"].toString()
                            map["id_doc"] = document.id


                            println(" sdfsdf ! ")
                            arrayList.add(map)
                        }


                        println(" size = ${arrayList.size}")

                        val adap = SimpleAdapter(
                            binding.root.context, arrayList,
                            R.layout.layout_all_not_see, arrayOf("Zagolovok", "main_text"),
                            intArrayOf(R.id.one_view, R.id.two_view)
                        )

                        myListMain.adapter = adap
                        myListMain.divider = null


                    }

            }



                 myListMain.onItemClickListener  = AdapterView.OnItemClickListener { parent, view, position, id ->

               // println(" my arrayPos Select == ${arrayList[position]["mainText"]} ")
               // println(" position and id =${position} , ${id}")

                   val df = share.edit()

                                 df.let {
                                     df.putString("selectVar", arrayList[position]["id_doc"].toString())
                                 }.apply()

                //

                navLos.navigate(R.id.move_to_see_edit)
            }



            return root
        }



        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
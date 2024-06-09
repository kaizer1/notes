package com.work.vanya.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.work.vanya.notes.R

import com.work.vanya.notes.databinding.ListAllNotesFragmentBinding

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

       val  share = requireParentFragment().requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)



                  db.collection(share.getString("main", "")!!)
                .get()
                .addOnSuccessListener { result ->


                     if(arrayList.isNotEmpty()){
                         arrayList.clear()
                     }


                      for (document in result ) {

                          val map: HashMap<String, String> = HashMap()

                          map["Zagolovok"] = document.data["Big"].toString()
                          map["main_text"] = document.data["text_main"].toString()

                          println(" sdfsdf ! ")
                          arrayList.add(map)
                      }


                     println(" size = ${arrayList.size}")

     val adap = SimpleAdapter(binding.root.context, arrayList,
                                  R.layout.layout_all_not_see, arrayOf("Zagolovok", "main_text"),
                                  intArrayOf(R.id.one_view, R.id.two_view)
                              )

                     myListMain.adapter = adap
                     myListMain.divider = null


                }




            return root
        }



        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
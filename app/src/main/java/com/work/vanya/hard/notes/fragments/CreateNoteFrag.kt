package com.work.vanya.hard.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.work.vanya.hard.notes.R
import com.work.vanya.hard.notes.databinding.CreateNoteFragmentBinding

class CreateNoteFrag : Fragment() {

          private var _binding : CreateNoteFragmentBinding? = null
     private val binding get() = _binding!!
    lateinit var editMainBig : EditText
    lateinit var editMainText : EditText
        private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

           _binding = CreateNoteFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        editMainBig = root.findViewById(R.id.edit_bigzagol)
        editMainText = root.findViewById(R.id.edit_maintext)


        val  share = requireParentFragment().requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)







        val SaveButton = root.findViewById<Button>(R.id.id_save).setOnClickListener {

              if(editMainBig.text.isEmpty()  || editMainText.text.isEmpty()){
                // Toast ( all field are not empty )

                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, " all field are not empty", duration)
                         toast.show()

            }else {

   val usersdlkfj = hashMapOf(
             "Big" to editMainBig.text.toString(),
             "text_main" to editMainText.text.toString(),

                    )

                   db.collection(share.getString("main", "")!!)
                        .add(usersdlkfj)
                     .addOnSuccessListener {
                println("Success save all my data")

                              Toast.makeText(context, R.string.ok_add, Toast.LENGTH_SHORT).show()
                         editMainText.text.clear()
                         editMainBig.text.clear()
            }
            .addOnFailureListener {
                println(" no save data  ")
                Toast.makeText(context, R.string.failure_add, Toast.LENGTH_SHORT).show()
            }

              }
        }











        return root
    }
}
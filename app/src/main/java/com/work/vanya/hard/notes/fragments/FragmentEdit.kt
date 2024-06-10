package com.work.vanya.hard.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.work.vanya.hard.notes.R
import com.work.vanya.hard.notes.databinding.EditFragmentBinding

class FragmentEdit : Fragment() {

            private var _binding : EditFragmentBinding? = null
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


             _binding = EditFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root



             val navLos =  this.findNavController()

             val editZagolo = root.findViewById<EditText>(R.id.zagolo_edit)
             val editMainT = root.findViewById<EditText>(R.id.main_t_edit)
             val  share = requireParentFragment().requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)



        if(share.getString("main", "")!!.isNotEmpty()) {
            db.collection(share.getString("main", "")!!)
                .document(share.getString("selectVar", "")!!)
                .get()
                .addOnSuccessListener { it ->
                    editZagolo.setText(it.data!!["Big"].toString())
                    editMainT.setText(it.data!!["text_main"].toString())
                }

        }


             val buttonRemove = root.findViewById<Button>(R.id.remove_n).setOnClickListener {

                         db.collection(share.getString("main", "")!!).document(share.getString("selectVar", "")!!)
                             .delete()

                 navLos.navigate(R.id.back_too)
             }


            val buttonUpdate = root.findViewById<Button>(R.id.sabe_upda).setOnClickListener {

  if(editZagolo.text.isEmpty()  || editMainT.text.isEmpty()){

                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, " all field are not empty", duration)
                         toast.show()

            }else {

   val usersdlkfj = hashMapOf(
             "Big" to editZagolo.text.toString(),
             "text_main" to editMainT.text.toString()
   )

                db.collection(share.getString("main", "")!!).document(share.getString("selectVar", "")!!)
                             .set(usersdlkfj)
                    .addOnSuccessListener {

                        val toast = Toast.makeText(context, " You's note is succesful update", Toast.LENGTH_SHORT)
                         toast.show()

                    }

                }
            }

            val butRetur = root.findViewById<ImageButton>(R.id.back_main_frag).setOnClickListener {
               navLos.navigate(R.id.back_too)
            }




         return root
    }
}
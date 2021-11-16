package com.shivank.notesapp

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.android.gms.tasks.OnCompleteListener
import androidx.annotation.NonNull








class NotesFragment : Fragment(),EditClickInterface, NoteClickDeleteInterface {
    private lateinit var viewModal: NoteViewModal
    private lateinit var notesRV: RecyclerView
    private lateinit var addNewNote: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_notes, container, false)
        notesRV = view.findViewById(R.id.notesRV)
        addNewNote = view.findViewById(R.id.idAddNewNote)
        notesRV.layoutManager = LinearLayoutManager(context)

        val noteRVAdapter = RVAdapter(context , this, this)
        notesRV.adapter = noteRVAdapter
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(NoteViewModal::class.java)
        viewModal.allNotes.observe(viewLifecycleOwner, { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })
        addNewNote.setOnClickListener {
            changeFragment(AddNotesFragment())
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_singout,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.log_out){
            signOut()

        }
        return super.onOptionsItemSelected(item)
    }
    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut()
        Toast.makeText(context, "Log out success!", Toast.LENGTH_SHORT).show()
        changeFragment(GoogleLoginFragment())

    }
    override fun onEditClick(note: Notes) {
        val bundle = Bundle()
        bundle.putString("noteType", "edit")
        bundle.putString("noteTitle", note.noteTitle)
        bundle.putString("noteDescription", note.noteDescription)
        bundle.putInt("noteId", note.id)
        val fragmentTwo = AddNotesFragment()
        fragmentTwo.arguments = bundle
        changeFragment(fragmentTwo)

    }

    override fun onDeleteClick(note: Notes) {
        viewModal.deleteNote(note)
        Toast.makeText(context, "${note.noteTitle} Deleted.", Toast.LENGTH_SHORT).show()
    }
    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction?.commit()
    }
}
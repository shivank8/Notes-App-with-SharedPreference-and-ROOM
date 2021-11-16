package com.shivank.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AddNotesFragment : Fragment() {
    private lateinit var noteTitleEdt: EditText
    private lateinit var noteEdt: EditText
    private lateinit var saveBtn: Button
    private lateinit var viewModal: NoteViewModal
    private var noteID = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_add_notes, container, false)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(NoteViewModal::class.java)

        noteTitleEdt = view.findViewById(R.id.etNoteName)
        noteEdt = view.findViewById(R.id.etNoteDesc)
        saveBtn = view.findViewById(R.id.btnSaveNote)

        val noteType = arguments?.getString("noteType")
        if (noteType.equals("edit")) {
            val noteTitle = arguments?.getString("noteTitle")
            val noteDescription =arguments?.getString("noteDescription")
            noteID = arguments?.getInt("noteId", -1)!!
            saveBtn.text = getString(R.string.update_note)
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        }

        saveBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            if (noteType.equals("edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val updatedNote = Notes(noteTitle, noteDescription)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(context, "Note updated.", Toast.LENGTH_LONG).show()
                    changeFragment()
                }
            } else {

                when {
                    noteTitle.isEmpty() -> {
                        Toast.makeText(context, "Please enter note title!", Toast.LENGTH_LONG).show()
                    }
                    noteDescription.isEmpty() -> {
                        Toast.makeText(context, "Please enter note description!", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        viewModal.addNote(Notes(noteTitle, noteDescription))
                        Toast.makeText(context, "$noteTitle added.", Toast.LENGTH_LONG).show()
                        changeFragment()
                    }
                }

            }

        }
        return view
    }

    private fun changeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentContainer,NotesFragment())
        fragmentTransaction?.commit()
    }

}
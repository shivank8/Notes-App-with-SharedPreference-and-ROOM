package com.shivank.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(
    val context: Context?,
    private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val editClickInterface: EditClickInterface
) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    private val allNotes = ArrayList<Notes>()

    // on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val noteTV: TextView = itemView.findViewById(R.id.txtNoteTitle)
        val noteDS: TextView = itemView.findViewById(R.id.txtNoteDesc)
        val deleteIV: ImageView = itemView.findViewById(R.id.imgDelete)
        val editIV: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_single_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.noteTV.text = allNotes[position].noteTitle
        holder.noteDS.text = allNotes[position].noteDescription
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteClick(allNotes[position])
        }
        holder.editIV.setOnClickListener {
            editClickInterface.onEditClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Notes>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    fun onDeleteClick(note: Notes)
}

interface EditClickInterface {
    fun onEditClick(note: Notes)
}

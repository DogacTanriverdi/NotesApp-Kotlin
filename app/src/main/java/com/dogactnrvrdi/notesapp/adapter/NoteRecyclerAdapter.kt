package com.dogactnrvrdi.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dogactnrvrdi.notesapp.databinding.ListItemNoteBinding
import com.dogactnrvrdi.notesapp.model.Note

class NoteRecyclerAdapter : RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {

    class NoteViewHolder(
        private val binding: ListItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                noteTitleTV.text = note.title
                noteDescriptionTV.text = note.description
                val lastModifiedString = "Last Modified: ${note.createdDateFormatted}"
                noteCreatedDateTV.text = lastModifiedString
            }
        }
    }

    // Diff Util
    private val diffUtil = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var notes: List<Note>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    // Recycler Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
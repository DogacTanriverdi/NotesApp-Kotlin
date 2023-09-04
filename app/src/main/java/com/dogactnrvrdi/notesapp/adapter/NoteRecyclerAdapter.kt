package com.dogactnrvrdi.notesapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.ListItemNoteBinding
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.view.NotesFragment
import kotlin.coroutines.coroutineContext

class NoteRecyclerAdapter : RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {

    class NoteViewHolder(
        private val binding: ListItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(note: Note, context: Context) {
            binding.apply {
                noteTitleTV.text = note.title
                noteDescriptionTV.text = note.description
                val lastModifiedString =
                    context.getString(R.string.last_modified_capital_caps) + " " +
                            note.createdDateFormatted
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

    val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

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
        holder.bind(note, holder.itemView.context)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(note)
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    // On Item Click Listener
    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }
}
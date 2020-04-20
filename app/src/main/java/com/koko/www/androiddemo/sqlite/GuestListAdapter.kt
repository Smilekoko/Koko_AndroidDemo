package com.koko.www.androiddemo.sqlite

import android.content.Context
import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_GUEST_NAME
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.COLUMN_PARTY_SIZE
import com.koko.www.androiddemo.sqlite.WaitListContract.WaitListEntry.Companion.ID

class GuestListAdapter(private val context: Context, private var cursor: Cursor?) : RecyclerView.Adapter<GuestListAdapter.GuestViewHolder>() {


    init {
        Log.e("TAG", cursor?.count.toString() + "cursor数量")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.guest_list_item, parent, false)
        return GuestViewHolder(view)
    }


    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        if (!cursor!!.moveToPosition(position))
            return
        val name = cursor!!.getString(cursor!!.getColumnIndex(COLUMN_GUEST_NAME))
        val partySize = cursor!!.getInt(cursor!!.getColumnIndex(COLUMN_PARTY_SIZE))
        val id = cursor!!.getLong(cursor!!.getColumnIndex(ID))

        Log.e("koko", "id:$id ; guestName:$name ; partySizes$partySize")


        holder.nameTextView.text = name
        holder.partySizeTextView.text = partySize.toString()
        //用于不需要显示的数据
        holder.itemView.tag = id
    }

    override fun getItemCount(): Int {
        return cursor!!.count
    }

    inner class GuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        var partySizeTextView: TextView = itemView.findViewById(R.id.party_size_text_view)
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor
     */
    fun swapCursor(newCursor: Cursor?) {

        if (cursor != null) cursor!!.close()

        cursor = newCursor

        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged()
        }
    }
}

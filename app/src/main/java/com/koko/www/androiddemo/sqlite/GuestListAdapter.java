package com.koko.www.androiddemo.sqlite;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koko.www.androiddemo.R;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {
    private Cursor cursor;
    private Context context;


    public GuestListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        Log.e("TAG", cursor.getCount() + "curson数量");
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (!cursor.moveToPosition(position))
            return;
        String name = cursor.getString(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        Log.e("TAG", "名字" + name);
        int partySize = cursor.getInt(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        Log.e("TAG", partySize + "人数");
        long id = cursor.getLong(cursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(partySize));
        //用于不需要显示的数据
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class GuestViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView partySizeTextView;

        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            partySizeTextView = itemView.findViewById(R.id.party_size_text_view);
        }
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor
     */
    public void swapCursor(Cursor newCursor) {

        if (cursor != null) cursor.close();

        cursor = newCursor;

        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}

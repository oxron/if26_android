package fr.utt.if26t.minimalist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26t.minimalist.contract.MinimalistContract;

public class MinimalistAdapter extends RecyclerView.Adapter<MinimalistAdapter.MenuListViewHolder> {
    private Context mContext;
    private Cursor mCursor;


    public MinimalistAdapter(Context context, Cursor cursor) {
        mContext  = context;
        mCursor = cursor;
    }

    public class MenuListViewHolder extends RecyclerView.ViewHolder {

        public TextView nameList;

        public MenuListViewHolder(@NonNull View itemView) {
            super(itemView);

            nameList =itemView.findViewById(R.id.textview_title_list);
        }
    }

    @NonNull
    @Override
    public MenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new MenuListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String nameList = mCursor.getString(mCursor.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(MinimalistContract.ListEntry._ID));

        holder.nameList.setText(nameList);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

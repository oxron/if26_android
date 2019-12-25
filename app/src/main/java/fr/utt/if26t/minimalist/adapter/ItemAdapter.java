package fr.utt.if26t.minimalist.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26t.minimalist.R;
import fr.utt.if26t.minimalist.contract.MinimalistContract;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewHolder>{
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ItemAdapter(Context context, Cursor cursor) {
        mContext  = context;
        mCursor = cursor;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameItem;

        public ItemsViewHolder(@NonNull View itemView, final ItemAdapter.OnItemClickListener listener) {
            super(itemView);

            nameItem = itemView.findViewById(R.id.itemTextView);

            itemView.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    if (listener !=  null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemsViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String nameItem = mCursor.getString(mCursor.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(MinimalistContract.ListEntry._ID));

        holder.nameItem.setText(nameItem);
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

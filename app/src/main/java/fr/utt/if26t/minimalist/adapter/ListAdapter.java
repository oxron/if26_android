package fr.utt.if26t.minimalist.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.utt.if26t.minimalist.ListItemModel;
import fr.utt.if26t.minimalist.R;
import fr.utt.if26t.minimalist.contract.MinimalistContract;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MenuListViewHolder> {
    private final ArrayList<ListItemModel> dataList;
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ListAdapter(Context context, ArrayList<ListItemModel> dataList) {
        mContext  = context;
        //mCursor = cursor;
        this.dataList = dataList;
    }

    // un item du recycler view
    public class MenuListViewHolder extends RecyclerView.ViewHolder {

        public TextView nameList;

        public MenuListViewHolder(@NonNull View listView, final OnItemClickListener listener) {
            super(listView);
            listView.setTag(this);
            //listView.setOnClickListener((View.OnClickListener) mListener);
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) listView.getTag();
            final long id = viewHolder.getItemId();

            nameList = listView.findViewById(R.id.listTextView);

            listView.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    if (listener !=  null) {
//                        long id = mCursor.getLong(mCursor.getColumnIndex(MinimalistContract.ListEntry._ID));

                        int position = getAdapterPosition();

                        Long positionDatabase = dataList.get(position).getId();

//                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick((positionDatabase.intValue()));
//                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new MenuListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListViewHolder holder, int position) {
//        if (!mCursor.moveToPosition(position)) {
//            return;
//        }
//
//        String nameList = mCursor.getString(mCursor.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME));
//        long id = mCursor.getLong(mCursor.getColumnIndex(MinimalistContract.ListEntry._ID));
//
//        holder.nameList.setText(nameList);
//        holder.itemView.setTag(id);

        //TODO voir pour avoir un ensemble clé valeur avec index
        holder.nameList.setText(this.dataList.get(position).getName());
        holder.itemView.setTag(this.dataList.get(position).getId());

    }

    @Override
    public int getItemCount() {
//        return mCursor.getCount();
        return this.dataList.size();
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

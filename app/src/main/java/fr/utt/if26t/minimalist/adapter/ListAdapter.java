package fr.utt.if26t.minimalist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.utt.if26t.minimalist.model.ListModel;
import fr.utt.if26t.minimalist.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MenuListViewHolder> {
    private ArrayList<ListModel> dataList;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ListAdapter(Context context, ArrayList<ListModel> dataList) {
        mContext  = context;
        this.dataList = dataList;
    }

    // un item du recycler view
    public class MenuListViewHolder extends RecyclerView.ViewHolder {

        public TextView nameList;

        public MenuListViewHolder(@NonNull View listView, final OnItemClickListener listener) {
            super(listView);

            nameList = listView.findViewById(R.id.listTextView);

            listView.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    if (listener !=  null) {
                        int position = getAdapterPosition();
                        Long positionDatabase = dataList.get(position).getId();
                        listener.onItemClick((positionDatabase.intValue()));
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
        holder.nameList.setText(this.dataList.get(position).getName());
        holder.itemView.setTag(this.dataList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public void swapDataList(ArrayList<ListModel> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();
    }
}

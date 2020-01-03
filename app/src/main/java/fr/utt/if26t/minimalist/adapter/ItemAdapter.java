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

import fr.utt.if26t.minimalist.R;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.model.ItemModel;
import fr.utt.if26t.minimalist.model.ListModel;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewHolder>{
    private ArrayList<ItemModel> dataItems;
    private Context mContext;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ItemAdapter(Context context, ArrayList<ItemModel> dataItems) {
        mContext  = context;
        this.dataItems = dataItems;
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameItem;

        public ItemsViewHolder(@NonNull View itemView, final ItemAdapter.OnItemClickListener listener) {
            super(itemView);

            nameItem = itemView.findViewById(R.id.itemTextView);

            itemView.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    if (listener !=  null) {
                        int position = getAdapterPosition();
                        Long positionDatabase = dataItems.get(position).getId();
                        listener.onItemClick((positionDatabase.intValue()));
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
        holder.nameItem.setText(this.dataItems.get(position).getName());
        holder.itemView.setTag(this.dataItems.get(position).getId());
    }


    @Override
    public int getItemCount() {

        return (this.dataItems != null) ? this.dataItems.size() : 0;

//        if (this.dataItems == null)
//        {
//            return 0;
//        }
//        else {
//            return this.dataItems.size();
//        }
    }

    public void swapDataItems(ArrayList<ItemModel> newDataItems) {
        dataItems = newDataItems;
        notifyDataSetChanged();
    }
}

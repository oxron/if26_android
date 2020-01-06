package fr.utt.if26t.minimalist.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        public TextView nameItem, dateItem;
        public ImageView doneItem, importantItem;
        public LinearLayout layoutItem;

        public ItemsViewHolder(@NonNull View itemView, final ItemAdapter.OnItemClickListener listener) {
            super(itemView);

            nameItem = itemView.findViewById(R.id.itemTextView);
            dateItem = itemView.findViewById(R.id.dateItemTextView);
            doneItem = itemView.findViewById(R.id.checkImg);
            importantItem = itemView.findViewById(R.id.importantImg);
            layoutItem = itemView.findViewById(R.id.layoutItem);



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
        holder.dateItem.setText(this.dataItems.get(position).getDate());

        if (dataItems.get(position).getDone() ==  1) {
            holder.doneItem.setVisibility(View.VISIBLE);
        }
        if (dataItems.get(position).getPlanifie() ==  1) {
            holder.dateItem.setVisibility(View.VISIBLE);
        }
        if (dataItems.get(position).getImportant() == 1){
            holder.importantItem.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return (this.dataItems != null) ? this.dataItems.size() : 0;
    }

    public void swapDataItems(ArrayList<ItemModel> newDataItems) {
        dataItems = newDataItems;
        notifyDataSetChanged();
    }
}

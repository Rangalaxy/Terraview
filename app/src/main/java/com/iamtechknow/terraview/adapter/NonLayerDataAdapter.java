package com.iamtechknow.terraview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamtechknow.terraview.R;
import com.iamtechknow.terraview.picker.NonLayerContract;

import java.util.List;

/**
 * Item adapter for the category and measurement tab RecyclerViews
 */
public class NonLayerDataAdapter extends RecyclerView.Adapter<NonLayerDataAdapter.ViewHolder> {
    private List<String> mItems;
    private NonLayerContract.Presenter presenter;

    /**
     * Set up an empty adapter
     */
    public NonLayerDataAdapter(NonLayerContract.Presenter p) {
        super();
        presenter = p;
    }

    /**
     * View holder implementation for each list item
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            text = itemView.findViewById(R.id.item_text);
        }

        /**
         * Tell the presenter to post an event to the event bus, which decides what tab it belongs with to do so.
         */
        @Override
        public void onClick(View v) {
            presenter.emitEvent(mItems.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    /**
     * Called at the appropriate time by the presenter in the fragments to insert a string list
     * @param strings the data to show
     */
    public void insertList(List<String> strings) {
        mItems = strings;
        notifyDataSetChanged();
    }

    public void clearPresenter() {
        presenter = null;
    }
}

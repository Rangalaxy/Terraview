package com.iamtechknow.worldview.adapter;

import android.support.v7.widget.RecyclerView;

import com.iamtechknow.worldview.model.Layer;

public interface DragAndHideListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    /**
     * Called when the visibility image is toggled
     *
     * @param l The layer to hide
     * @param hide To hide or show the layer
     */
    void onToggleLayer(Layer l, boolean hide);

    /**
     * Called when the tile overlays need to be swapped, and maybe the layer stack too
     */
    void onSwapNeeded(int i, int i_new);

    /**
     * Called when an item is swiped to remove the corresponding tile overlay
     */
    void onLayerSwiped(int position);
}
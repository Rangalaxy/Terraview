package com.iamtechknow.worldview.picker;

import com.iamtechknow.worldview.model.Layer;

import java.util.ArrayList;

public interface LayerView {
    void populateList(ArrayList<Layer> layers);

    void showInfo(String html);
}

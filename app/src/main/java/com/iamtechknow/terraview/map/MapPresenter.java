package com.iamtechknow.terraview.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.google.android.gms.maps.GoogleMap;
import com.iamtechknow.terraview.model.Layer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Presenter portion of the Map contract, exposes all methods needed by the map view.
 */
public interface MapPresenter {

    void attachView(MapView v);

    void detachView();

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onMapReady(GoogleMap gmaps);

    void onDateChanged(Date date);

    Date getCurrDate();

    void getRemoteData(Context c);

    void getLocalData(LoaderManager manager, Context c);

    void setLayersAndUpdateMap(ArrayList<Layer> stack);

    void onSwapNeeded(int i, int i_new);

    void onToggleLayer(Layer l, boolean hide);

    void onLayerSwiped(int position, Layer l);

    ArrayList<Layer> getCurrLayerStack();

    void presentColorMaps();

    void presentAbout();

    void presentHelp();

    void chooseLayers();
}

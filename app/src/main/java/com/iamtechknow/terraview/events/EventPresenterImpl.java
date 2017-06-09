package com.iamtechknow.terraview.events;

import android.util.Patterns;

import com.iamtechknow.terraview.data.EONET;
import com.iamtechknow.terraview.model.Category;
import com.iamtechknow.terraview.model.Event;
import com.iamtechknow.terraview.model.TapEvent;
import com.iamtechknow.terraview.picker.RxBus;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class EventPresenterImpl implements EventPresenter, EONET.LoadCallback {
    private EventView view;
    private Disposable sub;
    private RxBus bus;
    private EONET client;

    //Whether open events have been loaded at startup
    private boolean loadedEvents, showingClosed;

    //Current category used for loading closed events
    private int currCat;

    public EventPresenterImpl(RxBus _bus, EventView v) {
        view = v;
        client = new EONET();
        client.setCallback(this);
        bus = _bus;
        sub = _bus.toObserverable().subscribe(this::handleEvent);
    }

    @Override
    public void detachView() {
        sub.dispose();
        sub = null;
        view = null;
    }

    /**
     * Load current events from EONET, if loaded from onStart, only gets loaded once.
     */
    @Override
    public void loadEvents(boolean onStart) {
        if(onStart && loadedEvents)
            return;

        loadedEvents = true;
        showingClosed = false;
        view.clearList();
        if(currCat == 0)
            client.getOpenEvents();
        else
            client.getEventsByCategory(currCat);
    }

    //Handle the event passed when a category is tapped
    //Need to handle whether to send open or close events
    @Override
    public void handleEvent(Object event) {
        if(event instanceof TapEvent) {
            TapEvent e = (TapEvent) event;
            if(e.getTab() == EventActivity.SELECT_EVENT_TAB) {
                currCat = e.getArg();
                view.clearList();
                if(showingClosed)
                    client.getClosedEvents(currCat, view.getEventLimit());
                else if(currCat == 0)
                    client.getOpenEvents();
                else
                    client.getEventsByCategory(currCat);
            }
        }
    }

    //Send an event to the event bus when an event has been tapped
    @Override
    public void presentEvent(Event e) {
        bus.send(new TapEvent(EventActivity.SELECT_EVENT, e));
    }

    /**
     * Present the source URL after validating it
     * @param url web link to be checked first
     */
    @Override
    public void presentSource(String url) {
        if(Patterns.WEB_URL.matcher(url).matches())
            view.showSource(url);
        else
            view.warnNoSource();
    }

    @Override
    public void presentClosed(int num) {
        showingClosed = true;
        view.clearList();
        client.getClosedEvents(currCat, num);
    }

    @Override
    public void onEventsLoaded(ArrayList<Event> data) {
        view.insertList(data);
    }

    @Override
    public void onCategoriesLoaded(ArrayList<Category> data) {}
}
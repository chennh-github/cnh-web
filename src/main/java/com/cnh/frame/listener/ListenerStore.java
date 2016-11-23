package com.cnh.frame.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/5
 */
public class ListenerStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerStore.class);

    // listener store, cache all listeners
    private static final Map<String, List<Listener>> listenerStore = new HashMap<String, List<Listener>>();


    /**
     * listener listener
     * @param listenerName
     * @param listener
     * @return index of listener
     */
    public static int registerListener (String listenerName, Listener listener) {
        List<Listener> listenerList;
        if (listenerStore.containsKey(listenerName)) {
            listenerList = listenerStore.get(listenerName);
        } else {
            listenerList = new ArrayList<Listener>();
            listenerStore.put(listenerName, listenerList);

            LOGGER.debug("listenerList does not exits, create a new listenerList form modular: {}", listenerName);
        }

        if (listenerList.contains(listener)) {
            LOGGER.warn("listener {} already exits in listenList in modular {}, ignored.", listener, listenerName);
            return listenerList.indexOf(listener);
        } else {
            listenerList.add(listener);
            return listenerList.size() - 1;
        }
    }


    /**
     * remove listener
     * @param listenerName
     * @param listener
     * @return listener any way.
     */
    public static Listener removeListener (String listenerName, Listener listener) {
        if (listenerStore.containsKey(listenerName)) {
            List<Listener> listenerList = listenerStore.get(listenerName);
            if (listenerList.contains(listener)) {
                listenerList.remove(listener);
            } else {
                LOGGER.warn("can't remove listener, because listenerList does not contains listener {}, ignored.", listener);
            }
        } else {
            LOGGER.warn("can't remove listener, because listenerName {} does not exits, ignored.", listenerName);
        }
        return listener;
    }

    /**
     * remove listener
     * @param listenerName
     * @param listenerIndex
     * @return null if not exits.
     */
    public static Listener removeListener (String listenerName, int listenerIndex) {
        Listener listener = null;
        if (listenerStore.containsKey(listenerName)) {
            List<Listener> listenerList = listenerStore.get(listenerName);
            if (listenerList.size() > listenerIndex) {
                listener = listenerList.remove(listenerIndex);
            } else {
                LOGGER.warn("can't remove listener, because listenerList does not contains listener, index {}, ignored.", listenerIndex);
            }
        } else {
            LOGGER.warn("can't remove listener, because listenerName {} does not exits, ignored.", listenerName);
        }
        return listener;
    }


    /**
     * execute listener list
     * @param listenerName
     * @param paramObject
     */
    public void executeListener (String listenerName, Object paramObject) {
        if (listenerStore.containsKey(listenerName)) {
            List<Listener> listenerList = listenerStore.get(listenerName);
            for (Listener listener: listenerList) {
                Object result = listener.execute(paramObject);
                if (result != null) {
                    paramObject = result;
                }
            }
        } else {
            LOGGER.warn("can't execute listenerList, because listenerName {} does not exits, ignored.", listenerName);
        }
    }

}

package com.cnh.mvc.system.demo.listener;

import com.cnh.frame.listener.Listener;
import com.cnh.frame.listener.ListenerStore;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
public class DemoListener {

    static {
        ListenerStore.registerListener("demoListener.afterDelete", new Listener() {
            @Override
            public Object execute(Object o) {
                return null;
            }
        });
    }

}

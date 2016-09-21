package com.learn.ex06.pyrmont.core;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.loader.WebappLoader;

/**
 * Created by huyan on 2016/9/20.
 */
public class SimpleContextLifecycleListener implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        Lifecycle lifecycle = event.getLifecycle();
        System.out.println(this.getClass().getSimpleName()+" event Type "+ event.getType());

        if (Lifecycle.START_EVENT.equals(event.getType())){
            System.out.println("Start event");
        } else if (Lifecycle.STOP_EVENT.equals(event.getType())){
            System.out.println("Stop event");
        }

    }
}

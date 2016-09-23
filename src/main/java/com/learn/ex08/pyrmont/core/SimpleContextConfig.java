package com.learn.ex08.pyrmont.core;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * Created by huyan on 2016/9/21.
 */
public class SimpleContextConfig implements LifecycleListener {

    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        if (Lifecycle.START_EVENT.equals(event.getType())){

            Context context = (Context)event.getLifecycle();
            //context.setConfigured(true);
        }
    }
}

package org.apache.catalina.util;

import java.util.HashMap;

/**
 * Created by huyan on 16/8/30.
 */
public class ParameterMap<K,V> extends HashMap<K,V> {

    public boolean locked = false;

    public boolean isLocked(){

        return this.locked;
    }
}

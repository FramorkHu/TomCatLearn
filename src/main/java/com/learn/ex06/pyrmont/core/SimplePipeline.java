package com.learn.ex06.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyan on 16/9/19.
 */
public class SimplePipeline implements Pipeline, Lifecycle {

    private Valve basic;
    private List<Valve> valves = new ArrayList<>();

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public Valve getBasic() {
        return this.basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        valves.add(valve);
    }

    @Override
    public Valve[] getValves() {
        return valves.toArray(new Valve[valves.size()]);
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        new SimplePipelineValveContext().invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

        valves.remove(valve);
    }

    class SimplePipelineValveContext implements ValveContext{

        int stage = 0;

        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {

            int subscript = stage;
            stage++;

            Valve basic = getBasic();
            if (subscript< valves.size()){
                valves.get(subscript).invoke(request, response, this);
            } else if ((subscript == valves.size()) && (basic!= null)){
                basic.invoke(request, response, this);
            } else {
                throw  new ServletException("pipeLine has no valve");
            }

        }

    }
}

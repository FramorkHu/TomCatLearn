package com.learn.ex05.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huyan on 2016/9/8.
 */
public class SimplePipeline implements Pipeline {


    private List<Valve> valves = new ArrayList<>();
    private Valve basic;

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        if (valve != null){
            valves.add(valve);
        }

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

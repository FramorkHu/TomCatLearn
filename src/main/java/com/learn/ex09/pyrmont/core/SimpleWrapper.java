package com.learn.ex09.pyrmont.core;

import com.learn.ex06.pyrmont.core.SimplePipeline;
import org.apache.catalina.*;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by huyan on 2016/9/8.
 */
public class SimpleWrapper implements Wrapper,Lifecycle {

    private Loader loader;
    protected Container parent;
    private Pipeline pipeline = new SimplePipeline();
    private String servletClass;
    private String name;
    private boolean started;

    private Servlet instance;

    LifecycleSupport lifecycle = new LifecycleSupport(this);

    public SimpleWrapper(){

        pipeline.setBasic(new SimpleWrapperValve(this));
    }


    public Pipeline getPipeline() {
        return pipeline;
    }


    @Override
    public long getAvailable() {
        return 0;
    }

    @Override
    public void setAvailable(long available) {

    }

    @Override
    public String getJspFile() {
        return null;
    }

    @Override
    public void setJspFile(String jspFile) {

    }

    @Override
    public int getLoadOnStartup() {
        return 0;
    }

    @Override
    public void setLoadOnStartup(int value) {

    }

    @Override
    public String getRunAs() {
        return null;
    }

    @Override
    public void setRunAs(String runAs) {

    }

    @Override
    public String getServletClass() {
        return this.servletClass;
    }

    @Override
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    @Override
    public boolean isUnavailable() {
        return false;
    }

    @Override
    public void addInitParameter(String name, String value) {

    }

    @Override
    public void addInstanceListener(InstanceListener listener) {

    }

    @Override
    public void addSecurityReference(String name, String link) {

    }

    @Override
    public Servlet allocate() throws ServletException {
        if (instance!=null){
            return instance;
        }

        ClassLoader classLoader = getLoader().getClassLoader();
        try {
            Class aClass = classLoader.loadClass(servletClass);
            instance = (Servlet)aClass.newInstance();
        } catch (Exception e) {
            throw new ServletException("Servlet class not found");
        }
        return instance;
    }

    @Override
    public void deallocate(Servlet servlet) throws ServletException {

    }

    @Override
    public String findInitParameter(String name) {
        return null;
    }

    @Override
    public String[] findInitParameters() {
        return new String[0];
    }

    @Override
    public String findSecurityReference(String name) {
        return null;
    }

    @Override
    public String[] findSecurityReferences() {
        return new String[0];
    }

    @Override
    public void load() throws ServletException {

    }

    @Override
    public void removeInitParameter(String name) {

    }

    @Override
    public void removeInstanceListener(InstanceListener listener) {

    }

    @Override
    public void removeSecurityReference(String name) {

    }

    @Override
    public void unavailable(UnavailableException unavailable) {

    }

    @Override
    public void unload() throws ServletException {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        if (loader != null){
            return loader;
        }
        if (parent.getLoader() != null){
            return parent.getLoader();
        }
        return null;
    }

    @Override
    public void setLoader(Loader loader) {

        this.loader = loader;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public void setLogger(Logger logger) {

    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public void setManager(Manager manager) {

    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {
        this.parent = container;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public void setRealm(Realm realm) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {

    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void addContainerListener(ContainerListener listener) {

    }

    @Override
    public void addMapper(Mapper mapper) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    @Override
    public Mapper findMapper(String protocol) {
        return null;
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        pipeline.invoke(request, response);
    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public void removeContainerListener(ContainerListener listener) {

    }

    @Override
    public void removeMapper(Mapper mapper) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

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

        System.out.println("Start wrapper:"+ servletClass);

        if (started){
            throw new LifecycleException("SimpleWrapper is started");
        }

        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        started = true;

        if (loader!= null && (loader instanceof Lifecycle) ){
            ((Lifecycle) loader).start();
        }
        if (pipeline instanceof Lifecycle){
            ((Lifecycle) pipeline).start();
        }
        lifecycle.fireLifecycleEvent(START_EVENT, null);

        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);

    }

    @Override
    public void stop() throws LifecycleException {
        System.out.println("Stop SimpleWrapper");

        try {
            instance.destroy();
        } catch (Exception e){
        }

        instance = null;

        if (!started){
            throw new LifecycleException("SimpleWrapper is stoped");
        }

        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);

        started = false;

        if (pipeline instanceof Lifecycle){
            ((Lifecycle) pipeline).stop();
        }
        if (loader!= null && (loader instanceof Lifecycle) ){
            ((Lifecycle) loader).stop();
        }

        lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
    }
}

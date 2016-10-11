package com.learn.ex20.pyrmont.standardmbeantest;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * Created by huyan on 2016/10/11.
 */
public class StandardAgent {

    private MBeanServer mBeanServer = null;

    public StandardAgent(){
        mBeanServer = MBeanServerFactory.createMBeanServer();
    }

    public MBeanServer getMBeanServer(){
        return this.mBeanServer;
    }

    public ObjectName createObjectName(String name){

        ObjectName objectName = null;

        try {

            objectName = new ObjectName(name);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return objectName;
    }

    private void createStandardBean(ObjectName objectName, String managedResourceClassName){

        try {

            mBeanServer.createMBean(managedResourceClassName, objectName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        StandardAgent agent = new StandardAgent();

        MBeanServer beanServer = agent.getMBeanServer();
        String domain = beanServer.getDefaultDomain();
        String managedResourceClassName = "com.learn.ex20.pyrmont.standardmbeantest.Car";

        ObjectName objectName = agent.createObjectName(domain+":type="+managedResourceClassName);

        agent.createStandardBean(objectName, managedResourceClassName);

        try {
            Attribute colorAttribute = new Attribute("Color","blue");
            beanServer.setAttribute(objectName, colorAttribute);
            System.out.println(beanServer.getAttribute(objectName, "Color"));
            beanServer.invoke(objectName,"drive",null,null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

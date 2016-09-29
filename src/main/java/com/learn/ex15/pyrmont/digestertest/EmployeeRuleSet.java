package com.learn.ex15.pyrmont.digestertest;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

/**
 * Created by huyan on 2016/9/29.
 */
public class EmployeeRuleSet extends RuleSetBase {
    @Override
    public void addRuleInstances(Digester digester) {

        digester.addObjectCreate("employee","com.learn.ex15.pyrmont.digestertest.Employee", "className");
        digester.addSetProperties("employee");

        digester.addObjectCreate("employee/office","com.learn.ex15.pyrmont.digestertest.Office");
        digester.addSetProperties("employee/office");
        digester.addSetNext("employee/office","addOffice");

        digester.addObjectCreate("employee/office/address","com.learn.ex15.pyrmont.digestertest.Address");
        digester.addSetProperties("employee/office/address");
        digester.addSetNext("employee/office/address","setAddress");
    }
}

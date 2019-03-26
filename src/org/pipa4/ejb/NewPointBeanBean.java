package org.pipa4.ejb;

import javax.ejb.Stateless;

@Stateless(name = "NewPointBeanEJB")
public class NewPointBeanBean {
    public NewPointBeanBean() {
    }

    public void NewPoint(){
        // TODO get data from form and put it into DB
    }
}

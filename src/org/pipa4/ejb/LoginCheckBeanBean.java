package org.pipa4.ejb;

import javax.ejb.Stateless;

@Stateless(name = "LoginCheckBeanEJB")
public class LoginCheckBeanBean {
    public LoginCheckBeanBean() {
    }

    public boolean check(String login, String password){
        // TODO
        return false;
    }
}

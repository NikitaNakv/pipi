package org.pipa4.ejb;

import javax.ejb.Stateless;

@Stateless(name = "LoginCheckBeanEJB")
public class LoginCheckBean {
    public LoginCheckBean() {
    }

    public boolean check(String login, String password){
        // TODO
        return false;
    }
    public int hash(String pwd){
        int hash = 0;
        // TODO passwords store as hash
        return hash;
    }
}

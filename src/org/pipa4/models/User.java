package org.pipa4.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "auth_key")
    private String authKey;

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthKey() {return authKey;}

    public void setAuthKey(String authKey){
        this.authKey = authKey;
    }
}

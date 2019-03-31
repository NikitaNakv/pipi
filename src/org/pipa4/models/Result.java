package org.pipa4.models;

import javax.persistence.*;

@Entity
@Table(name = "points")
public class Result {

    //TODO make a sequence in DB like
    // CREATE SEQUENCE JPA_SEQUENCE START WITH 1 INCREMENT BY 1;

    @Id
    @SequenceGenerator( name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private int id;
    @Column (name = "x")
    private double x;
    @Column (name = "y")
    private double y;
    @Column (name = "r")
    private double r;
    @Column (name = "result")
    private boolean result;

    @Column (name = "fok")
    private String fok;

    @Column (name = "auth")
    private String auth;

    public Result(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getFok() {
        return fok;
    }

    public void setFok(String name){
        this.fok = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
}

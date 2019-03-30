package org.pipa4.ejb;

import javax.ejb.Stateless;

@Stateless(name = "NewPointBeanEJB")
public class NewPointBean {
    public NewPointBean() {
    }

    public void NewPoint(){
        // TODO get data from form and put it into DB
    }

    public boolean isInArea(double x, double y, double r) {

        if((x >= 0 && y >= 0) && (x <= r) && (y <= (r))){
            return true;
        }
        else if(x <= 0 && y >= 0 && y <= (x+(r/2)) && x >= (-r/2) && y <= r){
            return true;
        }
        else return x >= 0 && y <= 0 && r >= Math.sqrt(y * y + x * x);
    }

}

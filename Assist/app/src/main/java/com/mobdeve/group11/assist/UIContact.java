package com.mobdeve.group11.assist;

import java.util.ArrayList;

public class UIContact {
    private String fName, lName, pNumber, guardian;
    private Integer type;

    public UIContact (String fName, String lName, String pNumber, String guardian){
        this.fName = fName;
        this.lName = lName;
        this.pNumber = pNumber;
        this.guardian = guardian;
        this.type = 2;
    }

    public UIContact(){
        this.fName = "fName";
        this.lName = "lName";
        this.pNumber = "pNumber";
        this.guardian = "guardian";
        this.type = 1;
    }

    public String getFName(){return fName;}
    public String getLName(){return lName;}
    public String getPNumber(){return pNumber;}
    public String getGuardian(){return guardian;}

    public void setLName(String lName){ this.lName = lName;}
    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}

}

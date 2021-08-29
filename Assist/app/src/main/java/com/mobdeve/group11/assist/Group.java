package com.mobdeve.group11.assist;

import java.util.ArrayList;
import java.util.Arrays;

public class Group {
    private String name;
    private ArrayList<UIContact> members;
    private Integer type;


    public Group (String name, ArrayList<UIContact> members){
        this.name = name;
        this.members = members;
        this.type = 2;
    }

    public Group (){
        this.name = "temp";
        this.type = 1;
        ArrayList<UIContact> g = new ArrayList<UIContact> (Arrays.asList (new UIContact("Angeli", "Mata", "09190038255", "Dianne Mata"), new UIContact("Sandra", "Berjamin", "09199854552", "Angela Berjamin")));
        this.members = g;
    }

    public String getName(){return name;}
    public ArrayList<UIContact> getMembers(){return members;}
    public void setName(String name){ this.name = name;}
    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}

}

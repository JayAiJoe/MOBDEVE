package com.mobdeve.group11.assist;

import java.util.ArrayList;
import java.util.Arrays;

public class Group {
    private String name;
    private ArrayList<Contact> members;
    private Integer type;


    public Group (String name, ArrayList<Contact> members){
        this.name = name;
        this.members = members;
        this.type = 2;
    }

    public Group (){
        this.name = "temp";
        this.type = 1;
        ArrayList<Contact> g = new ArrayList<Contact> (Arrays.asList (new Contact("Angeli", "Mata", "09190038255", "Dianne Mata"), new Contact("Sandra", "Berjamin", "09199854552", "Angela Berjamin")));
        this.members = g;
    }

    public String getName(){return name;}
    public ArrayList<Contact> getMembers(){return members;}
    public void setName(String name){ this.name = name;}
    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}

}

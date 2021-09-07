package com.mobdeve.group11.assist;

import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    private static String[] reminderChoices = {"On start time", "10 minutes before", "30 minutes before", "1 hour before", "3 hours before", "1 day before"};

    public static String[] getReminderChoices() { return reminderChoices; }

    public static int getReminderCount() { return reminderChoices.length; }

    public static String getReminderText(int i) { return  reminderChoices[i]; }

    public static String[] getGroupNames(List<ContactGroup> gList){
        String[] strArray = new String[gList.size()];
        for(int i=0; i<gList.size();i++)
        {
            strArray[i] = gList.get(i).getName();
        }
        return strArray;
    }

    public static ArrayList<Integer> getGroupIds(List<ContactGroup> gList){
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for(int i=0; i<gList.size();i++)
        {
            idArray.add(gList.get(i).getId());
        }
        return idArray;
    }

    public static String[] getTemplateTitles(List<Template> tList){
        String[] strArray = new String[tList.size()];
        for(int i=0; i<tList.size();i++)
        {
            strArray[i] = tList.get(i).getTitle();
        }
        return strArray;
    }

    public static ArrayList<Integer> getContactIds(List<Contact> cList){
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for(int i=0; i<cList.size();i++)
        {
            idArray.add(cList.get(i).getId());
        }
        return idArray;
    }

    public static String[] getContactNames(List<Contact> cList){
        String[] strArray = new String[cList.size()];
        for(int i=0; i<cList.size();i++)
        {
            strArray[i] = cList.get(i).getFirstName() + " " + cList.get(i).getLastName();
        }
        return strArray;
    }
}

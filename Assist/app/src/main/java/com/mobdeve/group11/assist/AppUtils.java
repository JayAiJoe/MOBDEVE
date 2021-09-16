package com.mobdeve.group11.assist;

import android.graphics.Bitmap;

import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//various static helper functions used in the project
public class AppUtils {

    //notification time choices
    private static String[] reminderChoices = {"On start time", "10 minutes before", "30 minutes before", "1 hour before", "3 hours before", "1 day before"};

    public static String[] getReminderChoices() { return reminderChoices; }

    public static int getReminderCount() { return reminderChoices.length; }

    public static String getReminderText(int i) { return  reminderChoices[i]; }

    //get only names from list of ContactGroup
    public static String[] getGroupNames(List<ContactGroup> gList){
        String[] strArray = new String[gList.size()];
        for(int i=0; i<gList.size();i++)
        {
            strArray[i] = gList.get(i).getName();
        }
        return strArray;
    }

    //get only ids from list of ContactGroup
    public static ArrayList<Integer> getGroupIds(List<ContactGroup> gList){
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for(int i=0; i<gList.size();i++)
        {
            idArray.add(gList.get(i).getId());
        }
        return idArray;
    }

    //get only titles from list of Template
    public static String[] getTemplateTitles(List<Template> tList){
        String[] strArray = new String[tList.size()];
        for(int i=0; i<tList.size();i++)
        {
            strArray[i] = tList.get(i).getTitle();
        }
        return strArray;
    }

    //get only ids from list of Contact
    public static ArrayList<Integer> getContactIds(List<Contact> cList){
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for(int i=0; i<cList.size();i++)
        {
            idArray.add(cList.get(i).getId());
        }
        return idArray;
    }

    //get only names from list of Contact
    public static String[] getContactNames(List<Contact> cList){
        String[] strArray = new String[cList.size()];
        for(int i=0; i<cList.size();i++)
        {
            strArray[i] = cList.get(i).getFirstName() + " " + cList.get(i).getLastName();
        }
        return strArray;
    }

    //verify if input string is a valid Philippine phone number
    public static boolean isValidPhoneNumber(String s)
    {
        Pattern p = Pattern.compile("^(09|\\+639)\\d{9}$");
        Matcher m = p.matcher(s);
        return (m.matches());
    }

    //make sure image isn't too big
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}

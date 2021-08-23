package com.mobdeve.group11.assist;

import java.util.ArrayList;
import java.util.Arrays;

public class DataHelper {
    public static ArrayList<Contact> initializeContacts() {
        ArrayList<Contact> data = new ArrayList<>();

        data.add(new Contact(
                "Patricia",
                "Ang",
                "09987654123",
                "Pat Ang"));
        data.add(new Contact(
                "Rogene",
                "Bueno",
                "09981254123",
                "Roge Bueno"));
        data.add(new Contact(
                "Sandra Angela",
                "Berjamin",
                "09199854552",
                "Sharon Berjamin"));
        data.add(new Contact(
                "Sharon",
                "Berjamin",
                "09985554123",
                "Sandra Berjamin"));
        data.add(new Contact(
                "Jose Ignacio",
                "Locsin",
                "09933654123",
                "Jay-ai Locsin"));
        data.add(new Contact(
                "Angeli",
                "Mata",
                "09190038255",
                "Grace Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));
        data.add(new Contact(
                "Dianne",
                "Mata",
                "09497134683",
                "Angeli Mata"));

        return data;
    }

    public static ArrayList<Group> initializeGroups() {
        ArrayList<Group> data = new ArrayList<>();
        DataHelper helper = new DataHelper();
        ArrayList<Contact> people = helper.initializeContacts();
        ArrayList<Contact> g1 = new ArrayList<Contact> (Arrays.asList (people.get(0), people.get(1)));
        ArrayList<Contact> g2 = new ArrayList<Contact> (Arrays.asList (people.get(0), people.get(1), people.get(2)));
        ArrayList<Contact> g3 = new ArrayList<Contact> (Arrays.asList (people.get(3), people.get(4)));
        ArrayList<Contact> g4 = new ArrayList<Contact> (Arrays.asList (people.get(5), people.get(4), people.get(3)));
        ArrayList<Contact> g5 = new ArrayList<Contact> (Arrays.asList (people.get(0), people.get(1), people.get(2), people.get(3), people.get(4)));
        ArrayList<Contact> g6 = new ArrayList<Contact> (Arrays.asList (people.get(6), people.get(5), people.get(4)));
        ArrayList<Contact> g7 = new ArrayList<Contact> (Arrays.asList (people.get(3), people.get(2)));


        data.add(new Group(
                "Faculty",
                g1));
        data.add(new Group(
                "Grade 5 - Service",
                g2));
        data.add(new Group(
                "Grade 6 - Courtesy",
                g3));
        data.add(new Group(
                "Grade 6 - Prayer",
                g4));
        data.add(new Group(
                "Music Club",
                g5));
        data.add(new Group(
                "Scholars",
                g6));
        data.add(new Group(
                "Student Officers",
                g7));

        return data;
    }

    public static ArrayList<Template> initializeTemplates() {
        ArrayList<Template> data = new ArrayList<> ();

        data.add(new Template(
                "All Souls + All Saints Days Announcement",
                "Announcement",
                "hello walang pasok"));
        data.add(new Template(
                "Music HW Due Reminder",
                "Reminder",
                "please pass your homework due tomorrow"));
        data.add(new Template(
                "Music Club Orientation Announcement",
                "Announcement",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        data.add(new Template(
                "Bio HW Due Reminder",
                "Reminder",
                "please pass your homework due tomorrow"));
        data.add(new Template(
                "Bio Exam #1 Template",
                "Exam",
                "Exam tomorrow"));
        data.add(new Template(
                "Bio Lecture Template",
                "Lecture",
                ""));
        data.add(new Template(
                "Leave of Absence Announcement",
                "LOA",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        return data;
    }
}

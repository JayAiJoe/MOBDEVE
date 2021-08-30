package com.mobdeve.group11.assist.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class AssistRepository {

    private ContactDao contactDao;
    private ContactGroupDao contactGroupDao;
    private EventDao eventDao;
    private EventGroupingDao eventGroupingDao;
    private GroupMembershipDao groupMembershipDao;
    private TemplateDao templateDao;

    private LiveData<List<Contact>> allContacts;
    private LiveData<List<ContactGroup>> allContactGroups;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Template>> allTemplates;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);;

    AssistRepository(Application application) {
        AssistDatabase db = AssistDatabase.getDatabase(application);
        contactDao = db.contactDao();
        contactGroupDao = db.contactGroupDao();
        eventDao = db.eventDao();
        eventGroupingDao = db.eventGroupingDao();
        groupMembershipDao = db.groupMembershipDao();
        templateDao = db.templateDao();

        allContacts = contactDao.loadAllContacts();
        allContactGroups = contactGroupDao.loadAllContactGroups();
        allEvents = eventDao.loadAllEvents();
        allTemplates = templateDao.loadAllTemplates();
    }

    //query functions
    LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    LiveData<List<ContactGroup>> getAllGroups() {
        return allContactGroups;
    }

    LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    LiveData<List<Template>> getAllTemplates() {
        return allTemplates;
    }

    LiveData<Contact> getContactById(Integer id) { return contactDao.findContactById(id); }

    LiveData<ContactGroup> getGroupById(Integer id) { return contactGroupDao.findContactGroupById(id); }

    LiveData<Event> getEventById(Integer id) { return eventDao.findEventById(id); }

    LiveData<Template> getTemplateById(Integer id) { return templateDao.findTemplateById(id); }

    LiveData<Contact> getContactByName(String firstName, String lastName) { return contactDao.findContactByName(firstName, lastName); }

    LiveData<ContactGroup> getGroupByName(String name) { return contactGroupDao.findContactByName(name); }

    LiveData<List<Integer>> getGroupsOfContact(Integer contactId) { return groupMembershipDao.findMembershipsByContactId(contactId); }

    LiveData<List<Integer>> getContactsInGroup(Integer groupId) { return groupMembershipDao.findMembershipsByGroupId(groupId); }

    LiveData<List<Integer>> getGroupsInEvent(Integer eventId) { return eventGroupingDao.findGroupingsByEventId(eventId); }

    LiveData<List<Integer>> getEventsOfGroup(Integer groupId) { return eventGroupingDao.findGroupingsByGroupId(groupId); }

    LiveData<List<Contact>> getManyContactsById(List<Integer> ids) { return contactDao.findManyContactsById(ids); }

    LiveData<List<ContactGroup>> getManyGroupsById(List<Integer> ids) { return contactGroupDao.findManyContactGroupsById(ids); }

    LiveData<List<Event>> loadEventsOfTheDay(LocalDate d) { return eventDao.loadEventsOfTheDay(d);}

    //async db update functions
    void addContact(Contact contact) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insertContact(contact);
        });
    }

    void addGroup(ContactGroup contactGroup) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactGroupDao.insertContactGroup(contactGroup);
        });
    }

    Integer deleteAllMembershipsOfGroup(Integer id) {
        Callable<Integer> insertCallable = () -> groupMembershipDao.deleteAllMembershipsOfGroup(id);
        Integer safe = 1;

        Future<Integer> future = executorService.submit(insertCallable);
        try {
            safe = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return safe;
    }

    long addGroupGetId(ContactGroup contactGroup) {
        Callable<Long> insertCallable = () -> contactGroupDao.insertContactGroup(contactGroup);
        long rowId = 0;

        Future<Long> future = executorService.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    void addEvent(Event event) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertEvent(event);
        });
    }

    void addGrouping(EventGrouping eventGrouping) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            eventGroupingDao.insertEventGrouping(eventGrouping);
        });
    }

    void addMembership(GroupMembership groupMembership) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            groupMembershipDao.insertGroupMembership(groupMembership);
        });
    }

    void addTemplate(Template template) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            templateDao.insertTemplate(template);
        });
    }

    void updateContact(Contact contact) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.updateContact(contact);
        });
    }

    void updateGroup(ContactGroup contactGroup) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactGroupDao.updateContactGroup(contactGroup);
        });
    }

    void updateEvent(Event event) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.updateEvent(event);
        });
    }

    void updateTemplate(Template template) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            templateDao.updateTemplate(template);
        });
    }

    void deleteContact(Contact contact) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.deleteContact(contact);
        });
    }

    void deleteGroup(ContactGroup contactGroup) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            contactGroupDao.deleteContactGroup(contactGroup);
        });
    }

    void deleteEvent(Event event) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteEvent(event);
        });
    }

    void deleteGrouping(EventGrouping eventGrouping) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            eventGroupingDao.deleteEventGrouping(eventGrouping);
        });
    }

    void deleteMembership(GroupMembership groupMembership) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            groupMembershipDao.deleteGroupMemberShip(groupMembership);
        });
    }

    void deleteTemplate(Template template) {
        AssistDatabase.databaseWriteExecutor.execute(() -> {
            templateDao.deleteTemplate(template);
        });
    }

}

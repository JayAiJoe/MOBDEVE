package com.mobdeve.group11.assist.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AssistViewModel extends AndroidViewModel {

    private AssistRepository mRepository;

    public AssistViewModel(Application application) {
        super(application);
        mRepository = new AssistRepository(application);
    }

    public LiveData<List<Contact>> getAllContacts() { return mRepository.getAllContacts(); }

    public LiveData<List<ContactGroup>> getAllGroups() { return mRepository.getAllGroups(); }

    public LiveData<List<Event>> getAllEvents() { return mRepository.getAllEvents(); }

    public LiveData<List<Template>> getAllTemplates() { return mRepository.getAllTemplates(); }

    public LiveData<Contact> getContactById(Integer id) { return mRepository.getContactById(id); }

    public LiveData<ContactGroup> getGroupById(Integer id) { return mRepository.getGroupById(id); }

    public LiveData<Event> getEventById(Integer id) { return mRepository.getEventById(id); }

    public LiveData<Template> getTemplateById(Integer id) { return mRepository.getTemplateById(id); }

    public LiveData<Contact> getContactByName(String firstName, String lastName) { return mRepository.getContactByName(firstName, lastName); }

    public LiveData<ContactGroup> getGroupByName(String name) { return mRepository.getGroupByName(name); }

    public LiveData<List<Integer>> getGroupIdsInEvent(Integer eventId) { return mRepository.getGroupsInEvent(eventId); }

    public LiveData<List<Integer>> getEventIdsOfGroup(Integer groupId) { return mRepository.getEventsOfGroup(groupId); }

    public LiveData<List<Integer>> getGroupIdsOfContact(Integer contactId) { return mRepository.getGroupsOfContact(contactId); }

    public LiveData<List<Integer>> getContactIdsInGroup(Integer groupId) { return mRepository.getContactsInGroup(groupId); }

    public LiveData<List<Contact>> getManyContactsById(List<Integer> ids) { return mRepository.getManyContactsById(ids); }

    public LiveData<List<ContactGroup>> getManyCGroupsById(List<Integer> ids) { return mRepository.getManyGroupsById(ids); }

    public void addContact(Contact contact) { mRepository.addContact(contact); }

    public void addGroup(ContactGroup contactGroup) { mRepository.addGroup(contactGroup); }

    public void addEvent(Event event) { mRepository.addEvent(event); }

    public void addGrouping(EventGrouping eventGrouping) { mRepository.addGrouping(eventGrouping); }

    public void addMembership(GroupMembership groupMembership) { mRepository.addMembership(groupMembership); }

    public void addTemplate(Template template) { mRepository.addTemplate(template); }

    public void updateContact(Contact contact) { mRepository.updateContact(contact); }

    public void updateGroup(ContactGroup contactGroup) { mRepository.updateGroup(contactGroup); }

    public void updateEvent(Event event) { mRepository.updateEvent(event); }

    public void updateTemplate(Template template) { mRepository.updateTemplate(template); }

    public void deleteContact(Contact contact) { mRepository.deleteContact(contact); }

    public void deleteGroup(ContactGroup contactGroup) { mRepository.deleteGroup(contactGroup); }

    public void deleteEvent(Event event) { mRepository.deleteEvent(event); }

    public void deleteGrouping(EventGrouping eventGrouping) { mRepository.deleteGrouping(eventGrouping); }

    public void deleteMembership(GroupMembership groupMembership) { mRepository.deleteMembership(groupMembership); }

    public void deleteTemplate(Template template) { mRepository.deleteTemplate(template); }
}
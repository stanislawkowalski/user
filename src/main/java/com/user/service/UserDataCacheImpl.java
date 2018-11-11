package com.user.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import com.user.domain.User;
import com.user.exception.BadUserDefinitionException;

public class UserDataCacheImpl implements UserDataCache {

	private TreeSet<User> users;
    private HashMap<Long, User> idMap;
    private HashMap<String, TreeSet<User>> lastNameMap;
    private Class<? extends Comparator<User>> comparator;

    public UserDataCacheImpl(Class<? extends Comparator<User>> comparator) {
    	this.comparator = comparator;
        users = newEmptyUserSet();
        idMap = new HashMap<>();
        lastNameMap = new HashMap<>();
    }

    @Override
    public synchronized boolean addUser(User user) {
        checkUserHasAllMandatoryFields(user);
        checkUserIdIsBiggerThanZero(user.getId());
        if (checkIdIsNotInCacheNow(user.getId())) {
            addUserToCache(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(idMap.get(id));
    }

    @Override
    public List<User> getUsers(String lastName) {
        TreeSet<User> usersSetByLastName = getLastNameSet(lastName);
        return new ArrayList<>(usersSetByLastName);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    @Override
    public int size() {
    	return users.size();
    }
    
    private TreeSet<User> newEmptyUserSet() {
    	try {
    		return new TreeSet<>(comparator.newInstance());
    	} catch (Exception ex) {
    		return new TreeSet<>();
    	}
    }
    
    private void checkUserHasAllMandatoryFields(User user) {
        if (user == null || user.getId() == null ||
                user.getFirstName() == null || user.getLastName() == null) {
            throw new BadUserDefinitionException();
        }
    }

    private void checkUserIdIsBiggerThanZero(Long userId) {
        if (userId.compareTo(0L) < 1) {
        	throw new BadUserDefinitionException();
        }
    }

    private boolean checkIdIsNotInCacheNow(Long userId) {
        return !getUser(userId).isPresent();
    }

    private void addUserToCache(User user) {
        users.add(user);
        idMap.put(user.getId(), user);
        TreeSet<User> lastNameSet = getLastNameSet(user.getLastName());
        lastNameSet.add(user);
        lastNameMap.put(getKeyFromLastName(user.getLastName()), lastNameSet);
    }

    private TreeSet<User> getLastNameSet(String lastName) {
        TreeSet<User> usersSetByLastName = lastNameMap.get(getKeyFromLastName(lastName));
        return usersSetByLastName == null ? newEmptyUserSet() : usersSetByLastName;
    }

    private String getKeyFromLastName(String lastName) {
        return lastName.trim().toLowerCase();
    }
}

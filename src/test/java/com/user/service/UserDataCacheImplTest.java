package com.user.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.user.domain.User;
import com.user.exception.BadUserDefinitionException;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class UserDataCacheImplTest {

    private UserDataCacheImpl userDataCacheImpl;

    @Before
    public void before() {
        userDataCacheImpl = new UserDataCacheImpl();
    }

    @Test
    public void testInit_beforeAddUsers() {
    	assertEquals(0, userDataCacheImpl.getAllUsers().size());
    }

    @Test
    public void testAddUser_afterAddUsers() {
        addTestUsers();
        List<User> allCacheUsers = userDataCacheImpl.getAllUsers();
        assertEquals(new Long(3), allCacheUsers.get(0).getId());
        assertEquals(new Long(1), allCacheUsers.get(1).getId());
        assertEquals(new Long(2), allCacheUsers.get(2).getId());
        assertEquals(3, userDataCacheImpl.getAllUsers().size());
    }

    @Test
    public void testAddUser_addUserWithIdExistingInCache() {
        addTestUsers();
        boolean isAdd = userDataCacheImpl.addUser(new User(2L, "Andrzej", "Mikula", "Olsztyn"));
        assertFalse(isAdd);
        assertEquals(3, userDataCacheImpl.getAllUsers().size());
    }

    @Test
    public void testAddUser_addUserWithDataExistingInCacheButDifferentId() {
        addTestUsers();
        boolean isAdd = userDataCacheImpl.addUser(new User(4L, "Stanislaw", "Kowalski", "Poznan"));
        assertTrue(isAdd);
        assertTrue(userDataCacheImpl.getUser(4L).isPresent());
        assertEquals(4, userDataCacheImpl.getAllUsers().size());
    }

    @Test(expected = BadUserDefinitionException.class)
    public void testAddUser_addUserWithIdLessThanOne() {
    	userDataCacheImpl.addUser(new User(0L, "Stanislaw", "Kowalski", "Poznan"));
    }

    @Test(expected = BadUserDefinitionException.class)
    public void testAddUser_addUserWithNullId() {
        userDataCacheImpl.addUser(new User(null, "Stanislaw", "Kowalski", "Poznan"));
    }

    @Test(expected = BadUserDefinitionException.class)
    public void testAddUser_addUserWithNullFirstName() {
        userDataCacheImpl.addUser(new User(1L, null, "Kowalski", "Poznan"));
    }

    @Test(expected = BadUserDefinitionException.class)
    public void testAddUser_addUserWithNullLastName() {
        userDataCacheImpl.addUser(new User(1L, "Stanislaw", null, "Poznan"));
    }

    @Test
    public void testGetUserById_userNotExistInCache() {
        addTestUsers();
        Optional<User> userById = userDataCacheImpl.getUser(4L);
        assertFalse(userById.isPresent());
    }

    @Test
    public void testGetUserById_userExistInCache() {
        addTestUsers();
        Optional<User> userById = userDataCacheImpl.getUser(1L);
        assertTrue(userById.isPresent());
        assertEquals(new Long(1), userById.get().getId());
    }

    @Test
    public void testGetUsersByLastName_usersNotExistInCache() {
        addTestUsers();
        List<User> usersByLastName = userDataCacheImpl.getUsers("Kowal");
        assertTrue(usersByLastName.isEmpty());
    }

    @Test
    public void testGetUsersByLastName_usersExistInCache() {
        addTestUsers();
        String testLastName = "Kowalski";
        List<User> usersByLastName = userDataCacheImpl.getUsers(testLastName);
        assertEquals(2, usersByLastName.size());
        assertEquals(testLastName, usersByLastName.get(0).getLastName());
        assertEquals(testLastName, usersByLastName.get(1).getLastName());
    }

    @Test
    public void testGetUsersByLastName_lastNamesInDifferentCasesAndBeginEndWhiteSigns() {
        addTestUsers();
        userDataCacheImpl.addUser(new User(4L, "Marcin", "NOWAK", "Olsztyn"));
        List<User> usersByLastName = userDataCacheImpl.getUsers(" nowak	");
        assertEquals(2, usersByLastName.size());
        assertEquals(new Long(4), usersByLastName.get(0).getId());
        assertEquals(new Long(2), usersByLastName.get(1).getId());
    }

    private void addTestUsers() {
        userDataCacheImpl.addUser(new User(1L, "Stanislaw", "Kowalski", "Poznan"));
        userDataCacheImpl.addUser(new User(2L, "Grzegorz", "Nowak", "Szczecin"));
        userDataCacheImpl.addUser(new User(3L, "Mariusz", "Kowalski", "Gdansk"));
    }
}

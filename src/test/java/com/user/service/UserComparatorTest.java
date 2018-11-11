package com.user.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.user.domain.User;

public class UserComparatorTest {
	
	private static final String TEST_LAST_NAME = "Kowalski";
    private static final String TEST_FIRST_NAME = "Stanislaw";
    private static final String TEST_ADDRESS = "Poznan";

    private User testUser;
    private UserComparator userComparator;

    @Before
    public void before() {
        testUser = new User(2L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS);
        userComparator = new UserComparator();
    }

    @Test
    public void testCompare_isAlphabeticallyBefore() {
        int compareResult = userComparator.compare(testUser, new User(1L, "Artur", "Nowak", TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompare_isAlphabeticallyAfter() {
        int compareResult = userComparator.compare(testUser, new User(3L, "Zbigniew", "Kowal", TEST_ADDRESS)); 
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompare_isByFirstNameAlphabeticallyBefore() {
        int compareResult = userComparator.compare(testUser, new User(1L, "Zbigniew", TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompare_isByFirstNameAlphabeticallyAfter() {
        int compareResult = userComparator.compare(testUser, new User(3L, "Artur", TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompare_isByIdAlphabeticallyBefore() {
        int compareResult = userComparator.compare(testUser, new User(3L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompare_isByIdAlphabeticallyAfter() {
        int compareResult = userComparator.compare(testUser, new User(1L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompare_isAlphabeticallyEqual() {
        int compareResult = userComparator.compare(testUser, new User(2L, TEST_FIRST_NAME, TEST_LAST_NAME, "Szczecin"));
        assertTrue(isZero(compareResult));
    }

    private boolean isPositive(int compareResult) {
        return compareResult > 0;
    }

    private boolean isNegative(int compareResult) {
        return compareResult < 0;
    }

    private boolean isZero(int compareResult) {
        return compareResult == 0;
    }

}

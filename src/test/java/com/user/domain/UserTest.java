package com.user.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private static final String TEST_LAST_NAME = "Kowalski";
    private static final String TEST_FIRST_NAME = "Stanislaw";
    private static final String TEST_ADDRESS = "Poznan";

    private User testUser;

    @Before
    public void before() {
        testUser = new User(2L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS);
    }

    @Test
    public void testCompareTo_isAlphabeticallyBefore() {
        int compareResult = testUser.compareTo(new User(1L, "Artur", "Nowak", TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompareTo_isAlphabeticallyAfter() {
        int compareResult = testUser.compareTo(new User(3L, "Zbigniew", "Kowal", TEST_ADDRESS));
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompareTo_isByFirstNameAlphabeticallyBefore() {
        int compareResult = testUser.compareTo(new User(1L, "Zbigniew", TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompareTo_isByFirstNameAlphabeticallyAfter() {
        int compareResult = testUser.compareTo(new User(3L, "Artur", TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompareTo_isByIdAlphabeticallyBefore() {
        int compareResult = testUser.compareTo(new User(3L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isNegative(compareResult));
    }

    @Test
    public void testCompareTo_isByIdAlphabeticallyAfter() {
        int compareResult = testUser.compareTo(new User(1L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS));
        assertTrue(isPositive(compareResult));
    }

    @Test
    public void testCompareTo_isAlphabeticallyEqual() {
        int compareResult = testUser.compareTo(new User(2L, TEST_FIRST_NAME, TEST_LAST_NAME, "Szczecin"));
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

package com.user.service;

import java.util.Comparator;

import com.user.domain.User;

public class UserComparatorMock implements Comparator<User> {

	@Override
	public int compare(User userFirst, User userSecond) {
		return userFirst.getId().compareTo(userSecond.getId());
	}

}

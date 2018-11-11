package com.user.service;

import java.util.Comparator;

import com.user.domain.User;

public class UserComparator implements Comparator<User>{

	@Override
	public int compare(User userFirst, User userSecond) {
		int compareLastName = prepareStringToCompare(userFirst.getLastName())
				.compareTo(prepareStringToCompare(userSecond.getLastName()));
        if (compareLastName != 0) {
            return compareLastName;
        }
        int compareFirstName = prepareStringToCompare(userFirst.getFirstName())
        		.compareTo(prepareStringToCompare(userSecond.getFirstName()));
        if (compareFirstName != 0) {
            return compareFirstName;
        }
        return userFirst.getId().compareTo(userSecond.getId());
	}

	private String prepareStringToCompare(String toPrepare) {
		return toPrepare.trim().toLowerCase();
	}
}

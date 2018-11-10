package com.user.domain;

public class User implements Comparable<User> {

    public User(Long id, String firstName, String lastName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    private Long id;
    private String firstName;
    private String lastName;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(User userToCompare) {
        int compareLastName = lastName.compareTo(userToCompare.getLastName());
        if (compareLastName != 0) {
            return compareLastName;
        }
        int compareFirstName = firstName.compareTo(userToCompare.getFirstName());
        if (compareFirstName != 0) {
            return compareFirstName;
        }
        return id.compareTo(userToCompare.getId());
    }
}

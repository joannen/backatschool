package se.theyellowbelliedmarmot.backatschool.model;

import java.util.UUID;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-01.
 */
public class User {

    private String firstName;
    private String lastName;
    private String id;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        id = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
}

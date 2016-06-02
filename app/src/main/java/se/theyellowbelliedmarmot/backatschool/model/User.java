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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
            return false;
        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}

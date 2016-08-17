package se.theyellowbelliedmarmot.backatschool.tools;

import se.theyellowbelliedmarmot.backatschool.model.User;

public final class Validator {

    public static boolean validateUserInput(User user){
        return (user.getFirstName().matches("[a-zåäöA-ZÅÄÖ-]+") && user.getLastName().matches("[a-zåäöA-ZÅÄÖ-]+"));
    }
}
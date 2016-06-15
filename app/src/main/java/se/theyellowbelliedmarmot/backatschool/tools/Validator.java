package se.theyellowbelliedmarmot.backatschool.tools;

import se.theyellowbelliedmarmot.backatschool.model.User;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-15.
 */
public final class Validator {

    public static boolean validateUserInput(User user){
        return (user.getFirstName().matches("[a-zåäöA-ZÅÄÖ-]+") && user.getLastName().matches("[a-zåäöA-ZÅÄÖ-]+"));
    }
}

package se.theyellowbelliedmarmot.backatschool.tools;

import se.theyellowbelliedmarmot.backatschool.model.User;

<<<<<<< HEAD
=======
/**
 * Created by TheYellowBelliedMarmot on 2016-06-15.
 */
>>>>>>> acb12e987b0dc112e826e5ce7f708085d7114970
public final class Validator {

    public static boolean validateUserInput(User user){
        return (user.getFirstName().matches("[a-zåäöA-ZÅÄÖ-]+") && user.getLastName().matches("[a-zåäöA-ZÅÄÖ-]+"));
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> acb12e987b0dc112e826e5ce7f708085d7114970

package se.theyellowbelliedmarmot.backatschool.service;

import se.theyellowbelliedmarmot.backatschool.model.User;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-01.
 */
public final class UserService {

    private User user;
    private static String URL = "http://beacons.zenzor.io/sys/api/register_user";

    public UserService(User user) {
        this.user = user;
    }

    public void registerUser(User user){

    }
}

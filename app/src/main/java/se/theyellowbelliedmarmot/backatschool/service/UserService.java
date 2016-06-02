package se.theyellowbelliedmarmot.backatschool.service;

import android.content.Context;
import android.util.Log;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import se.theyellowbelliedmarmot.backatschool.model.User;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-01.
 */
public final class UserService {

    private static final String TAG = UserService.class.getSimpleName();
    private static String URL = "http://beacons.zenzor.io/sys/api/register_user";
    private static String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    public void registerUser(final User user, Context context) {

        String input = "input={\"api_key\":\"" + APIKEY + "\",\"first_name\":\""
                        + user.getFirstName() + "\",\"last_name\":\"" + user.getLastName() + "\"}";

        Ion.with(context).load(URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (result != null) {
                            Log.d(TAG, result);
                            Log.d(user.getFirstName(), user.getLastName());
                        } else {
                            Log.d(TAG, "no result");
                        }
                    }
                });
    }
}

package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by joanne on 10/06/16.
 */
public class BaseActivity extends AppCompatActivity{

    public static final String TAG = "LOGTAG";
    protected static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected String readUser(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name","");
        String lastName = sharedPreferences.getString("last_name", "");
        return  firstName + lastName;
    }

    protected void saveUser(String firstName, String lastName){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.commit();
    }

    public void saveUserId(String id){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    protected String readUserId(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        return id;
    }

}

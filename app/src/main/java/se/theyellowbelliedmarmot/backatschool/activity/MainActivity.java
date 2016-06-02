package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.User;
import se.theyellowbelliedmarmot.backatschool.service.UserService;

public class MainActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private UserService userService = new UserService();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(this, "BLE Not Supported",
//                    Toast.LENGTH_SHORT).show();
//            finish();
//        }

        if(readUser().equals(""))
            {final EditText authFirstName = new EditText(this);
            final EditText authLastName = new EditText(this);
            final AlertDialog.Builder authInputBox = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enter your'e name: ")
                    .setView(authFirstName)
                    .setView(authLastName)
                    .setCancelable(false)
                    .setPositiveButton("Check In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            firstName = authFirstName.getText().toString();
                            lastName = authLastName.getText().toString();
                            user = new User(firstName, lastName);
                            try {
                                saveUser(firstName, lastName);
                                userService.registerUser(user, getApplicationContext());
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
            authInputBox.create();
            authInputBox.show();
        }
//        else {
            // Create new Intent and start Scan Activity
//        }
    }

    private void saveUser(String firstName, String lastName){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.commit();
    }

    private String readUser(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name","");
        String lastName = sharedPreferences.getString("last_name", "");
        return  firstName + lastName;
    }
}

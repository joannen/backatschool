package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
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


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        if(readUser().equals("")) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText authFirstName = new EditText(this);
            authFirstName.setHint("Enter first name");
            linearLayout.addView(authFirstName);
            final EditText authLastName = new EditText(this);
            authLastName.setHint("Enter last name");
            linearLayout.addView(authLastName);

            final AlertDialog.Builder authInputBox = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enter your name: ")
                    .setView(linearLayout)
//                    .setView(authLastName)
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
                                Intent intent = new Intent(getApplicationContext(), ScanActiveBeacon.class);
                                startActivity(intent);
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
        else {
            Intent intent = new Intent(this, ScanActiveBeacon.class);
            startActivity(intent);
        }
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

    // Test this
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

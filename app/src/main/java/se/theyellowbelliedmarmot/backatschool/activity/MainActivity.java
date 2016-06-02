package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.DialogInterface;
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

        final EditText authFirstName = new EditText(this);
        final EditText authLastName = new EditText(this);
        final AlertDialog.Builder authInputBox = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Enter your'e name: ")
                .setView(authFirstName)
                .setView(authLastName)
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firstName = authFirstName.getText().toString();
                        lastName = authLastName.getText().toString();
                        user = new User(firstName, lastName);
                        try {
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
}

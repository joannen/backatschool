package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.constants.URLS;
import se.theyellowbelliedmarmot.backatschool.model.User;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

public class MainActivity extends BaseActivity {

    private String firstName;
    private String lastName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first check for BLEsupport

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        //check if user is registered
        if (readUser().equals("")) {
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
                    .setCancelable(false)
                    .setPositiveButton("Check In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            firstName = authFirstName.getText().toString();
                            lastName = authLastName.getText().toString();
                            user = new User(firstName, lastName);
                            try {
                                saveUser(firstName, lastName);
                                registerUser(user, getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), ScanActiveBeacon.class);
                                startActivity(intent);
                            } catch (Exception e) {
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
        } else {
            Log.d(TAG, "USERID: " + readUserId());
            Log.d(TAG, "USER: " + readUser());
            Intent intent = new Intent(this, ScanActiveBeacon.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void registerUser(final User user, final Context context) {
        String input = JsonParser.userInputToJson(APIKEY, user.getFirstName(), user.getLastName());

        Ion.with(context).load(URLS.REGISTER_USER)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.d(TAG, result.toString());
                            Log.d(TAG, "ID_USER: " + result.get("id_user").getAsString());
                            saveUserId(getApplicationContext(), result.get("id_user").getAsString());

                        } else {
                            Log.d(TAG, "no result");
                        }
                    }
                });
    }

}

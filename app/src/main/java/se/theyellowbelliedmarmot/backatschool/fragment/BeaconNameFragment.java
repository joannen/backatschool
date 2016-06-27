package se.theyellowbelliedmarmot.backatschool.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.activity.BaseActivity;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-27.
 */
public class BeaconNameFragment extends DialogFragment {

    String beaconName;
    private SharedPreferences preferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Context context = getActivity();

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText newName = new EditText(context);
        newName.setHint("Enter new name");
        layout.addView(newName);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Change beacon name")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        beaconName = newName.getText().toString();
                        preferences = getActivity().getSharedPreferences("beaconName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("beacon_name", beaconName);
                        editor.commit();
                    }
                });


        AlertDialog dialog = builder.create();
        return dialog;

    }
}

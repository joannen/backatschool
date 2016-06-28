package se.theyellowbelliedmarmot.backatschool.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import se.theyellowbelliedmarmot.backatschool.R;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-27.
 */
public class BeaconNameFragment extends DialogFragment {

//    public static String PREF_BEACON_NAME = "new_beacon_name";
    private SharedPreferences preferences;
    private EditText beaconName;

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        Context context = getActivity();
//
//        LinearLayout layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        final EditText newName = new EditText(context);
//        newName.setHint("Enter new name");
//        layout.addView(newName);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                .setTitle("Change beacon name")
//                .setView(layout)
//                .setNegativeButton("Cancel", null)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        beaconName = newName.getText().toString();
//                        preferences = getActivity().getSharedPreferences(PREF_BEACON_NAME, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("beacon_name", beaconName);
//                        editor.commit();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//
//        return dialog;
//
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(BeaconNameFragment.this.getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View inputView = factory.inflate(R.layout.name_input_layout, null);

        beaconName = (EditText)inputView.findViewById(R.id.name_input_text);
        builder.setView(inputView);
        builder.setMessage("Change beacon name");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final DialogInterface d = dialogInterface;
                Button buttonOK = ((AlertDialog)dialogInterface).getButton( DialogInterface.BUTTON_POSITIVE );
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nameInputCheck()) {
                            d.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private boolean nameInputCheck(){
        String name = beaconName.getText().toString();

        if (name.equals("")){
            Toast.makeText(getActivity(),"No name",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.commit();

        return true;
    }
}

package se.theyellowbelliedmarmot.backatschool.activity;

import android.app.Activity;
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
import se.theyellowbelliedmarmot.backatschool.model.Beacon;


public class BeaconNameFragment extends DialogFragment {

    private SharedPreferences preferences;
    private EditText beaconName;
    NoticeDialogListener dialogListener;
    Beacon beacon;

    public interface NoticeDialogListener{
        void onDialogPositiveClick(DialogFragment fragment, Beacon beacon, String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (NoticeDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            beacon = (Beacon) bundle.getSerializable("beacon");
        }
    }

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
                        String name = beaconName.getText().toString();

                        if (nameInputCheck(name)) {
                            d.dismiss();
                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", name);
                        editor.commit();
                        dialogListener.onDialogPositiveClick(BeaconNameFragment.this, beacon, name );
                        Toast.makeText(getActivity(),"New beacon name is saved", Toast.LENGTH_LONG ).show();
                    }
                });
            }
        });

        return dialog;
    }

    private boolean nameInputCheck(String name){
        if (name.equals("")){
            Toast.makeText(getActivity(),"No name",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

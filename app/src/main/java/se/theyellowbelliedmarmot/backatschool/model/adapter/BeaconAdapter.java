package se.theyellowbelliedmarmot.backatschool.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.activity.SubscribedBeacons;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;

/**
 * Created by joanne on 03/06/16.
 */
public final class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.BeaconViewHolder> {

    private final Context context;
    private final List<Beacon> beacons;

    public BeaconAdapter(Context context, List<Beacon> beacons) {
        this.context = context;
        this.beacons = beacons;
    }

    @Override
    public BeaconAdapter.BeaconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beacon_row, parent, false);
        return new BeaconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeaconAdapter.BeaconViewHolder holder, int position) {
        holder.uuidView.setText(beacons.get(position).getUuid());
        holder.majorView.setText(beacons.get(position).getMajor());
        holder.minorView.setText(beacons.get(position).getMinor());
        holder.nameView.setText(beacons.get(position).getName());
        holder.rssiView.setText(String.valueOf(beacons.get(position).getRssi()));
        holder.addressView.setText(beacons.get(position).getDeviceAddress());

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transTwo));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transOne));
        }
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public static final class BeaconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView uuidView;
        public final TextView majorView;
        public final TextView minorView;
        public final TextView rssiView;
        public final TextView nameView;
        public final TextView addressView;

        public BeaconViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.uuidView = (TextView) itemView.findViewById(R.id.uuid_text);
            this.majorView = (TextView) itemView.findViewById(R.id.major_text);
            this.minorView = (TextView) itemView.findViewById(R.id.minor_text);
            this.rssiView = (TextView) itemView.findViewById(R.id.rssi_text);
            this.nameView = (TextView) itemView.findViewById(R.id.beacon_name_text);
            this.addressView = (TextView) itemView.findViewById(R.id.device_address);
        }

        @Override
        public void onClick(View view) {
            Log.d("CLICKED " , "BEACON ADAPTER");
            Intent intent = new Intent(view.getContext(), SubscribedBeacons.class);
            intent.putExtra("uuid", uuidView.getText());
            intent.putExtra("major", majorView.getText());
            intent.putExtra("minor", minorView.getText());
            intent.putExtra("name", nameView.getText());
            intent.putExtra("rssi", rssiView.getText());
            intent.putExtra("deviceAddress", addressView.getText());
            intent.putExtra("has_beacon", true);
            view.getContext().startActivity(intent);
        }
    }
}

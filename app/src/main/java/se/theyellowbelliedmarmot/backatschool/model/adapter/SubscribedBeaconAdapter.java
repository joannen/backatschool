package se.theyellowbelliedmarmot.backatschool.model.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.fragment.BeaconNameFragment;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;

/**
 * Created by TheYellowBelliedMarmot on 2016-06-27.
 */
public class SubscribedBeaconAdapter extends RecyclerView.Adapter<SubscribedBeaconAdapter.SubscribedBeaconViewHolder>{

    private List<Beacon> beacons;
    private Context context;

    public SubscribedBeaconAdapter(Context context, List<Beacon> beacons){
        this.context = context;
        this.beacons = beacons;
    }

    @Override
    public SubscribedBeaconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscribed_beacon_row, parent, false);
        SubscribedBeaconViewHolder viewHolder = new SubscribedBeaconViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubscribedBeaconViewHolder holder, int position) {
        holder.bindSubscribedBeacon(beacons.get(position));

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lightGreen));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lightGray));
        }
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public class SubscribedBeaconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView uuidView;
        public TextView majorView;
        public TextView minorView;
        public TextView nameView;
        public TextView addressView;

        public SubscribedBeaconViewHolder(View itemView) {
            super(itemView);
            uuidView = (TextView) itemView.findViewById(R.id.subsc_uuid_text);
            majorView = (TextView) itemView.findViewById(R.id.subsc_major_text);
            minorView = (TextView) itemView.findViewById(R.id.subsc_minor_text);
            nameView = (TextView) itemView.findViewById(R.id.subsc_beacon_name_text);
            addressView = (TextView) itemView.findViewById(R.id.subsc_device_address);

            itemView.setOnClickListener(this);

        }

        public void bindSubscribedBeacon(Beacon beacon){
            uuidView.setText(beacon.getUuid());
            majorView.setText(beacon.getMajor());
            minorView.setText(beacon.getMinor());
            nameView.setText(beacon.getName());
            addressView.setText(beacon.getDeviceAddress());
        }

        @Override
        public void onClick(View view) {
            BeaconNameFragment nameFragment = new BeaconNameFragment();
            FragmentManager manager = ((Activity) context).getFragmentManager();
            nameFragment.show(manager, "name_fragment");
        }
    }
}

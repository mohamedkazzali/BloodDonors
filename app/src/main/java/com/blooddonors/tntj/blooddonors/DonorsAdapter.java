package com.blooddonors.tntj.blooddonors;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.ViewHolder> {

    public List<Donors> donorsList;
    public DonorsAdapter(List<Donors> donorsList) {
        this.donorsList = donorsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_donor_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(donorsList.get(position).getFullName());
        holder.bloodText.setText(donorsList.get(position).getBloodGroup());
        holder.phoneText.setText(donorsList.get(position).getMobileNumber());

    }

    @Override
    public int getItemCount() {
        return donorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView bloodText;
        public TextView nameText;
        public TextView phoneText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            bloodText = (TextView) mView.findViewById(R.id.opTxtBloodGroup);
            nameText = (TextView) mView.findViewById(R.id.opTxtFullName);
            phoneText = (TextView) mView.findViewById(R.id.opTxtPhoneNumber);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
}

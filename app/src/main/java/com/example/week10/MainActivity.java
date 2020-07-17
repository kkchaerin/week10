package com.example.week10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.week10.databinding.ActivityMainBinding;
import com.example.week10.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;

class Email{
    CharSequence mLabel;
    String mPName;
    Drawable mIcon;
    public Email(CharSequence sender, String title, Drawable icon){
        mLabel = sender;
        mPName = title;
        mIcon = icon;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    ItemBinding itemBinding;
    MyViewHolder(ItemBinding binding){
        super(binding.getRoot());
        itemBinding = binding;
    }
} //view holder

class MyAdaptor extends RecyclerView.Adapter<MyViewHolder>{
    private List<Email> mEmails;
    MyAdaptor(List<Email> emails){
        mEmails = emails;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBinding itemBinding = ItemBinding.inflate(inflater,parent, false);
        return new MyViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Email email = mEmails.get(position);
        holder.itemBinding.packages.setText(email.mPName);
        holder.itemBinding.label.setText(email.mLabel);
        holder.itemBinding.imageView.setImageDrawable(email.mIcon);
    }

    @Override
    public int getItemCount() {
        return mEmails.size();
    }
}//adapter

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList mEmails =new ArrayList<Email>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(int i=1;i<packages.size();i++) {
            ApplicationInfo appInfo = packages.get(i);
            CharSequence label = appInfo.loadLabel(pm);
            Drawable icon = appInfo.loadIcon(pm);
            String packageName = appInfo.packageName;

            mEmails.add(new Email(label, packageName, icon));
        }
        binding.recyclerview.setAdapter(new MyAdaptor(mEmails));
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}


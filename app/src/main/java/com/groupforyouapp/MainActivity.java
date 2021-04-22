package com.groupforyouapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.groupforyouapp.Models.Users;
import com.groupforyouapp.db.FirebasedbHelper;
import com.groupforyouapp.db.MyCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView nickName = (TextView) headerView.findViewById(R.id.menu_nickname);
        final TextView accType = (TextView) headerView.findViewById(R.id.menu_account_type);

        setSupportActionBar(toolbar);

        // addButton function for create new group
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FirebasedbHelper().getCurUser(new MyCallback() {
                        @Override
                        public void onSuccess(List<?> list) {
                            Users curUser = (Users) list.get(0);
                            if (curUser.getUserType()){
                                Intent intent = new Intent( MainActivity.this, AddGroupActivity.class);
                                startActivity(intent);
                            }else {
                                Snackbar.make(findViewById(R.id.group_root), "Користувач не має прав на створення гуртка", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

            }
        });
         //show nickname and account type in menu_header
        new FirebasedbHelper().getCurUser(new MyCallback() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onSuccess(List<?> list) {
                 Users curUser = (Users) list.get(0);
                 nickName.setText(curUser.getName()+" "+curUser.getS_name());
                 accType.setText(curUser.getUserType()?"Керіник гуртка":"Звичайний користувач");
             }
         });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_filter, R.id.nav_search, R.id.nav_exit)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groups, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

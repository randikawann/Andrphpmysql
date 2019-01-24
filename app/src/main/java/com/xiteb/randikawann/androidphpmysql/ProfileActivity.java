package com.xiteb.randikawann.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    TextView tvusername, tvuseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if(!SharedPreManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        tvusername = findViewById(R.id.tvusername);
        tvuseremail = findViewById(R.id.tvuseremail);

        tvusername.setText(SharedPreManager.getInstance(this).getUsername());

        tvuseremail.setText(SharedPreManager.getInstance(this).getUserEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:
                Toast.makeText(this,"You cicked menu",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLogout:
                SharedPreManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return true;
    }
}

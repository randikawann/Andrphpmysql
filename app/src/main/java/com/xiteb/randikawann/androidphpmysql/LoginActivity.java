package com.xiteb.randikawann.androidphpmysql;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etsignuserNmae, etsignpassword;
    private Button btsignIn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etsignuserNmae = findViewById(R.id.etsignuserNmae);
        etsignpassword = findViewById(R.id.etsignpassword);

        btsignIn = findViewById(R.id.btsignIn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");

        btsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("12345","1");
                userogin();
            }
        });



    }

    private void userogin(){
//        Log.i("12345","2");
        final String signuserName = etsignuserNmae.getText().toString().trim();
        final String signpassword = etsignpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Log.i("12345","5");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                SharedPreManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("username"),
                                                jsonObject.getString("email")

                                        );
                                Toast.makeText(LoginActivity.this, "User Loging Successful",Toast.LENGTH_SHORT).show();
                            }else{
//                                Log.i("12345","6");

                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.i("12345","7");

                        progressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                Log.i("12345","3");
                Map<String, String> params = new HashMap<>();
                params.put("username", signuserName);
                params.put("password", signpassword);
                return params;
            }
        };

//        Log.i("12345","4");
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}

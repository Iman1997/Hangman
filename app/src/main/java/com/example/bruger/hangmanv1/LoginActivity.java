package com.example.bruger.hangmanv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView mTextViewResult;

    Button login;

    RequestQueue queue;
    Gson gson;

    private String username = "s145005";
    private String password = "s145005";
    private int statusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextViewResult = (TextView) findViewById(R.id.resultTextView);

        queue = Volley.newRequestQueue(this);
        gson = new Gson();

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                User user = new User(username, password);
                String jsonUser = gson.toJson(user);
                */


                JSONObject user = new JSONObject();
                try {
                    user.put("username", username);
                    user.put("password", password);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


                loginRequest(user);
            }
        });
    }

    private void loginRequest(final JSONObject user) {
        String url = "http://192.168.174.130/Webservice/login/authenticate";

        queue = Volley.newRequestQueue(getApplicationContext());
        //queue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // Result handling
                        Log.d("STATE",jsonObject.toString() );
                        System.out.println(jsonObject.toString());
                        Intent Home = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(Home);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }

        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                //statusCode = response.statusCode;

                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
        queue.start();
    }

}

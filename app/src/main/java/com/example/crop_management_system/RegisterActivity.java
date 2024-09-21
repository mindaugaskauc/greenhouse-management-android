package com.example.crop_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crop_management_system.UtilService.SharedPreferenceClass;
import com.example.crop_management_system.UtilService.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginBtn;
    private EditText firstname_ET, lastname_ET, email_ET, password_ET;
    private Button registerBtn;
    private CheckBox showHideBtn;
    ProgressBar progressBar;
    private String firstname, lastname, email, password;
    UtilService utilService;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        showHideBtn = findViewById(R.id.showHideBtn);
        showHideBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                if (value)
                {
                    // Show Password
                    password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    // Hide Password
                    password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginBtn = findViewById(R.id.signInBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                finish();
            }
        });

        progressBar = findViewById(R.id.progress_bar);
        firstname_ET = findViewById(R.id.firstname_ET);
        lastname_ET = findViewById(R.id.lastname_ET);
        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);
        utilService = new UtilService();
        sharedPreferenceClass =  new SharedPreferenceClass(this);

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, RegisterActivity.this);
                firstname = firstname_ET.getText().toString();
                lastname = lastname_ET.getText().toString();
                email = email_ET.getText().toString();
                password = password_ET.getText().toString();

                if (validate(view)){
                    registerUser(view);
                }
            }
        });
    }

    private void registerUser(View view) {
        progressBar.setVisibility(View.VISIBLE);

        final HashMap<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);

        String apiKey= Tools.postRestRegisterURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiKey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")){
                        String welcome = response.getString("server_welcome");
                        sharedPreferenceClass.setValue_string("shared_welcome", welcome);

                        Toast.makeText(RegisterActivity.this, welcome, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                        finish();
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null){
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(RegisterActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");

                return params;
            }
        };

        //set retry policy
        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        //request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public boolean validate(View view){
        boolean isValid;
        isValid = true;

        if (TextUtils.isEmpty(firstname)){
            utilService.showSnackbar(view, "Įveskite vardą");
            isValid = false;
        }
        else if (TextUtils.isEmpty(lastname)){
            utilService.showSnackbar(view, "Įveskite pavardę");
            isValid = false;
        }
        else if (TextUtils.isEmpty(email)){
            utilService.showSnackbar(view, "Įveskite elektroninio pašto adresą");
            isValid = false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            utilService.showSnackbar(view, "Įveskite tinkamą elektroninio pašto adreso formatą");
            isValid = false;
        }
        else if (TextUtils.isEmpty(password)){
            utilService.showSnackbar(view, "Įveskite slaptažodį");
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences snapshot_pref = getSharedPreferences("user_snapshot", MODE_PRIVATE);
        if (snapshot_pref.contains("shared_welcome")){
            startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
            finish();
        }
    }
}
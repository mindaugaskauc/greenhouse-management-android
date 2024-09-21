package com.example.crop_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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

public class MainLoginActivity extends AppCompatActivity {

    private TextView registerBtn; //create textView type variable register
    private TextView forgotPasswordBtn; // create textView type variable reset
    private EditText email_ET, password_ET; // create an EditText type variables email_et and password_ET
    private Button loginBtn; // create button variable loginBtn
    private CheckBox showHideBtn; // create a checkbox type variable showHideBtn
    ProgressBar progressBar; // create a ProgressBar type variable progressBar (when clicking sign in, circle is spinning)
    private String email, password; // create a string type variable email and password
    UtilService utilService; // create utilService object
    SharedPreferenceClass sharedPreferenceClass; // create SharedPreference class object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main_login); // show activity_login html

        showHideBtn = findViewById(R.id.showHideBtn); //assign checkBox view shoHideBtn created in activity_login.html
        showHideBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //Register a callback
            // to be invoked when the checked state of the button changes
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                if (value) //if there is some value
                {
                    // Show Password
                    password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //text field HideReturnsTransformation shows password and ignores carriage return characters
                    // carriage return characters
                    //\r (Carriage Return) → moves the cursor to the beginning of the line without advancing to the next line
                    // \n (Line Feed) → moves the cursor down to the next line without returning to the beginning of the line — In a *nix environment \n moves to the beginning of the line.
                    // \r\n (End Of Line) → a combination of \r and \n

                }
                else //if value is not entered
                {
                    // Hide Password
                    password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // the text field transformation method PasswordTransformationMethod hides password
                }
            }
        });

        registerBtn = findViewById(R.id.signUpBtn); // registerBtn textView type variable assigned signUpBtn textView from activity_login.html
        registerBtn.setOnClickListener(new View.OnClickListener() { // register a callback to be called when this view is cliecked
            @Override
            public void onClick(View view) { // when signUpBtn textView is clicked
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class)); //Return RegisterActivity class
                finish();
            }
        });

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn); // resetBtn textView type variable assigned resetBtn textBiew from activity_login.html
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() { // register a callback to be called when resetBtn view is clicked
            @Override
            public void onClick(View view) { //when this view is clicked
                startActivity(new Intent(getApplicationContext(), SendEmailActivity.class)); //ResetActivityClass is called
                finish();
            }
        });



        loginBtn = findViewById(R.id.signInBtn);
        progressBar = findViewById(R.id.progress_bar);
        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);
        utilService = new UtilService();
        sharedPreferenceClass = new SharedPreferenceClass(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, MainLoginActivity.this);
                email = email_ET.getText().toString();
                password = password_ET.getText().toString();

                if (validate(view)) {
                    loginUser(view);
                }
            }
        });
    }

    private void loginUser(View view) {

        progressBar.setVisibility(View.VISIBLE);

        final HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        String apiKey= Tools.postRestLoginURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiKey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")){
                        String welcome = response.getString("server_welcome");
                        sharedPreferenceClass.setValue_string("shared_welcome", welcome);

                        Toast.makeText(MainLoginActivity.this, welcome, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainLoginActivity.this, NavigationActivity.class));
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
                        Toast.makeText(MainLoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

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

    public boolean validate(View view) {
        boolean isValid;
        isValid = true;

        if (TextUtils.isEmpty(email)){
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
            startActivity(new Intent(MainLoginActivity.this, NavigationActivity.class));
            finish();
        }
    }
}
package com.example.crop_management_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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


public class SendEmailActivity extends AppCompatActivity {

    private TextView loginBtn; // create a loginBtn TextView type variable to store singBtn Textview view from activity_reset.html
    private EditText email_ET; //create an EditText type variable email_ET to store email text field
    private Button sendEmailBtn;
    ProgressBar progressBar;
    private String email; //create a string type variable email to store email text
    UtilService utilService;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        loginBtn = findViewById(R.id.signInBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                finish();
            }
        });

        sendEmailBtn = findViewById(R.id.sendEmailBtn);
        progressBar = findViewById(R.id.progress_bar);
        email_ET = findViewById(R.id.email_ET);
        utilService = new UtilService();
        sharedPreferenceClass = new SharedPreferenceClass(this);

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, SendEmailActivity.this);
                email = email_ET.getText().toString();

                if (validate(view)) {
                    resetUser(view);
                }
            }
        });
    }

    private void resetUser(View view) {

        progressBar.setVisibility(View.VISIBLE);

        final HashMap<String, String> params = new HashMap<>();
        params.put("email", email);

        //String apiKey= "https://snapshotproject.herokuapp.com/api/snapshot/auth/login";
        String apiKey= Tools.postRestSendPasswordLinkUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiKey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")){
                        String message = response.getString("msg");
                        sharedPreferenceClass.setValue_string("success_message", message);

                        Toast.makeText(SendEmailActivity.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SendEmailActivity.this, SendEmailActivity.class)); // after clicking the button , stays in the same page
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
                        Toast.makeText(SendEmailActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

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

        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences snapshot_pref = getSharedPreferences("user_snapshot", MODE_PRIVATE);
        if (snapshot_pref.contains("token")){
            startActivity(new Intent(SendEmailActivity.this, SendEmailActivity.class));
            finish();
        }
    }
}
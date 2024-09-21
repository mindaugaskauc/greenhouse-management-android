package com.example.crop_management_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crop_management_system.UtilService.SharedPreferenceClass;
import com.example.crop_management_system.UtilService.UtilService;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_water#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_water extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Create a request queue
   // RequestQueue myRequestQueue;
    // Create a string
  //private String water_crop;
  //  private String water_crop2;


    UtilService utilService;
    SharedPreferenceClass sharedPreferenceClass;



    public nav_water() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_water.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_water newInstance(String param1, String param2) {
        nav_water fragment = new nav_water();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nav_water, container, false);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_nav_water, container, false);
        Switch switchButton = (Switch) root.findViewById(R.id.switch1);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Instantiate the myRequestQueue object
                RequestQueue myRequestQueue = Volley.newRequestQueue(getActivity());
                String water_plant; // create a water_plant variable
                // final HashMap<String, String> params = new HashMap<>();
                if (isChecked) {

                /*    // The toggle is enabled
                    final HashMap<String, String> paramsOn = new HashMap<>();
                    water_crop1 = "1";
                    //https://beginnersbook.com/2013/12/hashmap-in-java-with-example/
                    paramsOn.put("water_crop1", water_crop1);
                    // https://stackoverflow.com/questions/16780294/how-to-print-to-the-console-in-android-studio
                    //  Log.d("myTag", params.get(water_crop));
                    String apiKey = Tools.postWateringURL;
                    Log.d("myTagApi", apiKey);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            apiKey, new JSONObject(paramsOn), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    String welcome = response.getString("server_answer");
                                    sharedPreferenceClass.setValue_string("shared_welcome", welcome);
                                    String test3 = sharedPreferenceClass.getValue_string("shared_welcome");
                                    Log.d("myTagAShared3", test3);

                                    //https://stackoverflow.com/questions/10770055/use-toast-inside-fragment
                                    Toast.makeText(getActivity(), welcome, Toast.LENGTH_LONG).show();
                                    //https://stackoverflow.com/questions/15478105/start-an-activity-from-a-fragment
                                    startActivity(new Intent(getActivity(), nav_water.class));
                                    //https://stackoverflow.com/questions/7907900/finishing-current-activity-from-a-fragment
                                    getActivity().finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                                    JSONObject obj = new JSONObject(res);
                                    Toast.makeText(getActivity(), obj.getString("msg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException | UnsupportedEncodingException je) {
                                    je.printStackTrace();
                                }
                            }
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");

                            return paramsOn;
                        }
                    };

                    //set retry policy
                    int socketTime = 3000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsonObjectRequest.setRetryPolicy(policy);

                    //request add
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                  */
                    // The toggle is enabled
                    water_plant = "WATER ON";
                    String apiKey = Tools.postWateringOnURL;
                    StringRequest stringRequestOn = new StringRequest(Request.Method.POST, apiKey, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //This code is executed if the server responds, whether or not the response contains data.
                            //The String 'response' contains the server's response.
                        }
                    }, new Response.ErrorListener()
                    { //Create an error listener to handle errors appropriately.
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //This code is executed if there is an error.
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();
                            MyData.put("water_plant", water_plant); //Add the data you'd like to send to the server.
                            return MyData;
                        }

                    };

                    // Add the request to turn on the button to the queue and set the function
                    // to run for a default number of retries when else condition is true (if not set, it will run forever)
                    stringRequestOn.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    myRequestQueue.add(stringRequestOn);
                    myRequestQueue.cancelAll(stringRequestOn);

                } else {
                    // The toggle is disabled
                    water_plant = "WATER OFF";
                    String apiKey = Tools.postWateringOffURL;
                    StringRequest stringRequestOff = new StringRequest(Request.Method.POST, apiKey, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //This code is executed if the server responds, whether or not the response contains data.
                            //The String 'response' contains the server's response.
                        }
                    }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //This code is executed if there is an error.
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();
                            MyData.put("water_plant", water_plant); //Add the data you'd like to send to the server.
                            return MyData;
                        }
                    };

                    stringRequestOff.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                   myRequestQueue.add(stringRequestOff);
                    myRequestQueue.cancelAll(stringRequestOff);

                }

            }

        });

        return root;


        // Inflate the layout for this fragment
       // ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_nav_s_temp, container, false);
       // Button Show = (Button) root.findViewById(R.id.button_show_s_temp);
    }
}
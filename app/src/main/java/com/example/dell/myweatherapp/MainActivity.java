package com.example.dell.myweatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    Button button;
    EditText city;
    TextView result;

    // http://api.openweathermap.org/data/2.5/weather?q=ghazipur&appid=edd403945655a9ac514da196cb5c9f92

    String baseURL="http://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=edd403945655a9ac514da196cb5c9f92";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        city=findViewById(R.id.getCity);
        result=findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL=baseURL+city.getText().toString()+API;
                Log.i("URL","URL: "+URL);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("JSON","JSON: "+jsonObject);

                        try {
                            String info=jsonObject.getString("weather");
                            Log.i("info","info:  "+info);

                            JSONArray ar=new JSONArray(info);
                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject parObj=ar.getJSONObject(i);
                                String weather=parObj.getString("main");
                                result.setText(weather);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error","Somethig went wrong: "+error);
                            }
                        }

                );

                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
                
            }
        });





    }
}

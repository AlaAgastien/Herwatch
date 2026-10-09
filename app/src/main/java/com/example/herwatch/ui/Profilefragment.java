package com.example.herwatch.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.job.Home;
//import com.example.job.R;
//import com.example.job.SessionManager;
//import com.example.job.config;
import com.example.herwatch.Choose_user;
import com.example.herwatch.Home;
import com.example.herwatch.Login;
import com.example.herwatch.R;
//import com.example.bloodbank.SessionManager;
import com.example.herwatch.Sessionmanager;
import com.example.herwatch.config;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Profilefragment extends Fragment {

    EditText proname1,proemail1,prophone1,proguard1,propass1;
    Button proupdate1;
    TextView prohome1;

    String proname2, proemail2, prophone2,proguard2,propass2,url= config.baseurl+"profileupdation.php";
    String ppid,pproname, pproemail, pprophone,pproguard,ppropass,status,message;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profilefragment2, container, false);

        proname1 = v.findViewById(R.id.proname);
        proemail1 = v.findViewById(R.id.proemail);
        prophone1=v.findViewById(R.id.prophone);
        proguard1=v.findViewById(R.id.proguard);
        propass1 = v.findViewById(R.id.propass);
        proupdate1 = v.findViewById(R.id.proupdate);
        prohome1= v.findViewById(R.id.prohome);

        HashMap<String,String> data=new Sessionmanager(getActivity()).getUserDetails();



        ppid=data.get("id");
        proname2=data.get("name");
        proemail2=data.get("email");
        prophone2=data.get("phone");
        proguard2=data.get("guardiannumber");
        propass2=data.get("password");

        proname1.setText(proname2);
        proemail1.setText(proemail2);
        prophone1.setText(prophone2);
        proguard1.setText(proguard2);
        propass1.setText(propass2);

        prohome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout1();
            }
        });

        proupdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });
        return v;
    }
    private void submit() {
        pproname=proname1.getText().toString();
        pproemail=proemail1.getText().toString();
        pprophone=prophone1.getText().toString();
        pproguard=proguard1.getText().toString();
        ppropass=propass1.getText().toString();

        if (TextUtils.isEmpty(proname2)){
            proname1.requestFocus();
            proname1.setError("required field");
            return;
        }

//        if (TextUtils.isEmpty(pregadde)){
//            regadde.requestFocus();
//            regadde.setError("required field");
//            return;
//        }
        if (TextUtils.isEmpty(proemail2)){
            proemail1.requestFocus();
            proemail1.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(prophone2)){
            prophone1.requestFocus();
            prophone1.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(proguard2)){
            proguard1.requestFocus();
            proguard1.setError("required field");
            return;
        }

        if (TextUtils.isEmpty(propass2)){
            propass1.requestFocus();
            propass1.setError("required field");
            return;
        }
        StringRequest str = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("0".equals(status)) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "updation successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Home.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",ppid);
                params.put("name",pproname);
                params.put("email",pproemail);
                params.put("phone",pprophone);
                params.put("guardiannumber",pproguard);
                params.put("password",ppropass);

                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(str);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Object binding = null;
    }
    private void logout1() {
        new AlertDialog.Builder(requireContext()) // Use requireContext() for Fragment
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Correct SessionManager call
                        new Sessionmanager(requireContext()).logoutUser();

                        // Use requireActivity() for launching an Intent from a Fragment
                        startActivity(new Intent(requireActivity(), Choose_user.class));
                        requireActivity().finish(); // Finish the activity hosting the fragment
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Dismiss the dialog
                    }
                })
                .show();
    }


}

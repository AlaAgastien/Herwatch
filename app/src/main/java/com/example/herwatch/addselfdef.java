package com.example.herwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class addselfdef extends AppCompatActivity {
    EditText t, d;
    Button vdo;
    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;
    String url = config.baseurl + "addselfdef.php";
    String teacher, tphn;
    byte[] inputData = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfdef);

        t = findViewById(R.id.title);
        d = findViewById(R.id.description);
        vdo = findViewById(R.id.upload);
        rQueue = Volley.newRequestQueue(this);

        HashMap<String, String> map = new Sessionmanager(addselfdef.this).getUserDetails();
        teacher = map.get("name");
        tphn = map.get("phone");

        vdo.setOnClickListener(view -> upload1());
    }

    private void upload1() {
        String ca = t.getText().toString().trim();
        String snote = d.getText().toString().trim();

        if (TextUtils.isEmpty(ca)) {
            t.setError("Title is required");
            t.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(snote)) {
            d.setError("Description is required");
            d.requestFocus();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/mp4");
        startActivityForResult(intent, 1);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("File Name", displayName);
                        uploadVideo(displayName, uri);
                    }
                }
            }
        }
    }

    private void uploadVideo(final String videoName, Uri videoFile) {
        try (InputStream iStream = getContentResolver().openInputStream(videoFile)) {
            if (iStream == null) {
                Toast.makeText(this, "Failed to open file", Toast.LENGTH_SHORT).show();
                return;
            }

            inputData = getBytes(iStream);

            if (inputData == null || inputData.length == 0) {
                Toast.makeText(this, "Error: Unable to process video file", Toast.LENGTH_SHORT).show();
                return;
            }

            showSimpleProgressDialog(this, "Uploading", "Uploading video...", false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    response -> {
                        removeSimpleProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(addselfdef.this, message, Toast.LENGTH_SHORT).show();
                            if ("1".equals(status)) {
                                startActivity(new Intent(addselfdef.this, Home.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        removeSimpleProgressDialog();
                        Toast.makeText(addselfdef.this, "Upload Failed: " + error.toString(), Toast.LENGTH_LONG).show();
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", teacher);
                    params.put("userphone", tphn);
                    params.put("title", t.getText().toString().trim());
                    params.put("description", d.getText().toString().trim());
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    if (videoName != null && inputData != null) {
                        params.put("filename", new DataPart(videoName, inputData));
                    }
                    return params;
                }
            };

            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            rQueue.add(volleyMultipartRequest);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File Processing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void showSimpleProgressDialog(Context context, String title, String message, boolean isCancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(isCancelable);
            mProgressDialog.show();
        }
    }

    private void removeSimpleProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}

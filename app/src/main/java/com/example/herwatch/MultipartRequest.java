package com.example.herwatch;

import com.android.volley.Response;
import java.util.HashMap;
import java.util.Map;

public class MultipartRequest extends VolleyMultipartRequest {
    private Response.Listener<String> mListener;

    public MultipartRequest(
            String url,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {
        super(Method.POST, url, response -> {
            String parsed;
            try {
                parsed = new String(response.data, "UTF-8");
            } catch (Exception e) {
                parsed = new String(response.data);
            }
            listener.onResponse(parsed);
        }, errorListener);
        this.mListener = listener;
    }

    @Override
    protected Map<String, String> getParams() {
        return new HashMap<>();
    }

    @Override
    protected Map<String, DataPart> getByteData() {
        return new HashMap<>();
    }
}
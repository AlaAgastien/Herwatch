package com.example.herwatch.ui.home;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.herwatch.MultipartRequest;
import com.example.herwatch.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.io.ByteArrayOutputStream;

import android.telephony.SmsManager;
import android.util.Base64;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.herwatch.Sessionmanager;
import com.example.herwatch.VolleyMultipartRequest;
import com.example.herwatch.VolleyMultipartRequest2;
import com.example.herwatch.config;
import com.example.herwatch.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "HomeFragment";

    private static final int SMS_PERMISSION_REQUEST_CODE = 66;
    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private static final float SHAKE_THRESHOLD = 20f;
    private static final int CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST = 3;
    private boolean isProcessingShake = false;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: Initializing map");
        mMap = googleMap;
        enableMyLocation();
    }

    private void enableMyLocation() {
        Log.d(TAG, "enableMyLocation: Checking location permissions");
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "enableMyLocation: Location permission granted");
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
            // Request camera permission after location permission is granted
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) 
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation: Attempting to get current location");
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            Log.i(TAG, String.format("getCurrentLocation: Location obtained - Lat: %f, Long: %f", 
                                    location.getLatitude(), location.getLongitude()));
                            LatLng currentLocation = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required for emergency alerts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    
        // Initialize sensors
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    
        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    
        // Get the SupportMapFragment and request notification when the map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Set up capture button click listener
        binding.captureButton.setOnClickListener(v -> handleEmergency());
    
        return root;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
    
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
    
            float deltaX = Math.abs(lastX - x);
            float deltaY = Math.abs(lastY - y);
            float deltaZ = Math.abs(lastZ - z);
    
            if ((deltaX > SHAKE_THRESHOLD || deltaY > SHAKE_THRESHOLD || deltaZ > SHAKE_THRESHOLD) && !isProcessingShake) {
                isProcessingShake = true;
                handleEmergency();
            }
    
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };
    
    private void handleEmergency() {
        Log.d(TAG, "handleEmergency: Processing emergency alert");
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission
            requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_CODE
            );
        } else {
            launchCamera();
        }
    }

    private CameraHelper cameraHelper;
    private boolean isCameraInitialized = false;

    private void launchCamera() {
        Log.d(TAG, "launchCamera: Initializing camera capture");
        if (cameraHelper == null) {
            cameraHelper = new CameraHelper(requireContext(), getViewLifecycleOwner());
        }

        if (!isCameraInitialized) {
            cameraHelper.initializeCamera(() -> {
                isCameraInitialized = true;
                captureImage();
            });
        } else {
            captureImage();
        }
    }

    private void captureImage() {
        Log.d(TAG, "captureImage: Starting image capture process");
        cameraHelper.captureImage(new CameraHelper.CaptureCallback() {
            @Override
            public void onCaptureSuccess(Bitmap photo) {
                getCurrentLocationAndSendAlert(photo);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), "Error capturing image: " + message, Toast.LENGTH_SHORT).show();
                isProcessingShake = false;
            }
        });
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraHelper != null) {
            cameraHelper.shutdown();
        }
        binding = null;
    }
    
    private void getCurrentLocationAndSendAlert(Bitmap photo) {
        Log.d(TAG, "getCurrentLocationAndSendAlert: Getting location for alert");
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            sendEmergencyAlert(photo, location);
                        }
                    });
        }
    }
    
    private void sendEmergencyAlert(Bitmap photo, Location location) {
        Log.d(TAG, "sendEmergencyAlert: Preparing emergency alert with photo and location");
        if (photo == null || location == null) {
            Log.e(TAG, "sendEmergencyAlert: Invalid photo or location data");
            Toast.makeText(requireContext(), "Invalid emergency data", Toast.LENGTH_SHORT).show();
            isProcessingShake = false;
            return;
        }

        // Create a temporary file from the bitmap
        final File imageFile;
        FileOutputStream fos = null;
        try {
            File cacheDir = requireContext().getCacheDir();
            imageFile = File.createTempFile("emergency_", ".jpg", cacheDir);
            fos = new FileOutputStream(imageFile);
            if (!photo.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                throw new IOException("Failed to compress image");
            }
        } catch (IOException e) {
            Log.e(TAG, "sendEmergencyAlert: Error creating image file", e);
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException closeError) {
                    closeError.printStackTrace();
                }
            }
            Toast.makeText(requireContext(), "Error creating image file", Toast.LENGTH_SHORT).show();
            isProcessingShake = false;
            return;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Create emergency message with image URL placeholder
        String emergencyMessage = String.format("Emergency! Location: http://maps.google.com/maps?q=%f,%f\nImage will be available at: %s",
                location.getLatitude(), location.getLongitude(), config.baseurl + "emergency_images/[image_name]");

        // Prepare multipart request
        String url = config.baseurl + "send_emergency.php";
        VolleyMultipartRequest2 multipartRequest = new VolleyMultipartRequest2(
                Request.Method.POST,
                url,
                response -> {
                    try {
                        String responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject jsonResponse = new JSONObject(responseData);
                        if ("1".equals(jsonResponse.getString("status"))) {
                            String imagePath = jsonResponse.getString("image_path");
                            System.out.println("IMSGe:"+imagePath );
                            String finalMessage = emergencyMessage.replace("[image_name]", imagePath);
                            sendAlertToFriends(finalMessage);
                            Log.i(TAG, "sendEmergencyAlert: Alert sent successfully");
                            Toast.makeText(requireContext(), "Emergency alert sent", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = jsonResponse.optString("message", "Unknown server error");
                            String errorType = jsonResponse.optString("error_type", "unknown");
                            Log.e(TAG, String.format("sendEmergencyAlert: Server error - %s (Type: %s)", errorMessage, errorType));
                            Toast.makeText(requireContext(), "Failed to send alert: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "sendEmergencyAlert: Error processing server response", e);
                        Toast.makeText(requireContext(), "Error processing server response", Toast.LENGTH_SHORT).show();
                    } finally {
                        isProcessingShake = false;
                        // Clean up the temporary file
                        if (imageFile != null && imageFile.exists()) {
                            imageFile.delete();
                        }
                    }
                },
                error -> {
                    Log.e(TAG, "sendEmergencyAlert: Network error", error);
                    String errorMessage = "Network error";
                    if (error.networkResponse != null) {
                        errorMessage += String.format(" (Status: %d)", error.networkResponse.statusCode);
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    isProcessingShake = false;
                    // Clean up the temporary file
                    if (imageFile != null && imageFile.exists()) {
                        imageFile.delete();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", new Sessionmanager(requireContext()).getUserDetails().get("phone"));
                params.put("message", emergencyMessage);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    final byte[] fileBytes = Files.readAllBytes(imageFile.toPath());
                    if (fileBytes.length == 0) {
                        throw new IOException("Empty image file");
                    }
                    params.put("image", new DataPart(imageFile.getName(), fileBytes, "image/jpeg"));
                } catch (IOException e) {
                    Log.e(TAG, "sendEmergencyAlert: Error reading image file", e);
                    throw new AuthFailureError("Failed to read image file");
                }
                return params;
            }
        };

        // Add request timeout and retry policy
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000, // 30 seconds timeout
                2, // Reduce retry attempts
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add the request to the queue
        Volley.newRequestQueue(requireContext()).add(multipartRequest);
    }

    private void sendAlertToFriends(String message) {
        Log.d(TAG, "sendAlertToFriends: Fetching friends list to send alerts");
        String url = config.baseurl + "frdslist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject friend = array.getJSONObject(i);
                            String friendPhone = friend.getString("fphonenumber");
                            System.out.println("FPHONE:"+friendPhone);

                            sendSMSToFriend(friendPhone, message);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "sendEmergencyAlert: Error creating image file", e);
                        Log.e(TAG, "sendAlertToFriends: Error processing friend list", e);
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error sending alerts to friends", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(requireContext(), "Error fetching friends list", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phonenumber", new Sessionmanager(requireContext()).getUserDetails().get("phone"));
                return params;
            }
        };
        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }

    private void sendSMSToFriend(String phoneNumber, String message) {
        Log.d(TAG, "sendSMSToFriend: Sending SMS to " + phoneNumber);
        try {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) 
                    == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> parts = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        SMS_PERMISSION_REQUEST_CODE);
            }
        } catch (Exception e) {
            Log.e(TAG, "sendEmergencyAlert: Error creating image file", e);
            Log.e(TAG, "sendSMSToFriend: Failed to send SMS to " + phoneNumber, e);
            e.printStackTrace();
            Toast.makeText(requireContext(), 
                    "Failed to send SMS to " + phoneNumber, 
                    Toast.LENGTH_SHORT).show();
        }
    }
}
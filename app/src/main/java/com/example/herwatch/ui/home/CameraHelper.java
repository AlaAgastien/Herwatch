package com.example.herwatch.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Size;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraHelper {
    private final Context context;
    private final LifecycleOwner lifecycleOwner;
    private final Executor mainExecutor;
    private ImageCapture imageCapture;
    private ProcessCameraProvider cameraProvider;

    public interface CaptureCallback {
        void onCaptureSuccess(Bitmap photo);
        void onError(String message);
    }

    public CameraHelper(Context context, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.mainExecutor = ContextCompat.getMainExecutor(context);
    }

    public void initializeCamera(Runnable onInitialized) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = 
            ProcessCameraProvider.getInstance(context);

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                imageCapture = new ImageCapture.Builder()
                    .setTargetResolution(new Size(1080, 1920))
                    .build();

                // Select front camera
                CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build();

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    imageCapture
                );

                onInitialized.run();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, mainExecutor);
    }

    public void captureImage(CaptureCallback callback) {
        if (imageCapture == null) {
            callback.onError("Camera not initialized");
            return;
        }

        imageCapture.takePicture(mainExecutor,
            new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(ImageProxy image) {
                    Bitmap photo = imageProxyToBitmap(image);
                    image.close();
                    callback.onCaptureSuccess(photo);
                }

                @Override
                public void onError(ImageCaptureException error) {
                    callback.onError(error.getMessage());
                }
            });
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // Rotate the bitmap if needed
        Matrix matrix = new Matrix();
        matrix.postRotate(image.getImageInfo().getRotationDegrees());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), 
            bitmap.getHeight(), matrix, true);
    }

    public void shutdown() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
        }
    }
}
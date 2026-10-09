package com.example.herwatch;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.HashMap;

public class Translatactivity extends AppCompatActivity {

    private EditText inputText;
    private Spinner languageSpinner;
    private Button translateButton;
    private TextView translatedText;

    private HashMap<String, String> languageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translatactivity);

        inputText = findViewById(R.id.inputText);
        languageSpinner = findViewById(R.id.languageSpinner);
        translateButton = findViewById(R.id.translateButton);
        translatedText = findViewById(R.id.translatedText);

        setupLanguageSpinner();

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateText();
            }
        });
    }

    private void setupLanguageSpinner() {
        String[] languages = {"English", "Malayalam", "Tamil", "Hindi", "Marathi", "Kannada", "French", "Arabic"};
        languageMap = new HashMap<>();
        languageMap.put("English", "en");
        languageMap.put("Malayalam", "ml");
        languageMap.put("Tamil", "ta");
        languageMap.put("Hindi", "hi");
        languageMap.put("Marathi", "mr");
        languageMap.put("Kannada", "kn");
        languageMap.put("French", "fr");
        languageMap.put("Arabic", "ar");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
    }

    private void translateText() {
        String text = inputText.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter text to translate", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        String targetLangCode = languageMap.get(selectedLanguage);

        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage("en")
                .setTargetLanguage(targetLangCode)
                .build();

        Translator translator = Translation.getClient(options);

        translator.downloadModelIfNeeded()
                .addOnSuccessListener(aVoid -> translator.translate(text)
                        .addOnSuccessListener(translatedText::setText)
                        .addOnFailureListener(e -> Toast.makeText(Translatactivity.this, "Translation failed!", Toast.LENGTH_SHORT).show()))
                .addOnFailureListener(e -> Toast.makeText(Translatactivity.this, "Model download failed!", Toast.LENGTH_SHORT).show());
    }
}
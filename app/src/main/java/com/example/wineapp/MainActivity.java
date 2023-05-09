package com.example.wineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Resources resources;
    private TextView title;
    private TextView label;
    private EditText code_input;
    private Button submit_button;
    private ProgressBar loadingIndicator;
    private String goodcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // récupérer les ressources
        resources = getResources();

        //initialisation
        title = findViewById(R.id.title);
        label = findViewById(R.id.label);
        code_input = findViewById(R.id.code_input);
        submit_button = findViewById(R.id.submit_button);
        loadingIndicator = findViewById(R.id.loading_indicator);

        //appel API
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Créer l'URL de la requête de login
                    URL loginUrl = new URL("https://la-forge-des-enigmes.capucinemad.com/api/login");

                    // Ouvrir la connexion HTTP pour la requête de login
                    HttpURLConnection loginConnection = (HttpURLConnection) loginUrl.openConnection();
                    loginConnection.setRequestMethod("POST");
                    loginConnection.setRequestProperty("Content-Type", "application/json");

                    // Paramètres de la requête de login
                    String loginParams = "{ \"username\": \"machine1@mail.com\", \"password\": \"A@vF7%LW5N&sfo\" }";

                    // Envoyer la requête de login
                    loginConnection.setDoOutput(true);
                    DataOutputStream loginOutputStream = new DataOutputStream(loginConnection.getOutputStream());
                    loginOutputStream.writeBytes(loginParams);
                    loginOutputStream.flush();
                    loginOutputStream.close();

                    // Lire la réponse de la requête de login
                    int loginResponseCode = loginConnection.getResponseCode();
                    String token = null;
                    if (loginResponseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(loginConnection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Récupérer le token de la réponse de login
                        JSONObject tokenjsonResponse = new JSONObject(response.toString());
                        token = tokenjsonResponse.getString("token");
                    } else {
                        // Afficher une erreur si la requête de login a échoué
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(resources.getString(R.string.error));
                                title.setVisibility(View.VISIBLE);
                                loadingIndicator.setVisibility(View.GONE);
                            }
                        });
                        return;
                    }

                    // Créer l'URL de la requête de récupération du JSON
                    URL jsonUrl = new URL("https://la-forge-des-enigmes.capucinemad.com/api/wine_games/1");

                    // Ouvrir la connexion HTTP pour la requête de récupération du JSON
                    HttpURLConnection jsonConnection = (HttpURLConnection) jsonUrl.openConnection();
                    jsonConnection.setRequestMethod("GET");
                    jsonConnection.setRequestProperty("Authorization", "Bearer " + token);

                    // Envoyer la requête de récupération du JSON
                    int jsonResponseCode = jsonConnection.getResponseCode();
                    if (jsonResponseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(jsonConnection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Afficher le JSON dans la console
                        String json = response.toString();
                        System.out.println("JSON: " + json);

                        JSONObject jsonResponse = new JSONObject(response.toString());

                        // afficher la page
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    label.setText(jsonResponse.getString("userCodeName"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    goodcode = jsonResponse.getString("userCode");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                loadingIndicator.setVisibility(View.GONE);
                                title.setVisibility(View.VISIBLE);
                                label.setVisibility(View.VISIBLE);
                                code_input.setVisibility(View.VISIBLE);
                                submit_button.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        // Afficher une erreur si la requête de récupération du JSON a échoué
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(resources.getString(R.string.error));
                                title.setVisibility(View.VISIBLE);
                                loadingIndicator.setVisibility(View.GONE);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(resources.getString(R.string.error));
                            title.setVisibility(View.VISIBLE);
                            loadingIndicator.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();

        //Btn validation
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code_input.getText().length() > 0 && code_input.getText().toString().equals(goodcode)){
                    Intent intent = new Intent(MainActivity.this, WineActivity.class);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), resources.getString(R.string.error_code), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
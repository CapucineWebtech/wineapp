package com.example.wineapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderActivity extends AppCompatActivity {
    private Context context;
    private Resources resources;
    private static final String KEY_SAVE_HINT = "savehint";
    private TextView hint;
    private String savehint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // récupérer le context
        context = getApplicationContext();

        // récupérer les ressources
        resources = getResources();

        //initialisation
        hint = findViewById(R.id.hint);
        Button button_return = findViewById(R.id.button_return);
        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);
        ImageButton button3 = findViewById(R.id.button3);

        //restore les données
        if(savedInstanceState != null){
            savehint = savedInstanceState.getString(KEY_SAVE_HINT);
            hint.setText(savehint);
        }else{
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
                            // Afficher une erreur si la requête de login a échoué
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hint.setText(resources.getString(R.string.error_hint));
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
                            savehint = jsonResponse.getString("hint");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hint.setText(savehint);
                                }
                            });
                        } else {
                            // Afficher une erreur si la requête de récupération du JSON a échoué
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hint.setText(resources.getString(R.string.error_hint));
                                }
                            });
                        }
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hint.setText(resources.getString(R.string.error_hint));
                            }
                        });
                    }
                }
            }).start();
        }

        //Btn return
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        //Btn
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WineActivity.class);
                intent.putExtra(WineActivity.KEY_PAGE_NUMBER, "0");
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WineActivity.class);
                intent.putExtra(WineActivity.KEY_PAGE_NUMBER, "1");
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WineActivity.class);
                intent.putExtra(WineActivity.KEY_PAGE_NUMBER, "2");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SAVE_HINT, savehint);
    }
}
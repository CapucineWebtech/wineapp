package com.example.wineapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WineActivity extends AppCompatActivity {

    private Integer pageNumber;
    public static final String KEY_PAGE_NUMBER = "0";
    private TextView page_title;
    private ScrollView scroll_view;
    private static final String[] PAGE_TITLES = {
            "Catalogue de vin rouge", "Catalogue de vin blanc", "Catalogue de vin rosé et autres"
    };
    private static final String[][] WINE_TITLES = {
            {"CHATEAUVIEUX", "PROBUS MONT DOUR", "CLOS DE LA ROUE", "SAINT-DIDIER", "LA SAROMAGNOTE", "LE BOIS DE LISSIEU", "VARAX SYRAH"},
            {"CHAMP DE LORGE", "PROBUS MONT DOUR", "LA SAROMAGNOTE", "L'ORCHIDEE", "VARAX VIOGNIER"},
            {"LA CABANE", "LA SAROMAGNOTE - ROSE", "BULLE LYONNAISE", "BLANC DE BLANC", "QUETZAL", "LEON"}
    };
    private static final String[][] WINE_POINT1 = {
            {"•  Village de production : Chasselay", "•  Village de production : Marcilly d’Azergues", "•  Village de production : Lissieu", "•  Village de production : Saint-Didier au Mont d’Or", "•  Village de production : Saint-Romain au Mont d’Or", "•  Village de production : Lissieu", "•  Village de production : Marcilly d’Azergues"},
            {"•  Village de production : Chasselay", "•  Village de production : Chasselay", "•  Village de production : Saint-Romain au Mont d’Or", "•  Village de production : Chasselay", "•  Village de production : Marcilly d’Azergues"},
            {"•  Village de production : Chasselay", "•  Village de production : Marcilly d’Azergues", "•  Village de production : Lissieu", "•  Village de production : Saint-Didier au Mont d’Or", "•  Village de production : Chasselay", "•  Village de production : Chasselay"}
    };
    private static final String[][] WINE_POINT2 = {
            {"•  Cépage : 100%Gamay", "•  Cépage : 100%Gamay", "•  Cépage : 100%Gamay (+ de 1000 sortes de gamay)", "•  Cépage : 100%Gamay", "•  Cépage : 65% Pinot Noir 35% Syrah", "•  Cépage : 100 % Pinot Noir", "•  Cépage : 100 % Syrah"},
            {"•  Cépage : 100% Chardonnay", "•  Cépage : 100% Chardonnay", "•  Cépage : 50 % Chardonnay – 50% Viognier", "•  Cépage : 100 % Viognier", "•  Cépage : 100 % Viognier"},
            {"•  Cépage : 100% Gamay", "•  Cépage : 100%Gamay", "•  Cépage : 100%Gamay (+ de 1000 sortes de gamay)", "•  Cépage : 100%Gamay", "•  Cépage : 100 % Chardonnay en vendanges tardives", "•  Cépage : 100 % Chardonnay"}
    };
    private static final String[][] WINE_POINT3 = {
            {"•  Sol : terroir granitique", "•  Sol : terroir de gravières", "•  Sol : Terroir argile et pierres dorées", "•  Sol : argilo-calcaire", "•  Sol : calcaires bajociens", "•  Sol : terroir d’argile et pierres dorées", "•  Sol : gravières et silex"},
            {"•  Sol : terroir argilo limoneux", "•  Sol : terroir argilo limoneux", "•  Sol : calcaires bajociens", "•  Sol : terroir granitique", "•  Sol : gravières et silex"},
            {"•  Sol : terroir granitique", "•  Sol : terroir de gravières", "•  Sol : Terroir argile et pierres dorées", "•  Sol : argilo-calcaire", "•  Sol : terroir argilo limoneux", "•  Sol : terroir argilo limoneux"}
    };
    private static final String[][] WINE_POINT4 = {
            {"•  Elevage : cuve pendant 10 mois", "•  Elevage : fût de chêne pendant 10 mois", "•  Elevage : cuve pendant 10 mois", "•  Elevage : fût de chêne pendant 12 mois", "•  Elevage : fût de chêne pendant 8 mois", "•  Elevage : fût de chêne pendant 10 mois", "•  Elevage : fût de chêne pendant 8 mois"},
            {"•  Elevage : Cuve pendant 6 mois", "•  Elevage : Fût de chêne pendant 12 mois", "•  Elevage : fût de chêne pendant 12 mois", "•  Elevage : fût de chêne pendant 10 mois", "•  Elevage : fût de chêne pendant 12 mois"},
            {"•  Elevage : cuve pendant 6 mois sur lie", "•  Elevage : fût de chêne pendant 10 mois", "•  Elevage : cuve pendant 10 mois", "•  Elevage : fût de chêne pendant 12 mois", "•  Elevage : fût de chêne pendant plus de 12 mois", "•  Elevage : vin passerillé pendant 4 mois puis en fût durant 30 mois"}
    };
    private static final String[][] WINE_POINT_CULINAIRE = {
            {"•  Charcuterie, fromage frais", "•  Charcuteries, fromages frais", "•  Terrines, viandes grillées, volailles", "•  Viandes rouges, boeuf ficelle", "•  Saucisses grillées, côtelettes d’agneau", "•  Viandes grillées, agneau, fromages", "•  Cuisine asiatique, plats épicés, tajines, fromages"},
            {"•  Apéritif, salades estivales (avocat-crevette)", "•  Poissons, crustacés, gambas à la plancha", "•  Poissons en sauce, gratin de fruits de mer, volailles", "•  Apéritif, poissons grillés, salades estivales, crevettes", "•  Apéritif, Foie gras"},
            {"•  Charcuteries, barbecue, poissons", "•  Charcuteries, fromages frais", "•  Terrines, viandes grillées, volailles", "•  Viandes rouges, boeuf ficelle", "•  Apéritifs ou desserts (tartes aux fruits, poire Belle-Hélène)", "•  Desserts, tartes aux mirabelles, ou en digestif"}
    };
    private static final int[][] WINE_IMAGES = {
            {R.drawable.chateauvieux, R.drawable.probus_mont_dour_rouge, R.drawable.clos_de_la_roue, R.drawable.saint_didier, R.drawable.la_saromagnote, R.drawable.le_bois_de_lissieu, R.drawable.varax_rouge},
            {R.drawable.champ_de_lorge, R.drawable.probus_mont_dour, R.drawable.saromagnote_blanc, R.drawable.orchidee_vin, R.drawable.varax_blanc},
            {R.drawable.la_cabane, R.drawable.saromagnote_rose, R.drawable.la_bulle_lyonnaise, R.drawable.blanc_de_blanc, R.drawable.quetzal, R.drawable.leon_vin}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        //restore les données
        if(savedInstanceState != null){
            pageNumber = savedInstanceState.getInt(KEY_PAGE_NUMBER);
        }else{
            //récupérer la valeur de "pageNumber" à partir de l'intent
            pageNumber = 0;
            Intent intent = getIntent();
            if (intent.hasExtra(KEY_PAGE_NUMBER)) {
                String pageNumberString = intent.getStringExtra(WineActivity.KEY_PAGE_NUMBER);
                try {
                    pageNumber = Integer.parseInt(pageNumberString);
                } catch (NumberFormatException e) {
                    pageNumber = 0;
                }
            }
        }

        // initialisation
        page_title = findViewById(R.id.page_title);
        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);
        ImageButton button3 = findViewById(R.id.button3);
        ImageButton button4 = findViewById(R.id.button4);
        scroll_view = findViewById(R.id.scroll_view);

        createBottleListe(pageNumber);

        //Btn
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber = 0;
                createBottleListe(pageNumber);
                scroll_view.fullScroll(View.FOCUS_UP);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber = 1;
                createBottleListe(pageNumber);
                scroll_view.fullScroll(View.FOCUS_UP);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber = 2;
                createBottleListe(pageNumber);
                scroll_view.fullScroll(View.FOCUS_UP);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WineActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createBottleListe(int number) {
        page_title.setText(PAGE_TITLES[number]);
        LinearLayout cardContainer = findViewById(R.id.card_container);
        cardContainer.removeAllViews();
        for (int i = 0; i < WINE_TITLES[number].length; i++) {
            CardWine card = new CardWine(this);
            card.setImageResource(WINE_IMAGES[number][i]);
            card.setTitle(WINE_TITLES[number][i]);
            card.setPoint1(WINE_POINT1[number][i]);
            card.setPoint2(WINE_POINT2[number][i]);
            card.setPoint3(WINE_POINT3[number][i]);
            card.setPoint4(WINE_POINT4[number][i]);
            card.setPointCulinaire(WINE_POINT_CULINAIRE[number][i]);
            cardContainer.addView(card);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE_NUMBER, pageNumber);
    }
}
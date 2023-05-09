package com.example.wineapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class CardWine extends CardView {

    private ImageView cardImage;
    private TextView cardTitle;
    private TextView cardPoint1;
    private TextView cardPoint2;
    private TextView cardPoint3;
    private TextView cardPoint4;
    private TextView cardPointCulinaire;

    public CardWine(Context context) {
        super(context);
        init(context);
    }

    public CardWine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(attrs);
    }

    public CardWine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(attrs);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_wine, this, true);
        cardImage = findViewById(R.id.card_image);
        cardTitle = findViewById(R.id.card_title);
        cardPoint1 = findViewById(R.id.card_point1);
        cardPoint2 = findViewById(R.id.card_point2);
        cardPoint3 = findViewById(R.id.card_point3);
        cardPoint4 = findViewById(R.id.card_point4);
        cardPointCulinaire = findViewById(R.id.card_point_culinaire);
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CardWine);
        int imageResId = a.getResourceId(R.styleable.CardWine_card_image, 0);
        String title = a.getString(R.styleable.CardWine_card_title);
        String point1 = a.getString(R.styleable.CardWine_card_point1);
        String point2 = a.getString(R.styleable.CardWine_card_point2);
        String point3 = a.getString(R.styleable.CardWine_card_point3);
        String point4 = a.getString(R.styleable.CardWine_card_point4);
        String pointCulinaire = a.getString(R.styleable.CardWine_card_point_culinaire);
        a.recycle();

        if (imageResId != 0) {
            cardImage.setImageResource(imageResId);
        }
        if (title != null) {
            cardTitle.setText(title);
        }
        if (point1 != null) {
            cardPoint1.setText(point1);
        }
        if (point2 != null) {
            cardPoint2.setText(point2);
        }
        if (point3 != null) {
            cardPoint3.setText(point3);
        }
        if (point4 != null) {
            cardPoint4.setText(point4);
        }
        if (pointCulinaire != null) {
            cardPointCulinaire.setText(pointCulinaire);
        }
    }

    public void setImageResource(int resId) {
        cardImage.setImageResource(resId);
    }

    public void setTitle(String text) {
        cardTitle.setText(text);
    }

    public void setPoint1(String text) {
        cardPoint1.setText(text);
    }

    public void setPoint2(String text) {
        cardPoint2.setText(text);
    }

    public void setPoint3(String text) {
        cardPoint3.setText(text);
    }

    public void setPoint4(String text) {
        cardPoint4.setText(text);
    }

    public void setPointCulinaire(String text) {
        cardPointCulinaire.setText(text);
    }

}


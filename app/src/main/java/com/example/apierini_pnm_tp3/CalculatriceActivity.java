package com.example.apierini_pnm_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CalculatriceActivity extends AppCompatActivity {

    // Définition de certaines variables
    private static final String TAG = "CalculatriceActivity";
    private Map<Button, String> button = new HashMap<>();
    private Double n1;
    private Double n2;
    private String operateur;
    private String resultatTemp = "";
    private TextView resultat;
    private boolean ilyaN1 = false;
    private boolean ilyaOperateur = false;
    private boolean ilyaPoint = false;
    private boolean ilyaChiffe = false;
    private boolean dernierChiffreEstOperateur = false;
    private boolean n1EstResultat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatrice);

        // Création de liens avec les boutons de la calculatrice.
        button.put((Button)findViewById(R.id.buttonPlus), "+");
        button.put((Button)findViewById(R.id.buttonSoustraction), "-");
        button.put((Button)findViewById(R.id.buttonDivision), "/");
        button.put((Button)findViewById(R.id.buttonMultiplication), "*");
        button.put((Button)findViewById(R.id.buttonEffacer), "c");
        button.put((Button)findViewById(R.id.buttonEgal), "=");
        button.put((Button)findViewById(R.id.buttonPoint), ".");
        button.put((Button)findViewById(R.id.buttonChiffreZero), "0");
        button.put((Button)findViewById(R.id.buttonChiffre1), "1");
        button.put((Button)findViewById(R.id.buttonChiffre2), "2");
        button.put((Button)findViewById(R.id.buttonChiffre3), "3");
        button.put((Button)findViewById(R.id.buttonChiffre4), "4");
        button.put((Button)findViewById(R.id.buttonChiffre5), "5");
        button.put((Button)findViewById(R.id.buttonChiffre6), "6");
        button.put((Button)findViewById(R.id.buttonChiffre7), "7");
        button.put((Button)findViewById(R.id.buttonChiffre8), "8");
        button.put((Button)findViewById(R.id.buttonChiffre9), "9");
        resultat = findViewById(R.id.ecran);

        View.OnClickListener buttonClique = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logique pour effacer l'écran
                if (button.get((Button) v).equals("c")) {
                    resultat.setText("");
                    ilyaChiffe = false;
                    ilyaOperateur = false;
                    dernierChiffreEstOperateur = false;
                    ilyaPoint = false;
                    ilyaN1 = false;
                    resultatTemp = "";
                }
                // Logique d'utilisation du point
                else if (button.get((Button) v).equals(".")) {
                    if(!ilyaPoint) {
                        if (!ilyaChiffe) {
                            resultatTemp = "0.";
                            resultat.setText(resultat.getText().toString() + resultatTemp);
                        }
                        else {
                            resultatTemp += ".";
                            resultat.setText(resultat.getText().toString() + ".");
                        }
                        ilyaPoint = true;
                        dernierChiffreEstOperateur = false;
                    }
                }
                // Logique d'utilisation du égal
                else if (button.get((Button) v).equals("=")) {
                    if (ilyaN1 && ilyaOperateur) {
                        if (resultatTemp.length() > 0) {
                            if (resultatTemp.charAt(resultatTemp.length() - 1) != '.') {
                                n2 = Double.parseDouble(resultatTemp);
                            }
                            else {
                                n2 = Double.parseDouble(resultatTemp + "0");
                            }
                        }
                        else {
                            n2 = 0.0;
                        }
                        calculer();
                    }
                }
                // Logique d'utilisation des boutons numériques
                else if (Character.isDigit(button.get((Button) v).charAt(0))) {
                    if (!n1EstResultat) {
                        ilyaChiffe = true;
                        if (resultatTemp.equals("0") && !button.get((Button) v).equals("0")) {
                            resultatTemp = button.get((Button) v);
                            resultat.setText(resultat.getText().toString().substring(0, resultat.getText().toString().length() - 1) + button.get((Button) v));
                        }
                        else if (resultatTemp.equals("0") && button.get((Button) v).equals("0")) {

                        }
                        else {
                            resultatTemp += button.get((Button) v);
                            resultat.setText(resultat.getText().toString() + button.get((Button) v));
                        }
                        dernierChiffreEstOperateur = false;
                    }
                    else {
                        ilyaChiffe = true;
                        resultatTemp = button.get((Button) v);
                        dernierChiffreEstOperateur = false;
                        resultat.setText(button.get((Button) v));
                        n1EstResultat = false;
                        ilyaN1 = false;
                    }
                }
                // Logique d'utilisation des operateurs
                else {
                    n1EstResultat = false;
                    if (ilyaOperateur && dernierChiffreEstOperateur) {
                        operateur = button.get((Button) v);
                        String temp = resultat.getText().toString().substring(0, resultat.getText().toString().length() - 3);
                        resultat.setText((temp + " " + operateur + " "));
                    }
                    else if (ilyaOperateur) {
                        if (resultatTemp.length() > 0) {
                            if (resultatTemp.charAt(resultatTemp.length() - 1) != '.') {
                                n2 = Double.parseDouble(resultatTemp);
                            }
                            else {
                                n2 = Double.parseDouble(resultatTemp + "0");
                            }
                        }
                        else {
                            n2 = 0.0;
                        }
                        calculer();
                        operateur = button.get((Button) v);
                        n1EstResultat = false;
                        dernierChiffreEstOperateur = true;
                        ilyaOperateur = true;
                        resultat.setText(resultat.getText().toString() + " " + operateur + " ");
                    }
                    else {
                        operateur = button.get((Button) v);
                        if (!ilyaN1) {
                            if (resultatTemp.length() > 0) {
                                if (resultatTemp.charAt(resultatTemp.length() - 1) != '.') {
                                    n1 = Double.parseDouble(resultatTemp);
                                } else {
                                    n1 = Double.parseDouble(resultatTemp + "0");
                                    resultat.setText(resultat.getText().toString() + "0");
                                }
                            } else {
                                n1 = 0.0;
                            }
                        }
                        resultatTemp = "";
                        ilyaChiffe = false;
                        dernierChiffreEstOperateur = true;
                        ilyaPoint = false;
                        ilyaN1 = true;
                        resultat.setText(resultat.getText().toString() + " " + operateur + " ");
                    }
                    ilyaOperateur = true;
                }
            }
        };

        for (Button item : button.keySet()) {
            item.setOnClickListener(buttonClique);
        }
    }

    private void calculer() {
        Double resultat2 = 0.0;

        switch (operateur) {
            case "+":
                resultat2 = n1 + n2;
                break;
            case "-":
                resultat2 = n1 - n2;
                break;
            case "*":
                resultat2 = n1 * n2;
                break;
            case "/":
                if (n2 == 0) {
                    resultat2 = 0.0;
                }
                else {
                    resultat2 = n1 / n2;
                }
                break;
        }

        String resultatTemp2 = Double.toString(resultat2);

        if (resultatTemp2.substring(resultatTemp2.length() - 2, resultatTemp2.length()).equals(".0")) {
            resultatTemp2 = resultatTemp2.substring(0, resultatTemp2.length() - 2);
        }

        resultat.setText(resultatTemp2);
        ilyaN1 = true;
        n1 = resultat2;
        n1EstResultat = true;
        ilyaOperateur = false;
        ilyaPoint = false;
        ilyaChiffe = false;
        dernierChiffreEstOperateur = false;
        operateur = "";
        resultatTemp = "";
    }
}

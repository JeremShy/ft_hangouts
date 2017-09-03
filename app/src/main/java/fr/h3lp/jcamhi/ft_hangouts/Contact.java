package fr.h3lp.jcamhi.ft_hangouts;

import android.graphics.drawable.Drawable;

/**
 * Created by jcamhi on 9/3/17.
 */

public class Contact {
    private String _nom;
    private String _prenom;
    private String _numero;

    private Drawable _avatar;

    public Contact(String nom, String prenom, String numero, Drawable drawable) {
        this._nom = nom;
        this._prenom = prenom;
        this._numero = numero;
        this._avatar = drawable;
    }

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

    public void set_prenom(String _prenom) {
        this._prenom = _prenom;
    }

    public String get_numero() {
        return _numero;
    }

    public void set_numero(String _numero) {
        this._numero = _numero;
    }

    public Drawable get_avatar() {
        return _avatar;
    }

    public void set_avatar(Drawable _avatar) {
        this._avatar = _avatar;
    }

    public String get_nom_prenom() {
        return (_nom + " " + _prenom);
    }
}

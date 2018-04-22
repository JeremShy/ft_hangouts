package fr.h3lp.jcamhi.ft_hangouts;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jcamhi on 9/3/17.
 */

public class Contact {

    private long _id;
    private String _nom;
    private String _prenom;
    private String _numero;
    private Drawable _avatar;
    private String _domicile;

    private Date _anniversaire;

    public Contact(String nom, String prenom, String numero, Drawable drawable) {
        if (nom != null)
            this._nom = nom;
        else
            this._nom = "";
        if (prenom != null)
            this._prenom = prenom;
        else
            this._prenom = "";
        if (numero != null)
            this._numero = numero;
        else
            this._numero = "";
        this._domicile = "";
        this._anniversaire = null;
        this._avatar = drawable;
    }

    public Contact(long id, String nom, String prenom, String numero, Drawable drawable) {
        this._id = id;
        if (nom != null)
            this._nom = nom;
        else
            this._nom = "";
        if (prenom != null)
            this._prenom = prenom;
        else
            this._prenom = "";
        if (numero != null)
            this._numero = numero;
        else
            this._numero = "";
        this._domicile = "";
        this._anniversaire = null;
        this._avatar = drawable;
    }

    public Contact(long id, String nom, String prenom, String numero, Drawable drawable, String domicile, Date anniversaire) {
        this._id = id;
        if (nom != null)
            this._nom = nom;
        else
            this._nom = "";
        if (prenom != null)
            this._prenom = prenom;
        else
            this._prenom = "";
        if (numero != null)
            this._numero = numero;
        else
            this._numero = "";
        this._domicile = domicile;
        this._anniversaire = anniversaire;
        this._avatar = drawable;
    }

    public Contact(String nom, String prenom, String numero, Drawable drawable, String domicile, Date anniversaire) {
        if (nom != null)
            this._nom = nom;
        else
            this._nom = "";
        if (prenom != null)
            this._prenom = prenom;
        else
            this._prenom = "";
        if (numero != null)
            this._numero = numero;
        else
            this._numero = "";
        this._domicile = domicile;
        this._anniversaire = anniversaire;
        this._avatar = drawable;
    }

    public long get_id() {
        return _id;
    }

    public String get_nom() {
        return _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

    public String get_numero() {
        return _numero;
    }

    public Drawable get_avatar() {
        return _avatar;
    }

    public String get_nom_prenom() {
        return (_nom + " " + _prenom);
    }

    public String get_domicile() {
        return _domicile;
    }

    public Date get_anniversaire() {
        return _anniversaire;
    }

    public String get_anniv_as_str() {
        return Contact.get_date_as_str_stat(this._anniversaire);
    }

    public static String get_date_as_str_stat(Date d) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); //NON-NLS
        return (df.format(d));
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (rhs == this)
            return true;
        if (!(rhs instanceof  Contact))
            return false;
        Contact rhsContact = (Contact)rhs;
        if (rhsContact._id == this._id)
            return true;
        else
            return false;
    }
}

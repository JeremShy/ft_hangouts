package fr.h3lp.jcamhi.ft_hangouts;

class MySMS {
    private Contact _destinataire;
    private boolean    _fromMe;
    private String  _message;
    private int     _id;

    public MySMS(int id, boolean fromMe, String message, Contact destinataire) {
       _destinataire = destinataire;
       _fromMe = fromMe;
       _message = message;
       _id = id;
    }


    public Contact get_destinataire() {
        return _destinataire;
    }

    public boolean is_fromMe() {
        return _fromMe;
    }

    public String get_message() {
        return _message;
    }

    public int getId() {
        return _id;
    }
}

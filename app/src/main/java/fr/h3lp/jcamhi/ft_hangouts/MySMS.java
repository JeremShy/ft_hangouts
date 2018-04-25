package fr.h3lp.jcamhi.ft_hangouts;

class MySMS {
    private Contact _destinataire;
    private boolean    _fromMe;
    private String  _message;

    public MySMS(Contact destinataire, boolean fromMe, String message) {
       _destinataire = destinataire;
       _fromMe = fromMe;
       _message = message;
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
}

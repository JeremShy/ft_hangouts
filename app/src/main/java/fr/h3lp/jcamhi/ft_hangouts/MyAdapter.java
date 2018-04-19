package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by jcamhi on 9/3/17.
 */

class MyAdapter extends ArrayAdapter<Contact> {

    public static final String EXTRA_ID = "1";

    MyAdapter(Context context, List<Contact> contacts){
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int i, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_contact, parent, false);
        }
        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if (viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.nom_prenom = convertView.findViewById(R.id.nom_prenom);
            viewHolder.numero = convertView.findViewById(R.id.numero);
            viewHolder.avatar = convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        Contact contact = getItem(i);
        assert contact != null;
        viewHolder.nom_prenom.setText(contact.get_nom_prenom());
        viewHolder.numero.setText(contact.get_numero());
        viewHolder.avatar.setImageDrawable(contact.get_avatar());
        viewHolder.id = contact.get_id();
        return (convertView);
    }
}

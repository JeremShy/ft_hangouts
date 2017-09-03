package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jcamhi on 9/3/17.
 */

class MyAdapter extends ArrayAdapter<Contact> {

    public MyAdapter(Context context, List<Contact> contacts){
        super(context, 0, contacts);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_contact, parent, false);
        }
        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if (viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.nom_prenom = (TextView)convertView.findViewById(R.id.nom_prenom);
            viewHolder.numero = (TextView)convertView.findViewById(R.id.numero);
            viewHolder.avatar = (ImageView)convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        Contact contact = getItem(i);

        viewHolder.nom_prenom.setText(contact.get_nom_prenom());
        viewHolder.numero.setText(contact.get_numero());
        viewHolder.avatar.setImageDrawable(contact.get_avatar());

        return (convertView);
    }
}

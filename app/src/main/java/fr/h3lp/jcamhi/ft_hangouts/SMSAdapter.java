package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class SMSAdapter extends ArrayAdapter<MySMS> {
    SMSAdapter(Context context, List<MySMS> SMSs) {
        super(context, 0, SMSs);
    }

    @Override
    public View getView(int i, View convertView, @NonNull ViewGroup parent)
    {
        MySMS sms = getItem(i);
        if (sms == null)
            return null;

        if (convertView == null)
        {
            LayoutInflater inf = LayoutInflater.from(getContext());
            if (sms.is_fromMe())
            {
                convertView = inf.inflate(R.layout.sms_out_row, parent, false);
            }
            else
            {
                convertView = inf.inflate(R.layout.sms_inc_row, parent, false);
            }
        }
        SMSHolder holder = (SMSHolder)convertView.getTag();
        if (holder == null)
        {
            holder = new SMSHolder();
            holder.message = convertView.findViewById(R.id.sms_message_text);
            convertView.setTag(holder);
        }
        holder.message.setText(sms.get_message());
        return convertView;
    }
}

package io.github.httpdhruvaalam.checkyourbias.ListViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.httpdhruvaalam.checkyourbias.AlternativePerspectiveItem;
import io.github.httpdhruvaalam.checkyourbias.R;

/**
 * Created by Demo on 10/22/2016.
 */

public class AlternativePerspectiveAdapter extends ArrayAdapter<AlternativePerspectiveItem> {
    public AlternativePerspectiveAdapter(Context context, ArrayList<AlternativePerspectiveItem> items){
        super(context, 0, items);
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        AlternativePerspectiveItem item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alternative_perspective_row_layout, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.alternative_row_title);
        TextView url = (TextView) convertView.findViewById(R.id.alternative_row_url);

        title.setText(item.mArticleName);
        url.setText(item.mUrl);
        return convertView;
    }
}

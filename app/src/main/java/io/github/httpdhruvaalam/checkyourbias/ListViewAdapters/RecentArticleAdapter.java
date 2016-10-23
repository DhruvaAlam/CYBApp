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
import io.github.httpdhruvaalam.checkyourbias.RecentArticleItem;

/**
 * Created by Demo on 10/22/2016.
 */

public class RecentArticleAdapter extends ArrayAdapter<RecentArticleItem> {
    public RecentArticleAdapter(Context context, ArrayList<RecentArticleItem> items){
        super(context, 0 , items);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        RecentArticleItem item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recent_article_row_layout, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.recent_row_title);
        TextView rating = (TextView) convertView.findViewById(R.id.recent_row_bias_rating);

        title.setText(item.mArticleName);
        rating.setText(item.mRating);
        return convertView;
    }
}

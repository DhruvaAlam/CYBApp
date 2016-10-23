package io.github.httpdhruvaalam.checkyourbias.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.httpdhruvaalam.checkyourbias.AlternativePerspectiveItem;
import io.github.httpdhruvaalam.checkyourbias.ListViewAdapters.AlternativePerspectiveAdapter;
import io.github.httpdhruvaalam.checkyourbias.ListViewAdapters.RecentArticleAdapter;
import io.github.httpdhruvaalam.checkyourbias.R;
import io.github.httpdhruvaalam.checkyourbias.RecentArticleItem;

public class AuthorProfile extends AppCompatActivity {
    private RecentArticleAdapter mAdapter;
    String mAuthor = "Ben Smith";
    String mPublisher = "BuzzFeed";
    String mPosition = "BuzzFeed Editor-in-Chief";
    String mArticleNames[] = {
            "Former Republican Party Chairman Says He Won’t Vote For Trump",
            "Trump Pitches Himself As The American Modi",
            "Where Is Trump’s War On Media Headed?",
            "Leaked Polling Doesn’t Reveal Plot Against Obama — But Does Show Clinton’s Weaknesses"
    };
    String mArticleUrls[] = {
            "https://www.buzzfeed.com/bensmith/former-republican-party-chairman-says-he-wont-vote-for-trump?utm_term=.tgAOaoKXn#.bkjD4jgeW",
            "https://www.buzzfeed.com/bensmith/trump-modi?utm_term=.drzX97R5W#.pso1PXkvE",
            "https://www.buzzfeed.com/bensmith/view-from-baku?utm_term=.fsKg6vxY8#.ere0wPQ3X",
            "https://www.buzzfeed.com/bensmith/leaked-polling-doesnt-reveal-plot-against-obama-but-does-sho?utm_term=.jn2pLJj5N#.xlNq7LXw8"
    };
    String mArticleRatings[]= {
            "Biased For",
            "Slightly Biased For",
            "Biased For",
            "Biased Against"
    };

    TextView keyAuthorInfoView;
    ListView recentArticlesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_profile);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_author_name);
            setSupportActionBar(toolbar);

            keyAuthorInfoView = (TextView) findViewById(R.id.key_info_author);
            recentArticlesListView = (ListView) findViewById(R.id.recent_articles_listview);

            Intent intent = getIntent();
            //mAuthor = intent.getStringExtra("author");
            if (mAuthor != null) {
                if (getActionBar() != null) {
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getActionBar().setTitle(mAuthor);
                }
            }
            initVariables(intent.getStringExtra("json"));

            //Setup Text Output
            String keyInfoTextViewData = "Author: " + mAuthor + "\nPosition: " + mPosition +
                    "\nPublisher: " + mPublisher;

            keyAuthorInfoView.setText(keyInfoTextViewData);

            ArrayList<RecentArticleItem> items = new ArrayList<RecentArticleItem>();
            //add items to arraylist
            int numItems = mArticleNames.length;
            for (int i = 0; i < numItems; ++i) {
                RecentArticleItem item = new RecentArticleItem(mArticleNames[i], mArticleUrls[i], mArticleRatings[i]);
                items.add(item);
            }
            mAdapter = new RecentArticleAdapter(this, items);
            recentArticlesListView.setAdapter(mAdapter);

            recentArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = mAdapter.getItem(position).mUrl;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AuthorProfile.this, MainSearchActivity.class);
                    startActivity(intent);
                }
            });

    }

    private void initVariables(String jsonData){

    }
}

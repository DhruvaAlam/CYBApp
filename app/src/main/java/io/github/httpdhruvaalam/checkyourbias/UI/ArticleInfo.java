package io.github.httpdhruvaalam.checkyourbias.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.httpdhruvaalam.checkyourbias.AlternativePerspectiveItem;
import io.github.httpdhruvaalam.checkyourbias.ListViewAdapters.AlternativePerspectiveAdapter;
import io.github.httpdhruvaalam.checkyourbias.R;


public class ArticleInfo extends AppCompatActivity {
    AlternativePerspectiveAdapter mAdapter;
    String mAuthor = "Ben Smith";
    String mTitle = "How Hillary Clinton Took Charge In The First Presidential Debate";
    String mPublisher = "BuzzFeed";
    String mDate = "Sept. 26, 2016";
    String mUrl = "https://www.buzzfeed.com/bensmith/how-hillary-clinton-took-charge-in-the-first-presidential-de?utm_term=.sjoQqbYG9#.ug8n65vEX";
    String mBiasRating = "Strongly Biased For";
    String mSummary = "Here is the story of this debate so far: Hillary Clinton is a strong debater, and she came to play.\n" +
            "\n" +
            "Hillary Clinton set the pace and the tone.\n" +
            "\n" +
            "Trump leaned to his left to get an word in edgewise - and often took the bait as Clinton reached deeper and deeper into her opposition research file.\n" +
            "\n" +
            "Perhaps the signature line so far? When Clinton touted her website and Trump rejoined, \"And take a look at mine also.\"\n" +
            "\n" +
            "Clinton benefitted too from pre-debate coverage, which focused largely on whether Trump would show up wearing pants.\n" +
            "\n" +
            "Hillary Clinton has participated in 35 presidential debates - 26 in 2007 and 2008, nine in 2015 and 2016.\n" +
            "\n" +
            "If Clinton drops in the polls after this one - and if she manages not to fall off the podium over the next hour - she can at least take some solace in the reality that this is as good as it gets.";
    String mKeyWords = "Hillary Clinton\tstrong debater\ttook charge\tDonald Trump\tdrop in the polls";
    String mRelatedArticleTitles[] = {"Sorry, Hillary Clinton’s the one who’s unfit for the White House"};
    String mRelatedArticleUrls[] = {"http://nypost.com/2016/09/30/sorry-hillary-clintons-the-one-whos-unfit-for-the-white-house/"};


    //views for activity that need to have text change
    TextView keyInfoTextView;
    TextView biasRatingView;
    TextView summaryView;
    TextView wordCloudView;
    ListView differentArticlesListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set key views
        keyInfoTextView = (TextView) findViewById(R.id.key_info);
        biasRatingView = (TextView) findViewById(R.id.bias_rating);
        summaryView = (TextView) findViewById(R.id.summary_text);
        wordCloudView = (TextView) findViewById(R.id.word_cloud);
        differentArticlesListView = (ListView) findViewById(R.id.different_articles_listview);


        mUrl = mUrl.substring(0,50) + "...";

        Intent intent = getIntent();
        //mUrl = intent.getStringExtra("url");
        initVariables(intent.getStringExtra("json"));

        //Setup Text Output
        String keyInfoTextViewData = "Author: " + mAuthor +
                "\nPublisher: " + mPublisher + "\nDate: " + mDate + "\nUrl: " + mUrl;

        //set Title
        if (mTitle != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(mTitle);
            }
        }

        //set Key Info
        keyInfoTextView.setText(keyInfoTextViewData);

        //set bias rating
        biasRatingView.setText(mBiasRating);

        //set summary
        summaryView.setText(mSummary);

        //set key words
        wordCloudView.setText(mKeyWords);



        //different perspective items
        ArrayList<AlternativePerspectiveItem> items = new ArrayList<AlternativePerspectiveItem>();
        //add items to arraylist
        int numRelatedArticles = mRelatedArticleTitles.length;
        for (int i = 0; i < numRelatedArticles; ++i){
            AlternativePerspectiveItem item = new AlternativePerspectiveItem(mRelatedArticleTitles[i], mRelatedArticleUrls[i]);
            items.add(item);
        }

        mAdapter = new AlternativePerspectiveAdapter(this, items);
        differentArticlesListView.setAdapter(mAdapter);

        differentArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mAdapter.getItem(position).mUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleInfo.this, MainSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initVariables(String jsonData){
        try {
            if (jsonData == null){
                jsonData = "{ \"date\": \"Originally posted on Oct. 22, 2016, at 1:12 p.m.\", \"title\": \"An Emmett Till Sign In Mississippi Has Been Vandalized With Bullet Holes\", \"url\": \"https://www.buzzfeed.com/tamerragriffin/an-emmett-till-sign-in-mississippi-has-been-vandalized-with?utm_term=.wsp6rKR6A\", \"author\": [\"Tamerra Griffin\"], \"tags\": [\"Mississippi\", \"hadn\\u2019t\", \"picture\", \"bullet holes\", \"dead body\", \"Tallahatchie Sheriff\"], \"sentiment\": 0.003704309, \"publisher\": \"buzzfeed\"}";
            }
            JSONObject entire = new JSONObject(jsonData);

            JSONArray tagJSON = entire.getJSONArray("tags");
            for (int i = 0 ; i < tagJSON.length(); ++i){
                if (mKeyWords == null || mKeyWords == ""){
                    mKeyWords = tagJSON.getString(i) + "\t";
                } else if (i == tagJSON.length() - 1){
                    mKeyWords = mKeyWords + tagJSON.getString(i);
                } else {
                    mKeyWords = mKeyWords + tagJSON.getString(i) + "\t";
                }
            }

            mUrl = entire.getString("url");
            mDate = entire.getString("date");
            String publisher = entire.getString("publisher");
            mPublisher = publisher.substring(0,1).toUpperCase() + publisher.substring(1);
            mTitle = entire.getString("title");
            mAuthor = entire.getJSONArray("author").getString(0);
            mSummary = entire.getString("summary");
        } catch (JSONException ex){
            ex.printStackTrace();
        }

    }
}

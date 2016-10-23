package io.github.httpdhruvaalam.checkyourbias.UI;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.github.httpdhruvaalam.checkyourbias.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainSearchActivity extends AppCompatActivity {

    boolean mIsUrl;

    RadioGroup mTypeOfSearch;
    RadioButton mOptionSelected;

    Button mSearchButton;
    EditText mMainSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar  = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main_search);


        mSearchButton = (Button) findViewById(R.id.search_bar_main_button);
        mMainSearchBar = (EditText) findViewById(R.id.search_bar_editText_main);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchEntry = mMainSearchBar.getText().toString().trim();
                if (searchEntry == null || searchEntry.length() == 0){
                    Toast.makeText(MainSearchActivity.this, "Enter a valid Url or Author Name",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (isNetworkAvailable()) {
                    //settup OkHttp
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://localhost:5555/search?query=" + searchEntry)
                            .build();


                    if (!URLUtil.isValidUrl(searchEntry)) { // valid author
                        Toast.makeText(MainSearchActivity.this, "Author Entered",
                                Toast.LENGTH_LONG).show();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }
                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                }

                                MainSearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Intent intent = new Intent(MainSearchActivity.this, AuthorProfile.class);
                                            String jsonData = response.body().string();
                                            intent.putExtra("json", jsonData);
                                            startActivity(intent);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    } else { //valid url
                        Toast.makeText(MainSearchActivity.this, "Url entered",
                                Toast.LENGTH_LONG).show();
                        //String jsonData = response.body().string();
                        Intent intent = new Intent(MainSearchActivity.this, ArticleInfo.class);
                        String jsonData=  "{ \"date\": \"Originally posted on Oct. 22, 2016, at 1:12 p.m.\", \"title\": \"An Emmett Till Sign In Mississippi Has Been Vandalized With Bullet Holes\", \"url\": \"https://www.buzzfeed.com/tamerragriffin/an-emmett-till-sign-in-mississippi-has-been-vandalized-with?utm_term=.wsp6rKR6A\", \"author\": [\"Tamerra Griffin\"], \"tags\": [\"Mississippi\", \"hadn\\u2019t\", \"picture\", \"bullet holes\", \"dead body\", \"Tallahatchie Sheriff\"], \"sentiment\": 0.003704309, \"publisher\": \"buzzfeed\"}";
                        intent.putExtra("json", jsonData);
                        intent.putExtra("url", searchEntry);
                        startActivity(intent);
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                }

                                MainSearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Intent intent = new Intent(MainSearchActivity.this, ArticleInfo.class);
                                            //String jsonData = response.body().string();
                                            String jsonData=  "{ \"date\": \"Originally posted on Oct. 22, 2016, at 1:12 p.m.\", \"title\": \"An Emmett Till Sign In Mississippi Has Been Vandalized With Bullet Holes\", \"url\": \"https://www.buzzfeed.com/tamerragriffin/an-emmett-till-sign-in-mississippi-has-been-vandalized-with?utm_term=.wsp6rKR6A\", \"author\": [\"Tamerra Griffin\"], \"tags\": [\"Mississippi\", \"hadn\\u2019t\", \"picture\", \"bullet holes\", \"dead body\", \"Tallahatchie Sheriff\"], \"sentiment\": 0.003704309, \"publisher\": \"buzzfeed\"}";
                                            intent.putExtra("json", jsonData);
                                            intent.putExtra("url", searchEntry);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }
}

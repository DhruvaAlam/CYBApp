package io.github.httpdhruvaalam.checkyourbias;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Demo on 10/22/2016.
 */

public class ArticleData {
    public String mAuthor;
    public String mTitle;
    public String mPublisher;
    public String mDate;
    public String mSummary;
    public String mTopics[];
    public int mRatings[];

    public String mOtherArticleTitles[];
    public String mOtherArticleUrls[];

    public ArticleData(String jsonData){
        try {
            JSONObject data = new JSONObject(jsonData);
            mAuthor = data.getString("author");
            mTitle = data.getString("title");
            mPublisher = data.getString("publisher");
            mDate = data.getString("date");
            mSummary = data.getString("summary");
            JSONArray topics = data.getJSONArray("");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


}

package io.github.httpdhruvaalam.checkyourbias;

/**
 * Created by Demo on 10/22/2016.
 */

public class RecentArticleItem {
    public String mArticleName;
    public String mUrl;
    public String mRating;

    public RecentArticleItem(String articleName, String url, String rating){
        mArticleName = articleName;
        mUrl = url;
        mRating = rating;
    }
}

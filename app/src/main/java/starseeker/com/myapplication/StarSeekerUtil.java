package starseeker.com.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarSeekerUtil {
    /**
     * Generate request url for API.
     * Necessary information is included in parameter.
     *
     * @param parameter
     * @return request url for API.(Example: http://linedesign.cloudapp.net/hoshimiru/constellation?lat=35.862&lng=139.645&date=2013-10-31&hour=0&min=0&id=1)
     */
    public static String generateRequestUrl(Map parameter) {
        boolean isSearchMode = isSearchMode(parameter);
        StringBuilder requestUrl = new StringBuilder(StarSeekerConst.REQUEST_URL);

        double latitude = (Double) parameter.get(StarSeekerConst.LAT);
        double longitude = (Double) parameter.get(StarSeekerConst.LNG);
        String date = (String) parameter.get(StarSeekerConst.DATE);
        int hour = (Integer) parameter.get(StarSeekerConst.HOUR);
        int minute = (Integer) parameter.get(StarSeekerConst.MINUTE);

        requestUrl.append(StarSeekerConst.PARAM_SEPARATOR)
                .append(StarSeekerConst.LAT).append(StarSeekerConst.EQUAL).append(latitude).append(StarSeekerConst.AND)
                .append(StarSeekerConst.LNG).append(StarSeekerConst.EQUAL).append(longitude).append(StarSeekerConst.AND)
                .append(StarSeekerConst.DATE).append(StarSeekerConst.EQUAL).append(date).append(StarSeekerConst.AND)
                .append(StarSeekerConst.HOUR).append(StarSeekerConst.EQUAL).append(hour).append(StarSeekerConst.AND)
                .append(StarSeekerConst.MINUTE).append(StarSeekerConst.EQUAL).append(minute);
        //SearchExecutorのときは絞り込みのためにIDが必要だが、FindExecutorのときは不要.
        if(isSearchMode) {
            int id = (Integer) parameter.get(StarSeekerConst.ID);
            requestUrl.append(StarSeekerConst.AND).append(StarSeekerConst.ID).append(StarSeekerConst.EQUAL).append(id);
        }

        return requestUrl.toString();
    }

    private static boolean isSearchMode(Map parameter) {
        return StarSeekerConst.EXECUTE_TYPE_SEARCH.equals((String)parameter.get(StarSeekerConst.EXECUTE_TYPE)) ? true : false;
    }


    /**
     * Analyse from result(JSON style) and return star information.s
     *
     * @param result This is JSON style. This parameter must not be null.
     * @return starList List which includes StarInfo.
     */
    public static List<StarInfo> resultAnalyse(String result, boolean isSearchMode) throws JSONException {
        List<StarInfo> starList = new ArrayList<StarInfo>();
        try {
            JSONObject rootObj = new JSONObject(result);
            JSONArray listArray = rootObj.getJSONArray("result");
            int starCount = listArray.length();

            for(int i=0;i<starCount;i++) {
                JSONObject obj = listArray.getJSONObject(i);
                String jpName = (String) obj.get(StarSeekerConst.JP_NAME);
                String content = (String) obj.get(StarSeekerConst.CONTENT);
                String origin = (String) obj.get(StarSeekerConst.ORIGIN);
                String starImageUrl= (String) obj.get(StarSeekerConst.STAR_IMAGE_URL);
                StarInfo starInfo;
                if(!isSearchMode) {
                    starInfo = new StarInfo(jpName, content, origin, starImageUrl);
                } else {
                    starInfo = new StarInfo(jpName, content, origin, starImageUrl, createStarImage(starImageUrl));
                }
                starList.add(starInfo);
            }
        } catch(JSONException e) {
            e.printStackTrace();
            throw e;
        }

        return starList;
    }

    private static Bitmap createStarImage(String starImageUrl) {
        Bitmap starImg = null;

        try {
            URL url = new URL(starImageUrl);
            InputStream iStream = url.openStream();
            starImg = BitmapFactory.decodeStream(iStream);
            iStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return starImg;
    }
}

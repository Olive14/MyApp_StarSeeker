package starseeker.com.myapplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SearchExecutor implements IExecutor {

    public List<StarInfo> execute(Map parameter) throws SeekFailedException {
        int id = convertStarNameToId((String)parameter.get(StarSeekerConst.STAR_NAME));
        parameter.put(StarSeekerConst.ID, id);	//オートボクシング

        String requestUrl = StarSeekerUtil.generateRequestUrl(parameter);
        try {
            URL url = new URL(requestUrl);
            InputStream is = url.openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
            String result = sb.toString();
            return StarSeekerUtil.resultAnalyse(result, true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "URL format is invalid. [SearchExecutor #execute]");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "Encoding is not supported. [SearchExecutor #execute]");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "Unknown error has occurred. [SearchExecutor #execute]");
        }
    }

    /**
     * Convert from star name to its ID.
     * @param starName Star name.
     * @return id which is converted from star name.
     * @throws IllegalArgumentException If star name is not found.
     * @throws SeekFailedException If starName is null or search has failed.
     */
    private int convertStarNameToId(String starName) throws SeekFailedException {
        if(null==starName) {
            throw new SeekFailedException(new NullPointerException(), "starName is null. [SearchExecutor #convertStarNameToId]");
        }
        try {
            if(starName.endsWith("座")) {
                starName = starName.substring(0, starName.indexOf("座"));
            }
            StarNameAndIdHolder holder = StarNameAndIdHolder.getStarNameAndIdHolder(starName);
            return holder.getId();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "Search has failed. [SearchExecutor #convertStarNameToId]");
        }
    }
}


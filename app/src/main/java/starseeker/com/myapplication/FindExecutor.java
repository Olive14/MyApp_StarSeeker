package starseeker.com.myapplication;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class FindExecutor implements IExecutor {

    public List<StarInfo> execute(Map parameter) throws SeekFailedException {

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
            return StarSeekerUtil.resultAnalyse(sb.toString(), false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "URL format is invalid. [FindExecutor #execute]");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "Encoding is not supported. [FindExecutor #execute]");
        } catch(JSONException e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "JSON parse has failed. [FindExecutor #execute]");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeekFailedException(e, "Unknown error has occurred. [FindExecutor #execute]");
        }
    }
}

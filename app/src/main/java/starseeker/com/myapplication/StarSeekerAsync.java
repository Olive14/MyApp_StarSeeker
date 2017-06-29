package starseeker.com.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

public class StarSeekerAsync extends AsyncTask<Map, Void, List<StarInfo>> {
    Activity activity;

    public StarSeekerAsync(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<StarInfo> doInBackground(Map... parameter) {
        Map param = parameter[0];
        IExecutor executor = null;
        String executeType = (String) param.get(StarSeekerConst.EXECUTE_TYPE);

        if(StarSeekerConst.EXECUTE_TYPE_SEARCH.equals(executeType)) {
            param.put(StarSeekerConst.EXECUTE_TYPE, StarSeekerConst.EXECUTE_TYPE_SEARCH);
            executor = new SearchExecutor();
        } else if(StarSeekerConst.EXECITE_TYPE_FIND.equals(executeType)) {
            param.put(StarSeekerConst.EXECUTE_TYPE, StarSeekerConst.EXECITE_TYPE_FIND);
            executor = new FindExecutor();
        }

        try {
            return executor.execute(param);
        } catch (SeekFailedException e) {
            cancel(true);
            throw new CancellationException();
        }
    }
}

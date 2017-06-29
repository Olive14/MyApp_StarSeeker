package starseeker.com.myapplication;

import java.util.HashMap;
import java.util.Map;

public class LocationTimeInfoHolder {
    private static LocationTimeInfoHolder holder = new LocationTimeInfoHolder();
    private Map parameter = new HashMap();

    private LocationTimeInfoHolder() {}

    public static LocationTimeInfoHolder getInstance() {
        return holder;
    }

    public void setParameter(Map parameter) {
        if(null==parameter) {
            throw new NullPointerException();
        }
        this.parameter = parameter;
    }

    public Map getParameter() {
        return this.parameter;
    }
}

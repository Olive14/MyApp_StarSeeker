package starseeker.com.myapplication;

public class StarSeekerConst {
    /*
     * Keys for find or search parameter.
     * These are used in map instance.
     * */
    public static final String STAR_NAME = "starName";
    public static final String LAT = "lat";	//緯度
    public static final String LNG = "lng";	//経度
    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String MINUTE = "min";
    public static final String ID = "id";
    public static final String EXECUTE_TYPE = "executeType";    //search or find
    public static final String EXECUTE_TYPE_SEARCH = "search";
    public static final String EXECITE_TYPE_FIND = "find";

    /*
     * Request url for API
     * */
    public static final String REQUEST_URL = "http://linedesign.cloudapp.net/hoshimiru/constellation";
    public static final String PARAM_SEPARATOR = "?";
    public static final String AND = "&";
    public static final String EQUAL = "=";

    /*
    * Result of JSON keys
    * */
    public static final String JP_NAME = "jpName";
    public static final String CONTENT = "content";
    public static final String ORIGIN = "origin";
    public static final String STAR_IMAGE_URL = "starImage";
}

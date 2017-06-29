package starseeker.com.myapplication;

import java.util.List;
import java.util.Map;

public interface IExecutor {
    /**
     * Return star info about star which is searched or which is found from current location and time.
     * @param parameter
     * @return StarInfo
     * @throws SeekFailedException
     */
    public List<StarInfo> execute(Map parameter) throws SeekFailedException;
}

package starseeker.com.myapplication;

/**
 * This is the class for star seek failed.
 * This class has the cause of seek failed.
 */
public class SeekFailedException extends Exception {
    Exception caused;
    String message;

    /**
     * Constructor of SeekFailedException class.
     *
     * @param caused the cause of seek failed.
     * @param message the seek failed message.
     */
    public SeekFailedException(Exception caused, String message) {
        this.caused = caused;
        this.message = message;
    }

    public Exception getCaused() {
        return this.caused;
    }

    public  String message() {
        return this.message;
    }
}

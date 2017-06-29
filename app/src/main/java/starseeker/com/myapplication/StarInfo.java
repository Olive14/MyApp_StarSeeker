package starseeker.com.myapplication;

import android.graphics.Bitmap;

public class StarInfo {
    private String starName;
    private String content;
    private String origin;
    private String starImageUrl;
    private Bitmap starImage;

    /**
     * StarInfo constructor. Each parameters must not be null.
     *
     * @param starName Star name. This parameter must must not be null.
     * @param content Content of its star. This parameter must not be null.
     * @param origin Origin of its star. This parameter must not be null.
     * @param starImageUrl Star image of url. This parameter must not be null.
     * @throws NullPointerException If parameters of this constructor include null instance.
     */
    public StarInfo(String starName, String content, String origin, String starImageUrl) {
        if(null==starName || null==content || null==origin || null==starImageUrl) {
            throw new NullPointerException();
        }
        this.starName = starName;
        this.content = content;
        this.origin = origin;
        this.starImageUrl = starImageUrl;
    }

    /**
     * StarInfo constructor. Each parameters must not be null.
     *
     * @param starName Star name. This parameter must must not be null.
     * @param content Content of its star. This parameter must not be null.
     * @param origin Origin of its star. This parameter must not be null.
     * @param starImageUrl Star image of url. This parameter must not be null.
     * @param starImage Star image. This parameter must not be null.
     * @throws NullPointerException If parameters of this constructor include null instance.
     */
    public StarInfo(String starName, String content, String origin, String starImageUrl, Bitmap starImage) {
        if(null==starName || null==content || null==origin || null==starImageUrl || null==starImage) {
            throw new NullPointerException();
        }
        this.starName = starName;
        this.content = content;
        this.origin = origin;
        this.starImageUrl = starImageUrl;
        this.starImage = starImage;
    }

    public String getStarName() {
        return this.starName;
    }

    public String getContent() {
        return this.content;
    }

    public String getOrigin() {
        return this.origin;
    }

    public String getStarImageUrl() {
        return this.starImageUrl;
    }

    public Bitmap getStarImage() {
        return this.starImage;
    }
}

package demo.mark.com.xposedpluginsearch.models.weibo;

/**
 * Created by Berkeley on 5/24/16.
 */
public class PicInfo {

    private String cut_type;

    private String height;

    private String type;

    private String url;

    private String width;

    public String getCut_type() {
        return cut_type;
    }

    public void setCut_type(String cut_type) {
        this.cut_type = cut_type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "PicInfo{" +
                "cut_type='" + cut_type + '\'' +
                ", height='" + height + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", width='" + width + '\'' +
                '}';
    }
}

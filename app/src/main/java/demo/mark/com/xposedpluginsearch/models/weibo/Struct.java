package demo.mark.com.xposedpluginsearch.models.weibo;

/**
 * Created by Berkeley on 5/24/16.
 */
public class Struct {

    private boolean result;

    private String ori_url;

    private String url_title;

    private String url_type;

    private String short_url;

    private String page_id;

    private String log;

    private String url_type_pic;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getOri_url() {
        return ori_url;
    }

    public void setOri_url(String ori_url) {
        this.ori_url = ori_url;
    }

    public String getUrl_title() {
        return url_title;
    }

    public void setUrl_title(String url_title) {
        this.url_title = url_title;
    }

    public String getUrl_type() {
        return url_type;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getUrl_type_pic() {
        return url_type_pic;
    }

    public void setUrl_type_pic(String url_type_pic) {
        this.url_type_pic = url_type_pic;
    }

    @Override
    public String toString() {
        return "Struct{" +
                "result=" + result +
                ", ori_url='" + ori_url + '\'' +
                ", url_title='" + url_title + '\'' +
                ", url_type='" + url_type + '\'' +
                ", short_url='" + short_url + '\'' +
                ", page_id='" + page_id + '\'' +
                ", log='" + log + '\'' +
                ", url_type_pic='" + url_type_pic + '\'' +
                '}';
    }
}

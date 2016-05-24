package demo.mark.com.xposedpluginsearch.models.weibo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Berkeley on 5/24/16.
 */
public class PageInfo {

    private String object_type;

    private String page_url;

    private String type_icon;

    private String object_id;

    private String page_pic;

    private String page_id;

    private JSONArray buttons;

    private JSONObject actionlog;

    private String tips;

    private String page_desc;

    private String page_title;

    private int type;

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getType_icon() {
        return type_icon;
    }

    public void setType_icon(String type_icon) {
        this.type_icon = type_icon;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getPage_pic() {
        return page_pic;
    }

    public void setPage_pic(String page_pic) {
        this.page_pic = page_pic;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public JSONArray getButtons() {
        return buttons;
    }

    public void setButtons(JSONArray buttons) {
        this.buttons = buttons;
    }

    public JSONObject getActionlog() {
        return actionlog;
    }

    public void setActionlog(JSONObject actionlog) {
        this.actionlog = actionlog;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getPage_desc() {
        return page_desc;
    }

    public void setPage_desc(String page_desc) {
        this.page_desc = page_desc;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "object_type='" + object_type + '\'' +
                ", page_url='" + page_url + '\'' +
                ", type_icon='" + type_icon + '\'' +
                ", object_id='" + object_id + '\'' +
                ", page_pic='" + page_pic + '\'' +
                ", page_id='" + page_id + '\'' +
                ", buttons=" + buttons +
                ", actionlog=" + actionlog +
                ", tips='" + tips + '\'' +
                ", page_desc='" + page_desc + '\'' +
                ", page_title='" + page_title + '\'' +
                ", type=" + type +
                '}';
    }
}

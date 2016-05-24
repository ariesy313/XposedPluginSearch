package demo.mark.com.xposedpluginsearch.models.weibo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Berkeley on 5/24/16.
 */
public class Comment {

    private String text;

    private int floor_number;

    private int rootid;

    private int source_allowclick;

    private JSONObject status;

    private int source_type;

    private JSONArray url_objects;//相关账户

    private String scheme;

    private String idstr;

    private String source;

    private String created_at;

    private String mid;

    private User user;

    private User authorUser;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public int getRootid() {
        return rootid;
    }

    public void setRootid(int rootid) {
        this.rootid = rootid;
    }

    public int getSource_allowclick() {
        return source_allowclick;
    }

    public void setSource_allowclick(int source_allowclick) {
        this.source_allowclick = source_allowclick;
    }

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }

    public int getSource_type() {
        return source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public JSONArray getUrl_objects() {
        return url_objects;
    }

    public void setUrl_objects(JSONArray url_objects) {
        this.url_objects = url_objects;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(User authorUser) {
        this.authorUser = authorUser;
    }

    @Override
    public String
    toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", floor_number='" + floor_number + '\'' +
                ", url_objects=" + url_objects +
                ", idstr='" + idstr + '\'' +
                ", created_at='" + created_at + '\'' +
                ", user=" + user +
                '}';
    }
}

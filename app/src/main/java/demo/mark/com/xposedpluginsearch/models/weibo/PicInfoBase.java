package demo.mark.com.xposedpluginsearch.models.weibo;

/**
 * Created by Berkeley on 5/24/16.
 */
public class PicInfoBase {

    private PicInfo bmiddle;

    private PicInfo thumbnail;

    private PicInfo middleplus;

    private String pic_status;

    private String object_id;

    private PicInfo original;

    private String type;

    private String photo_tag;

    private PicInfo large;

    private String pic_id;

    private PicInfo largest;

    public PicInfo getBmiddle() {
        return bmiddle;
    }

    public void setBmiddle(PicInfo bmiddle) {
        this.bmiddle = bmiddle;
    }

    public PicInfo getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(PicInfo thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PicInfo getMiddleplus() {
        return middleplus;
    }

    public void setMiddleplus(PicInfo middleplus) {
        this.middleplus = middleplus;
    }

    public String getPic_status() {
        return pic_status;
    }

    public void setPic_status(String pic_status) {
        this.pic_status = pic_status;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public PicInfo getOriginal() {
        return original;
    }

    public void setOriginal(PicInfo original) {
        this.original = original;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto_tag() {
        return photo_tag;
    }

    public void setPhoto_tag(String photo_tag) {
        this.photo_tag = photo_tag;
    }

    public PicInfo getLarge() {
        return large;
    }

    public void setLarge(PicInfo large) {
        this.large = large;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    public PicInfo getLargest() {
        return largest;
    }

    public void setLargest(PicInfo largest) {
        this.largest = largest;
    }

    @Override
    public String toString() {
        return "PicInfoBase{" +
                "bmiddle=" + bmiddle +
                ", thumbnail=" + thumbnail +
                ", middleplus=" + middleplus +
                ", pic_status='" + pic_status + '\'' +
                ", object_id='" + object_id + '\'' +
                ", original=" + original +
                ", type='" + type + '\'' +
                ", photo_tag='" + photo_tag + '\'' +
                ", large=" + large +
                ", pic_id='" + pic_id + '\'' +
                ", largest=" + largest +
                '}';
    }
}

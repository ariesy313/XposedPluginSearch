package demo.mark.com.xposedpluginsearch.models.wandoujia;

import java.util.Comparator;

/**
 * Created by wudi on 16/4/22.
 */
public class ModelBean  implements Comparator {

    String icon;

    String appName;
    /**
     * 文章标题
     */
    String articlTitle;
    /**
     * 文章简介 可能为空
     */
    String summary;
    /**
     * 文章作者 可能为空
     */
    String author;

    /**
     * 发布时间
     */
    long published_date;

    /**
     * 文章详情 html
     */
    String detailHtml;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getArticlTitle() {
        return articlTitle;
    }

    public void setArticlTitle(String articlTitle) {
        this.articlTitle = articlTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPublished_date() {
        return published_date;
    }

    public void setPublished_date(long published_date) {
        this.published_date = published_date;
    }

    public String getDetailHtml() {
        return detailHtml;
    }

    public void setDetailHtml(String detailHtml) {
        this.detailHtml = detailHtml;
    }

    public int compare(Object arg0, Object arg1) {
        ModelBean bean0 = (ModelBean) arg0;
        ModelBean bean1 = (ModelBean) arg1;
        return bean0.getArticlTitle().compareTo(bean1.getArticlTitle());
    }

}

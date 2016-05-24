package demo.mark.com.xposedpluginsearch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import demo.mark.com.xposedpluginsearch.models.weibo.Comment;
import demo.mark.com.xposedpluginsearch.models.weibo.PageInfo;
import demo.mark.com.xposedpluginsearch.models.weibo.PicInfo;
import demo.mark.com.xposedpluginsearch.models.weibo.PicInfoBase;
import demo.mark.com.xposedpluginsearch.models.weibo.Struct;
import demo.mark.com.xposedpluginsearch.models.weibo.User;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class WeiboSearch implements IXposedHookLoadPackage {

    Context appContext = null;

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.sina.weibo"))
            return;

        XposedBridge.log("handleLoadPackage ===== " + lpparam.packageName);
        Config.enabled = true;

        findAndHookMethod("com.sina.weibo.MainTabActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (appContext != null) {
                    return;
                }
                appContext = ((Activity) param.thisObject).getApplicationContext();
                hookMethods(lpparam);
            }
        });


    }


    private void hookMethods(final LoadPackageParam lpparam) {

//        final Class SearchMatchedKeyClass = XposedHelpers.findClass("com.sina.weibo.models.SearchMatchedKey", lpparam.classLoader);
        final Class pagecardinfo = XposedHelpers.findClass("com.sina.weibo.card.model.PageCardInfo", lpparam.classLoader);
        final Class cardSearch = XposedHelpers.findClass("com.sina.weibo.card.model.CardSearch", lpparam.classLoader);
        final Class cardSpecialTitle = XposedHelpers.findClass("com.sina.weibo.card.model.CardSpecialTitle", lpparam.classLoader);
        final Class cardStatus = XposedHelpers.findClass("com.sina.weibo.models.Status", lpparam.classLoader);
        final Class mBlogExtraButtonInfo = XposedHelpers.findClass("com.sina.weibo.models.MBlogExtraButtonInfo", lpparam.classLoader);
        final Class forwardSumClass = XposedHelpers.findClass("com.sina.weibo.models.ForwardSummary", lpparam.classLoader);
        final Class mBlogCardInfo = XposedHelpers.findClass("com.sina.weibo.card.model.MblogCardInfo", lpparam.classLoader);
        final Class commentSumClass = XposedHelpers.findClass("com.sina.weibo.models.CommentSummary", lpparam.classLoader);
        final Class continueTagClass = XposedHelpers.findClass("com.sina.weibo.models.ContinueTag", lpparam.classLoader);
        final Class statusCommentClass = XposedHelpers.findClass("com.sina.weibo.models.StatusComment", lpparam.classLoader);
        final Class blogTitleClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitle", lpparam.classLoader);
        final Class blogTitleInfoClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitleInfo", lpparam.classLoader);
        final Class promotionClass = XposedHelpers.findClass("com.sina.weibo.models.Promotion", lpparam.classLoader);
        final Class blogTopicClass = XposedHelpers.findClass("com.sina.weibo.models.MblogTopic", lpparam.classLoader);
        final Class userInfoClass = XposedHelpers.findClass("com.sina.weibo.models.JsonUserInfo", lpparam.classLoader);
        final Class picinfoClass = XposedHelpers.findClass("com.sina.weibo.models.PicInfo", lpparam.classLoader);
        final Class picinfoSizeClass = XposedHelpers.findClass("com.sina.weibo.models.PicInfoSize", lpparam.classLoader);

        /**
         * 找人
         *
         * @param className 待Hook的Class
         * @param classLoader classLoader
         * @param methodName 待Hook的Method
         * @param parameterTypesAndCallback hook回调
         * @return
         */
        findAndHookMethod("com.sina.weibo.card.b", lpparam.classLoader, "a",
                JSONObject.class, int.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        JSONObject jsonObject = (JSONObject) param.args[0];

                        List<Object> cardList = new ArrayList<Object>();
//                        XposedBridge.log("com.sina.weibo.card.b.a json ==== " + jsonObject.toString());

                        int index = (int) param.args[1];
//                        XposedBridge.log("com.sina.weibo.card.b.a index ==== " + String.valueOf(index));
//                        XposedBridge.log("---------------------");

                        if (index == 9) {
                            Class cardStatus = XposedHelpers.findClass("com.sina.weibo.models.Status", lpparam.classLoader);
                            JSONObject localJSONObject = jsonObject.optJSONObject("mblog");
//                            XposedBridge.log("com.sina.weibo.card.b.a mblog ==== " + localJSONObject.toString());
//                            XposedBridge.log("=========================================");

                            //topic_struct
                            JSONArray topic_struct = localJSONObject.optJSONArray("topic_struct");
                            if(topic_struct != null){
                                for(int i = 0;i < topic_struct.length();i++){
                                    JSONObject topicObj = (JSONObject) topic_struct.get(i);

//                                    XposedBridge.log("topic_struct微博标题url ==== " + topicObj.optString("topic_url"));
//                                    XposedBridge.log("topic_struct微博标题内容 ==== " + topicObj.optString("topic_title"));

                                }
                            }

                            //mblog_show_union_info
                            String mblog_show_union_info = localJSONObject.optString("mblog_show_union_info");

                            //微博内容
                            String text = localJSONObject.optString("text");

//                            XposedBridge.log("mblog 微博内容 ==== " + text.toString());
                            cardList.add(text);

                            //mblogid
                            String mblogid = localJSONObject.optString("mblogid");

                            //isLongText 是否是长文本
                            boolean isLongText = localJSONObject.optBoolean("isLongText");

                            //mlevel
                            String mlevel = localJSONObject.optString("mlevel");

                            //source_type
                            String source_type = localJSONObject.optString("source_type");

                            //图片列表信息
                            List<PicInfoBase> picList = new ArrayList<PicInfoBase>();
                            List<String> urlList = new ArrayList<String>();

                            JSONArray picidsJSONArray = localJSONObject.optJSONArray("pic_ids");
                            JSONObject picinfoObject = localJSONObject.optJSONObject("pic_infos");
                            if ((picidsJSONArray != null) && (picinfoObject != null))
                            {
                                for(int i = 0;i < picidsJSONArray.length();i ++){

                                    String id = (String) picidsJSONArray.get(i);

//                                    XposedBridge.log("微博图片 pic ID ==== " + id);
                                    JSONObject picJsonObject = picinfoObject.getJSONObject(id);

                                    //单个图片信息
                                    PicInfoBase base = new PicInfoBase();

                                    //bmiddle
                                    JSONObject bmiddle = picJsonObject.getJSONObject("bmiddle");

                                    PicInfo bmiddleObj = new PicInfo();
                                    bmiddleObj.setCut_type(bmiddle.getString("cut_type"));
                                    bmiddleObj.setHeight(bmiddle.getString("height"));
                                    bmiddleObj.setType(bmiddle.getString("type"));
                                    bmiddleObj.setUrl(bmiddle.getString("url"));
                                    bmiddleObj.setWidth(bmiddle.getString("width"));

                                    base.setBmiddle(bmiddleObj);
                                    urlList.add(bmiddle.getString("url"));

                                    //thumbnail
                                    JSONObject thumbnail = picJsonObject.getJSONObject("thumbnail");

                                    PicInfo thumbnailObj = new PicInfo();
                                    thumbnailObj.setCut_type(thumbnail.getString("cut_type"));
                                    thumbnailObj.setHeight(thumbnail.getString("height"));
                                    thumbnailObj.setType(thumbnail.getString("type"));
                                    thumbnailObj.setUrl(thumbnail.getString("url"));
                                    thumbnailObj.setWidth(thumbnail.getString("width"));

                                    base.setThumbnail(thumbnailObj);

                                    //middleplus
                                    JSONObject middleplus = picJsonObject.getJSONObject("middleplus");

                                    PicInfo middleplusObj = new PicInfo();
                                    middleplusObj.setCut_type(middleplus.getString("cut_type"));
                                    middleplusObj.setHeight(middleplus.getString("height"));
                                    middleplusObj.setType(middleplus.getString("type"));
                                    middleplusObj.setUrl(middleplus.getString("url"));
                                    middleplusObj.setWidth(middleplus.getString("width"));

                                    base.setMiddleplus(middleplusObj);

                                    //pic_status
                                    int pic_status = picJsonObject.getInt("pic_status");
                                    base.setPic_status(pic_status + "");

                                    //object_id
                                    String object_id = picJsonObject.optString("object_id");
                                    base.setObject_id(object_id);

                                    //original
                                    JSONObject original = picJsonObject.getJSONObject("original");

                                    //original
                                    PicInfo originalObj = new PicInfo();
                                    originalObj.setCut_type(original.getString("cut_type"));
                                    originalObj.setHeight(original.getString("height"));
                                    originalObj.setType(original.getString("type"));
                                    originalObj.setUrl(original.getString("url"));
                                    originalObj.setWidth(original.getString("width"));

                                    base.setMiddleplus(originalObj);

                                    //type
                                    String type = picJsonObject.optString("type");
                                    base.setObject_id(type);

                                    //photo_tag
                                    int photo_tag = picJsonObject.getInt("photo_tag");
                                    base.setPic_status(photo_tag + "");

                                    //large
                                    JSONObject large = picJsonObject.getJSONObject("large");

                                    PicInfo largeObj = new PicInfo();
                                    largeObj.setCut_type(large.getString("cut_type"));
                                    largeObj.setHeight(large.getString("height"));
                                    largeObj.setType(large.getString("type"));
                                    largeObj.setUrl(large.getString("url"));
                                    largeObj.setWidth(large.getString("width"));

                                    base.setMiddleplus(largeObj);

                                    //largest
                                    JSONObject largest = picJsonObject.getJSONObject("largest");

                                    PicInfo largestObj = new PicInfo();
                                    largeObj.setCut_type(largest.getString("cut_type"));
                                    largeObj.setHeight(largest.getString("height"));
                                    largeObj.setType(largest.getString("type"));
                                    largeObj.setUrl(largest.getString("url"));
                                    largeObj.setWidth(largest.getString("width"));

                                    base.setMiddleplus(largeObj);

                                    //type
                                    String pic_id = picJsonObject.optString("pic_id");
                                    base.setObject_id(pic_id);

                                    picList.add(base);
                                }
//                                XposedBridge.log("图片列表 picList ==== " + picList.toString());
//                                XposedBridge.log("图片列表 picList ==== " + urlList.toString());
                                cardList.add(urlList);
                            }

                            //url_struct
                            List<Struct> structList = new ArrayList<Struct>();

                            JSONArray url_struct = localJSONObject.optJSONArray("url_struct");
                            if(url_struct != null) {
                                for (int i = 0; i < url_struct.length(); i++) {
                                    JSONObject struct = (JSONObject) url_struct.get(i);
                                    Struct struct1 = new Struct();

                                    struct1.setUrl_type(struct.optInt("url_type") + "");
                                    struct1.setLog(struct.optString("log"));
                                    struct1.setOri_url(struct.optString("ori_url"));
                                    struct1.setPage_id(struct.optString("page_id"));
                                    struct1.setResult(struct.optBoolean("result"));
                                    struct1.setShort_url(struct.optString("short_url"));
                                    struct1.setShort_url(struct.optString("short_url"));
                                    struct1.setUrl_title(struct.optString("url_title"));
                                    struct1.setUrl_type_pic(struct.optString("url_type_pic"));

                                    structList.add(struct1);
                                }


//                                XposedBridge.log("structList ==== " + structList.toString());
                            }

                            //scheme
                            String scheme = localJSONObject.optString("scheme");
//                            XposedBridge.log("scheme ==== " + scheme.toString());

                            //idstr
                            String idstr = localJSONObject.optString("idstr");

                            //in_reply_to_status_id
                            String in_reply_to_status_id = localJSONObject.optString("in_reply_to_status_id");

                            //attitudes_status
                            int attitudes_status = localJSONObject.optInt("attitudes_status");

                            //mblogtypename
                            String mblogtypename = localJSONObject.optString("mblogtypename");

                            //geo
                            String geo = localJSONObject.optString("geo");

                            //status
                            int status = localJSONObject.optInt("status");

                            //liked
                            boolean liked = localJSONObject.optBoolean("liked");

                            //mblogidObj
                            JSONObject social_time_info = localJSONObject.optJSONObject("social_time_info");
                            if(social_time_info != null) {
//                                XposedBridge.log("social_time_info ==== " + social_time_info.toString());
                            }

                            //favorited
                            Boolean favorited = localJSONObject.optBoolean("favorited");

                            //page_info
                            JSONObject page_info = localJSONObject.optJSONObject("page_info");
                            if(page_info != null) {
                                PageInfo pageInfo = new PageInfo();

                                //object_type
                                pageInfo.setObject_type(page_info.optString("object_type"));
                                pageInfo.setPage_url(page_info.optString("page_url"));
                                pageInfo.setType_icon(page_info.optString("type_icon"));
                                pageInfo.setObject_id(page_info.optString("object_id"));
                                pageInfo.setPage_pic(page_info.optString("page_pic"));
                                pageInfo.setPage_id(page_info.optString("page_id"));
                                pageInfo.setButtons(page_info.optJSONArray("buttons"));
                                pageInfo.setActionlog(page_info.optJSONObject("actionlog"));
                                pageInfo.setTips(page_info.optString("tips"));
                                pageInfo.setPage_desc(page_info.optString("page_desc"));
                                pageInfo.setType(page_info.optInt("page_desc"));
                                pageInfo.setPage_title(page_info.optString("page_desc"));

//                                XposedBridge.log("pageInfo ==== " + pageInfo.toString());
                            }

                            //in_reply_to_user_id
                            String in_reply_to_user_id = localJSONObject.optString("in_reply_to_user_id");

                            //textLenth
                            String textLength = localJSONObject.optString("textLength");

                            //attitudes_count 点赞的数量
                            int attitudes_count = localJSONObject.optInt("attitudes_count");
//                            XposedBridge.log("点赞的数量 ==== " + attitudes_count);
                            cardList.add(attitudes_count);

                            //cardid
                            String cardid = localJSONObject.optString("cardid");

                            //thumbnail_pic
                            String thumbnail_pic = localJSONObject.optString("thumbnail_pic");
//                            XposedBridge.log("图片url thumbnail_pic==== " + thumbnail_pic);

                            //id
                            int id = localJSONObject.optInt("id");

                            //comment_summary 评论列表
                            JSONObject comment_summary = localJSONObject.optJSONObject("comment_summary");
                            if(comment_summary != null) {
                                JSONArray comment_list = comment_summary.optJSONArray("comment_list");
                                if(comment_list != null) {
                                    List<Comment> commentList = new ArrayList<Comment>();
                                    for (int i = 0; i < comment_list.length(); i++) {
                                        JSONObject commentJson = (JSONObject) comment_list.get(i);
                                        Comment comment = new Comment();

                                        comment.setText(commentJson.optString("text"));
                                        comment.setFloor_number(commentJson.optInt("floor_number"));
                                        comment.setRootid(commentJson.optInt("rootid"));
                                        comment.setSource_allowclick(commentJson.optInt("source_allowclick"));

                                        JSONObject statusObj = commentJson.optJSONObject("status");
                                        comment.setStatus(statusObj);

                                        JSONObject authorUserObj = commentJson.optJSONObject("user");
                                        User authorUser = new User();
                                        authorUser.setBlock_app(authorUserObj.optInt("block_app"));
                                        authorUser.setRemark(authorUserObj.optString("block_app"));
                                        authorUser.setLocation(authorUserObj.optString("location"));
                                        authorUser.setVerified_reason(authorUserObj.optString("verified_reason"));
                                        authorUser.setType(authorUserObj.optInt("type"));
                                        authorUser.setStatuses_count(authorUserObj.optInt("statuses_count"));
                                        authorUser.setCity(authorUserObj.optString("city"));
                                        authorUser.setLevel(authorUserObj.optInt("level"));
                                        authorUser.setFavourites_count(authorUserObj.optInt("favourites_count"));
                                        authorUser.setIdstr(authorUserObj.optString("idstr"));
                                        authorUser.setVerified(authorUserObj.optBoolean("verified"));
                                        authorUser.setDescription(authorUserObj.optString("description"));
                                        authorUser.setProvince(authorUserObj.optString("province"));
                                        authorUser.setGender(authorUserObj.optString("gender"));
                                        authorUser.setWeihao(authorUserObj.optString("weihao"));
                                        authorUser.setUlevel(authorUserObj.optInt("cover_image"));
                                        authorUser.setMbrank(authorUserObj.optInt("mbrank"));
                                        authorUser.setUrl(authorUserObj.optString("url"));
                                        authorUser.setCover_image_phone(authorUserObj.optString("cover_image_phone"));
                                        authorUser.setAbility_tags(authorUserObj.optString("ability_tags"));
                                        authorUser.setVerified_contact_email(authorUserObj.optString("verified_contact_email"));
                                        authorUser.setVerified_contact_name(authorUserObj.optString("verified_contact_name"));
                                        authorUser.setFriends_count(authorUserObj.optInt("friends_count"));
                                        authorUser.setFollow_me(authorUserObj.optBoolean("follow_me"));
                                        authorUser.setProfile_image_url(authorUserObj.optString("profile_image_url"));
                                        authorUser.setPtype(authorUserObj.optInt("ptype"));
                                        authorUser.setBadge_top(authorUserObj.optString("badge_top"));
                                        authorUser.setVerified_type(authorUserObj.optInt("verified_type"));
                                        authorUser.setLang(authorUserObj.optString("lang"));
                                        authorUser.setVerified_source(authorUserObj.optString("verified_source"));
                                        authorUser.setCredit_score(authorUserObj.optString("credit_score"));
                                        authorUser.setVerified_trade(authorUserObj.optString("verified_trade"));
                                        authorUser.setFollowing(authorUserObj.optBoolean("following"));
                                        authorUser.setName(authorUserObj.optString("name"));
                                        authorUser.setDomain(authorUserObj.optString("domain"));
                                        authorUser.setCreated_at(authorUserObj.optString("created_at"));
                                        authorUser.setUser_ability(authorUserObj.optInt("user_ability"));
                                        authorUser.setFollowers_count(authorUserObj.optInt("followers_count"));
                                        authorUser.setOnline_status(authorUserObj.optInt("online_status"));
                                        authorUser.setHas_ability_tag(authorUserObj.optInt("has_ability_tag"));
                                        authorUser.setProfile_url(authorUserObj.optString("profile_url"));
                                        authorUser.setBi_followers_count(authorUserObj.optString("bi_followers_count"));
                                        authorUser.setGeo_enabled(authorUserObj.optBoolean("geo_enabled"));
                                        authorUser.setExtend(authorUserObj.optJSONObject("extend"));
                                        authorUser.setStar(authorUserObj.optInt("star"));
                                        authorUser.setUrank(authorUserObj.optInt("urank"));
                                        authorUser.setAvatar_hd(authorUserObj.optString("avatar_hd"));
                                        authorUser.setAllow_all_comment(authorUserObj.optBoolean("allow_all_comment"));
                                        authorUser.setAllow_all_act_msg(authorUserObj.optBoolean("allow_all_act_msg"));
                                        authorUser.setAvatar_large(authorUserObj.optString("avatar_large"));
                                        authorUser.setPagefriends_count(authorUserObj.optInt("pagefriends_count"));
                                        authorUser.setVerified_reason_url(authorUserObj.optString("verified_reason_url"));
                                        authorUser.setMbtype(authorUserObj.optInt("mbtype"));
                                        authorUser.setBadge(authorUserObj.optJSONObject("badge"));
                                        authorUser.setScreen_name(authorUserObj.optString("screen_name"));
                                        cardList.add(authorUserObj.optString("screen_name"));
                                        authorUser.setBlock_word(authorUserObj.optInt("block_word"));

                                        comment.setAuthorUser(authorUser);
//                                        XposedBridge.log("评论用户信息==== " + authorUser.toString());


                                        comment.setSource_type(commentJson.optInt("source_type"));
                                        comment.setUrl_objects(commentJson.optJSONArray("url_objects"));
                                        comment.setScheme(commentJson.optString("scheme"));
                                        comment.setIdstr(commentJson.optString("idstr"));
                                        comment.setSource(commentJson.optString("source"));
                                        comment.setCreated_at(commentJson.optString("created_at"));
                                        comment.setMid(commentJson.optString("mid"));

                                        commentList.add(comment);

                                    }

//                                    XposedBridge.log("评论列表信息==== " + commentList.toString());
                                    cardList.add(commentList);
                                }
                            }

                            //微博作者信息
                            JSONObject userObj = localJSONObject.optJSONObject("user");
                            if(userObj != null) {
                                User user = new User();

                                user.setBlock_app(userObj.optInt("block_app"));
                                user.setRemark(userObj.optString("block_app"));
                                user.setLocation(userObj.optString("location"));
                                user.setVerified_reason(userObj.optString("verified_reason"));
                                user.setType(userObj.optInt("type"));
                                user.setStatuses_count(userObj.optInt("statuses_count"));
                                user.setCity(userObj.optString("city"));
                                user.setLevel(userObj.optInt("level"));
                                user.setFavourites_count(userObj.optInt("favourites_count"));
                                user.setIdstr(userObj.optString("idstr"));
                                user.setVerified(userObj.optBoolean("verified"));
                                user.setDescription(userObj.optString("description"));
                                user.setProvince(userObj.optString("province"));
                                user.setGender(userObj.optString("gender"));
                                user.setWeihao(userObj.optString("weihao"));
                                user.setUlevel(userObj.optInt("cover_image"));
                                user.setMbrank(userObj.optInt("mbrank"));
                                user.setUrl(userObj.optString("url"));
                                user.setCover_image_phone(userObj.optString("cover_image_phone"));
                                user.setAbility_tags(userObj.optString("ability_tags"));
                                user.setVerified_contact_email(userObj.optString("verified_contact_email"));
                                user.setVerified_contact_name(userObj.optString("verified_contact_name"));
                                user.setFriends_count(userObj.optInt("friends_count"));
                                user.setFollow_me(userObj.optBoolean("follow_me"));
                                user.setProfile_image_url(userObj.optString("profile_image_url"));
                                user.setPtype(userObj.optInt("ptype"));
                                user.setBadge_top(userObj.optString("badge_top"));
                                user.setVerified_type(userObj.optInt("verified_type"));
                                user.setLang(userObj.optString("lang"));
                                user.setVerified_source(userObj.optString("verified_source"));
                                user.setCredit_score(userObj.optString("credit_score"));
                                user.setVerified_trade(userObj.optString("verified_trade"));
                                user.setFollowing(userObj.optBoolean("following"));
                                user.setName(userObj.optString("name"));
                                user.setDomain(userObj.optString("domain"));
                                user.setCreated_at(userObj.optString("created_at"));
                                user.setUser_ability(userObj.optInt("user_ability"));
                                user.setFollowers_count(userObj.optInt("followers_count"));
                                user.setOnline_status(userObj.optInt("online_status"));
                                user.setHas_ability_tag(userObj.optInt("has_ability_tag"));
                                user.setProfile_url(userObj.optString("profile_url"));
                                user.setBi_followers_count(userObj.optString("bi_followers_count"));
                                user.setGeo_enabled(userObj.optBoolean("geo_enabled"));
                                user.setExtend(userObj.optJSONObject("extend"));
                                user.setStar(userObj.optInt("star"));
                                user.setUrank(userObj.optInt("urank"));
                                user.setAvatar_hd(userObj.optString("avatar_hd"));
                                user.setAllow_all_comment(userObj.optBoolean("allow_all_comment"));
                                user.setAllow_all_act_msg(userObj.optBoolean("allow_all_act_msg"));
                                user.setAvatar_large(userObj.optString("avatar_large"));
                                cardList.add(userObj.optString("avatar_large"));
                                user.setPagefriends_count(userObj.optInt("pagefriends_count"));
                                user.setVerified_reason_url(userObj.optString("verified_reason_url"));
                                user.setMbtype(userObj.optInt("mbtype"));
                                user.setBadge(userObj.optJSONObject("badge"));
                                user.setScreen_name(userObj.optString("screen_name"));
                                cardList.add(userObj.optString("screen_name"));
                                user.setBlock_word(userObj.optInt("block_word"));

//                                XposedBridge.log("微博作者信息==== " + user.toString());
                            }

                            //卡片title
                            JSONObject titleObj = localJSONObject.optJSONObject("title");
                            String title = titleObj.optString("text");
//                            XposedBridge.log("卡片title==== " + title);
                            cardList.add(title);

                            //original_pic
                            String original_pic = localJSONObject.optString("original_pic");
//                            XposedBridge.log("图片url==== " + original_pic);

                            //reposts_count
                            int reposts_count = localJSONObject.optInt("reposts_count");
//                            XposedBridge.log("分享的数量为 ==== " + reposts_count);
                            cardList.add(reposts_count);

                            //created_at
                            String created_at = localJSONObject.optString("created_at");

//                            XposedBridge.log("创建时间 ==== " + created_at);
                            cardList.add(created_at);

                            //pic_bg_type
                            int pic_bg_type = localJSONObject.optInt("pic_bg_type");

                            //comments_count
                            int comments_count = localJSONObject.optInt("comments_count");

//                            XposedBridge.log("评论的数量为 ==== " + comments_count);
                            cardList.add(comments_count);

                            //source_allowclick
                            int source_allowclick = localJSONObject.optInt("source_allowclick");

                            //pic_bg
                            String pic_bg = localJSONObject.optString("pic_bg");
//                            XposedBridge.log("图片url pic_bg ==== " + pic_bg);

                            //bmiddle_pic
                            String bmiddle_pic = localJSONObject.optString("bmiddle_pic");

                            //source
                            String source = localJSONObject.optString("source");
//                            XposedBridge.log("source ==== " + source);

                            cardList.add(source);

                            //biz_feature
                            int biz_feature = localJSONObject.optInt("biz_feature");

                            XposedBridge.log("card info ======" + cardList.toString());
                            XposedBridge.log("=========================================");

                            //---------------------------------------------------------------------------------------

//                            if (localJSONObject != null) {
//                                Class[] inArgs = new Class[]{JSONObject.class};
//                                Object[] inArgsParms = new Object[]{localJSONObject};
//                                Constructor constructor = cardStatus.getDeclaredConstructor(inArgs); //找到指定的构造方法
//                                constructor.setAccessible(true);//设置安全检查，访问私有构造函数必须
//                                Object object = constructor.newInstance(inArgsParms);
//
//                                XposedBridge.log("com.sina.weibo.card.b.a status ==== " + object.toString());
//
//                                Method getCardInfo = cardStatus.getMethod("getCardInfo");
//                                Object cardinfo = getCardInfo.invoke(object, new Object[]{});
//                                if (cardinfo != null) {
//                                    XposedBridge.log("status cardinfo ====" + cardinfo.toString());
//
//                                    Method getActionlog = mBlogCardInfo.getMethod("getActionlog");
//                                    String actionlog = (String) getActionlog.invoke(cardinfo, new Object[]{});
//                                    if (actionlog != null) {
//                                        XposedBridge.log("CardInfo actionlog ====" + actionlog.toString());
//                                    }
//
//                                    Method getAuthorid = mBlogCardInfo.getMethod("getAuthorid");
//                                    String authorid = (String) getAuthorid.invoke(cardinfo, new Object[]{});
//                                    if (authorid != null) {
//                                        XposedBridge.log("CardInfo authorid ====" + authorid.toString());
//                                    }
//
//                                    Method getCards = mBlogCardInfo.getMethod("getCards");
//                                    String btnText = (String) getCards.invoke(cardinfo, new Object[]{});
//                                    if (btnText != null) {
//                                        XposedBridge.log("CardInfo btnText ====" + btnText.toString());
//                                    }
//
//                                    Method getContent1 = mBlogCardInfo.getMethod("getContent1");
//                                    String content1 = (String) getContent1.invoke(cardinfo, new Object[]{});
//                                    if (content1 != null) {
//                                        XposedBridge.log("CardInfo content1 ====" + content1.toString());
//                                    }
//
//                                    Method getContent1_icon = mBlogCardInfo.getMethod("getContent1_icon");
//                                    String content1icon = (String) getContent1_icon.invoke(cardinfo, new Object[]{});
//                                    if (content1icon != null) {
//                                        XposedBridge.log("CardInfo content1icon ====" + content1icon.toString());
//                                    }
//
//                                    Method getContent2 = mBlogCardInfo.getMethod("getContent2");
//                                    String content2 = (String) getContent2.invoke(cardinfo, new Object[]{});
//                                    if (content2 != null) {
//                                        XposedBridge.log("CardInfo content2 ====" + content2.toString());
//                                    }
//
//                                    Method getContent3 = mBlogCardInfo.getMethod("getContent3");
//                                    String content3 = (String) getContent3.invoke(cardinfo, new Object[]{});
//                                    if (actionlog != null) {
//                                        XposedBridge.log("CardInfo content3 ====" + content3.toString());
//                                    }
//
//                                    Method getContent4 = mBlogCardInfo.getMethod("getContent4");
//                                    String content4 = (String) getContent4.invoke(cardinfo, new Object[]{});
//                                    if (actionlog != null) {
//                                        XposedBridge.log("CardInfo content4 ====" + content4.toString());
//                                    }
//
//                                    Method getContent_data = mBlogCardInfo.getMethod("getContent_data");
//                                    String content_data = (String) getContent_data.invoke(cardinfo, new Object[]{});
//                                    if (content_data != null) {
//                                        XposedBridge.log("CardInfo content_data ====" + content_data.toString());
//                                    }
//
//                                    Method getContent_template = mBlogCardInfo.getMethod("getContent_template");
//                                    String content_template = (String) getContent_template.invoke(cardinfo, new Object[]{});
//                                    if (content_template != null) {
//                                        XposedBridge.log("CardInfo content_template ====" + content_template.toString());
//                                    }
//
//                                    Method getDesc = mBlogCardInfo.getMethod("getDesc");
//                                    String desc = (String) getDesc.invoke(cardinfo, new Object[]{});
//                                    if (desc != null) {
//                                        XposedBridge.log("CardInfo desc ====" + desc.toString());
//                                    }
//
//                                    Method getJsonButton = mBlogCardInfo.getMethod("getJsonButton");
//                                    Object jsonButton = getJsonButton.invoke(cardinfo, new Object[]{});
//                                    if (jsonButton != null) {
//                                        XposedBridge.log("CardInfo jsonButton ====" + jsonButton.toString());
//                                    }
//
//                                    Method getMedia = mBlogCardInfo.getMethod("getMedia");
//                                    Object media = getMedia.invoke(cardinfo, new Object[]{});
//                                    if (media != null) {
//                                        XposedBridge.log("CardInfo media ====" + media.toString());
//                                    }
//
//                                    Method getMonitorUrl = mBlogCardInfo.getMethod("getMonitorUrl");
//                                    String monitorUrl = (String) getMonitorUrl.invoke(cardinfo, new Object[]{});
//                                    if (monitorUrl != null) {
//                                        XposedBridge.log("CardInfo monitorUrl ====" + monitorUrl.toString());
//                                    }
//
//                                    Method getNote = mBlogCardInfo.getMethod("getNote");
//                                    String note = (String) getNote.invoke(cardinfo, new Object[]{});
//                                    if (note != null) {
//                                        XposedBridge.log("CardInfo note ====" + note.toString());
//                                    }
//
//                                    Method getObjectCategory = mBlogCardInfo.getMethod("getObjectCategory");
//                                    String objCategory = (String) getObjectCategory.invoke(cardinfo, new Object[]{});
//                                    if (objCategory != null) {
//                                        XposedBridge.log("CardInfo objCategory ====" + objCategory.toString());
//                                    }
//
//                                    Method getPagePic = mBlogCardInfo.getMethod("getPagePic");
//                                    String pagePic = (String) getPagePic.invoke(cardinfo, new Object[]{});
//                                    if (pagePic != null) {
//                                        XposedBridge.log("CardInfo pagePic ====" + pagePic.toString());
//                                    }
//
//                                    Method getPageTitle = mBlogCardInfo.getMethod("getPageTitle");
//                                    String pageTitle = (String) getPageTitle.invoke(cardinfo, new Object[]{});
//                                    if (pageTitle != null) {
//                                        XposedBridge.log("CardInfo pageTitle ====" + pageTitle.toString());
//                                    }
//
//                                    Method getPageTypePic = mBlogCardInfo.getMethod("getPageTypePic");
//                                    String pageTypePic = (String) getPageTypePic.invoke(cardinfo, new Object[]{});
//                                    if (pageTypePic != null) {
//                                        XposedBridge.log("CardInfo pageTypePic ====" + pageTypePic.toString());
//                                    }
//
//                                    Method getPageUrl = mBlogCardInfo.getMethod("getPageUrl");
//                                    String pageUrl = (String) getPageUrl.invoke(cardinfo, new Object[]{});
//                                    if (pageUrl != null) {
//                                        XposedBridge.log("CardInfo pageUrl ====" + pageUrl.toString());
//                                    }
//
//                                    Method getPage_bg_pic = mBlogCardInfo.getMethod("getPage_bg_pic");
//                                    String pageBgPic = (String) getPage_bg_pic.invoke(cardinfo, new Object[]{});
//                                    if (pageBgPic != null) {
//                                        XposedBridge.log("CardInfo pageBgPic ====" + pageBgPic.toString());
//                                    }
//
//                                    Method getPage_template_desc = mBlogCardInfo.getMethod("getPage_template_desc");
//                                    String pageTemplateDesc = (String) getPage_template_desc.invoke(cardinfo, new Object[]{});
//                                    if (pageTemplateDesc != null) {
//                                        XposedBridge.log("CardInfo pageTemplateDesc ====" + pageTemplateDesc.toString());
//                                    }
//
//                                    Method getPage_template_title = mBlogCardInfo.getMethod("getPage_template_title");
//                                    String pageTemplateTitle = (String) getPage_template_title.invoke(cardinfo, new Object[]{});
//                                    if (pageTemplateTitle != null) {
//                                        XposedBridge.log("CardInfo pageTemplateTitle ====" + pageTemplateTitle.toString());
//                                    }
//
//                                    Method getPic_info = mBlogCardInfo.getMethod("getPic_info");
//                                    Object picInfo = getPic_info.invoke(cardinfo, new Object[]{});
//                                    if (picInfo != null) {
//                                        XposedBridge.log("CardInfo picInfo ====" + picInfo.toString());
//                                    }
//
//                                    Method getSource = mBlogCardInfo.getMethod("getSource");
//                                    String source = (String) getSource.invoke(cardinfo, new Object[]{});
//                                    if (source != null) {
//                                        XposedBridge.log("CardInfo source ====" + source.toString());
//                                    }
//
//                                    Method getTemplateBeanlist = mBlogCardInfo.getMethod("getTemplateBeanlist");
//                                    List<Object> beanList = (List<Object>) getTemplateBeanlist.invoke(cardinfo, new Object[]{});
//                                    if (beanList != null) {
//                                        XposedBridge.log("CardInfo beanList ====" + beanList.toString());
//                                    }
//
//                                    Method getTips = mBlogCardInfo.getMethod("getTips");
//                                    String tips = (String) getTips.invoke(cardinfo, new Object[]{});
//                                    if (tips != null) {
//                                        XposedBridge.log("CardInfo tips ====" + tips.toString());
//                                    }
//
//                                    Method getUrlStruct = mBlogCardInfo.getMethod("getUrlStruct");
//                                    Object urlStruct = getUrlStruct.invoke(cardinfo, new Object[]{});
//                                    if (urlStruct != null) {
//                                        XposedBridge.log("CardInfo urlStruct ====" + urlStruct.toString());
//                                    }
//
//                                    Method getUserInfo = mBlogCardInfo.getMethod("getUserInfo");
//                                    Object userinfo = getUserInfo.invoke(cardinfo, new Object[]{});
//                                    if (userinfo != null) {
//                                        XposedBridge.log("CardInfo userinfo ====" + userinfo.toString());
//                                    }
//
//                                }
//
//                                Method getCommentList = cardStatus.getMethod("getCommentList");
//                                List<Object> commentList = (List<Object>) getCommentList.invoke(object, new Object[]{});
//                                if (commentList != null) {
//                                    XposedBridge.log("status commentList ====" + commentList.toString());
//                                }
//
//                                Method getCommentSummary = cardStatus.getMethod("getCommentSummary");
//                                Object summary = getCommentSummary.invoke(object, new Object[]{});
//                                if (summary != null) {
//                                    XposedBridge.log("status getCommentSummary ====" + summary.toString());
//
//                                    Method getSummaryInfo = commentSumClass.getMethod("getSummaryInfo");
//                                    String summaryInfo = (String) getSummaryInfo.invoke(summary, new Object[]{});
//                                    if (summaryInfo != null) {
//                                        XposedBridge.log("CommentSummary summaryInfo ====" + summaryInfo.toString());
//                                    }
//
//                                    Method getCommentInfo = commentSumClass.getMethod("getCommentInfo");
//                                    String commentInfo = (String) getCommentInfo.invoke(summary, new Object[]{});
//                                    if (commentInfo != null) {
//                                        XposedBridge.log("CommentSummary commentInfo ====" + commentInfo.toString());
//                                    }
//
//                                    Method getComments = commentSumClass.getMethod("getComments");
//                                    List<Object> comments = (List<Object>) getComments.invoke(summary, new Object[]{});
//                                    for (int i = 0; i < comments.size(); i++) {
//                                        if (comments != null && comments.get(i) != null) {
//                                            XposedBridge.log("StatusComment comments ====" + comments.get(i).toString());
//
//                                            Method getComment = statusCommentClass.getMethod("getComment");
//                                            String comment = (String) getComment.invoke(comments.get(i), new Object[]{});
//                                            if (comment != null) {
//                                                XposedBridge.log("StatusComment comment ====" + summaryInfo.toString());
//                                            }
//
//                                            Method getCreatedAt = statusCommentClass.getMethod("getCreatedAt");
//                                            String createdat = (String) getCreatedAt.invoke(comments.get(i), new Object[]{});
//                                            if (createdat != null) {
//                                                XposedBridge.log("StatusComment createdat ====" + createdat.toString());
//                                            }
//
//                                            Method getName = statusCommentClass.getMethod("getName");
//                                            String name = (String) getName.invoke(comments.get(i), new Object[]{});
//                                            if (name != null) {
//                                                XposedBridge.log("StatusComment name ====" + name.toString());
//                                            }
//
//                                            Method getReplyComment = statusCommentClass.getMethod("getReplyComment");
//                                            Object replyComment = getReplyComment.invoke(comments.get(i), new Object[]{});
//                                            if (replyComment != null) {
//                                                XposedBridge.log("StatusComment replyComment ====" + replyComment.toString());
//                                            }
//
//                                            Method getScheme = statusCommentClass.getMethod("getScheme");
//                                            String scheme = (String) getScheme.invoke(comments.get(i), new Object[]{});
//                                            if (scheme != null) {
//                                                XposedBridge.log("StatusComment scheme ====" + scheme.toString());
//                                            }
//
//                                            Method getSource = statusCommentClass.getMethod("getSource");
//                                            String source = (String) getSource.invoke(comments.get(i), new Object[]{});
//                                            if (source != null) {
//                                                XposedBridge.log("StatusComment source ====" + source.toString());
//                                            }
//
//                                            Method getUser = statusCommentClass.getMethod("getUser");
//                                            Object user = getUser.invoke(comments.get(i), new Object[]{});
//                                            if (user != null) {
//                                                XposedBridge.log("StatusComment user ====" + user.toString());
//                                            }
//
//                                        }
//                                    }
//
//                                }
//
//                                Method getComplaint = cardStatus.getMethod("getComplaint");
//                                String complaint = (String) getComplaint.invoke(object, new Object[]{});
//                                if (complaint != null) {
//                                    XposedBridge.log("status complaint ====" + complaint.toString());
//                                }
//
//                                //微博内容
//                                Method getText = cardStatus.getMethod("getText");
//                                String text1 = (String) getText.invoke(object, new Object[]{});
//                                if (text != null) {
//                                    XposedBridge.log("status text ====" + text.toString());
//                                }
//
//                                //getReposts_count 分享的数量
//                                Method getReposts_count = cardStatus.getMethod("getReposts_count");
//                                int repostCount = (int) getReposts_count.invoke(object, new Object[]{});
//                                    XposedBridge.log("status repostCount ====" + repostCount);
//
//                                //getAttitudes_count 点赞的数量
//                                Method getAttitudes_count = cardStatus.getMethod("getAttitudes_count");
//                                int attitudeCount = (int) getAttitudes_count.invoke(object, new Object[]{});
//                                    XposedBridge.log("status attitudeCount ====" + attitudeCount);
//
//                                //getComments_count 评论数量
//                                Method getComments_count = cardStatus.getMethod("getComments_count");
//                                int comment_count = (int) getComments_count.invoke(object, new Object[]{});
//                                    XposedBridge.log("status comment_count ====" + comment_count);
//
//                                Method getComplaintUrl = cardStatus.getMethod("getComplaintUrl");
//                                String complaintUrl = (String) getComplaintUrl.invoke(object, new Object[]{});
//                                if (complaintUrl != null) {
//                                    XposedBridge.log("status complaintUrl ====" + complaintUrl.toString());
//                                }
//
//                                Method getContinueTag = cardStatus.getMethod("getContinueTag");
//                                Object continueTag = getContinueTag.invoke(object, new Object[]{});
//                                if (continueTag != null) {
//                                    XposedBridge.log("status continueTag ====" + continueTag.toString());
//
//                                    Method getPic = continueTagClass.getMethod("getPic");
//                                    String pic = (String) getPic.invoke(continueTag, new Object[]{});
//                                    if (pic != null) {
//                                        XposedBridge.log("ContinueTag pic ====" + pic.toString());
//                                    }
//
//                                    Method getScheme = continueTagClass.getMethod("getScheme");
//                                    String scheme = (String) getScheme.invoke(continueTag, new Object[]{});
//                                    if (scheme != null) {
//                                        XposedBridge.log("ContinueTag scheme ====" + scheme.toString());
//                                    }
//
//                                    Method getTitle = continueTagClass.getMethod("getTitle");
//                                    String title = (String) getTitle.invoke(continueTag, new Object[]{});
//                                    if (title != null) {
//                                        XposedBridge.log("ContinueTag title ====" + title.toString());
//                                    }
//
//                                }
//
//                                //创建时间
//                                Method getCreatedDateStr = cardStatus.getMethod("getCreatedDateStr");
//                                String date = (String) getCreatedDateStr.invoke(object, new Object[]{});
//                                if (date != null) {
//                                    XposedBridge.log("Status date ====" + date.toString());
//                                }
//
//                                Method getExtraButtonInfo = cardStatus.getMethod("getExtraButtonInfo");
//                                Object extraButtonInfo = getExtraButtonInfo.invoke(object, new Object[]{});
//                                if (extraButtonInfo != null) {
//                                    XposedBridge.log("MBlogListItemView$f extraButtonInfo ====" + extraButtonInfo.toString());
//
//                                    Method getActionlog = mBlogExtraButtonInfo.getMethod("getActionlog");
//                                    String actionlog = (String) getActionlog.invoke(extraButtonInfo, new Object[]{});
//                                    if (actionlog != null) {
//                                        XposedBridge.log("extraButtonInfo actionlog ====" + actionlog.toString());
//                                    }
//
//                                    Method getExtraButtonImage = mBlogExtraButtonInfo.getMethod("getExtraButtonImage");
//                                    String buttonimage = (String) getExtraButtonImage.invoke(extraButtonInfo, new Object[]{});
//                                    if (buttonimage != null) {
//                                        XposedBridge.log("extraButtonInfo buttonimage ====" + buttonimage.toString());
//                                    }
//
//                                    Method getExtraButtonImageHighlight = mBlogExtraButtonInfo.getMethod("getExtraButtonImageHighlight");
//                                    String btnImageHignlight = (String) getExtraButtonImageHighlight.invoke(extraButtonInfo, new Object[]{});
//                                    if (btnImageHignlight != null) {
//                                        XposedBridge.log("extraButtonInfo btnImageHignlight ====" + btnImageHignlight.toString());
//                                    }
//
//                                }
//
//                                Method getFormatSourceDesc = cardStatus.getMethod("getFormatSourceDesc");
//                                String sourceDesc = (String) getFormatSourceDesc.invoke(object, new Object[]{});
//                                if (sourceDesc != null) {
//                                    XposedBridge.log("status sourceDesc ====" + sourceDesc.toString());
//                                }
//
//                                Method getFormatSourceUrl = cardStatus.getMethod("getFormatSourceUrl");
//                                String sourceUrl = (String) getFormatSourceUrl.invoke(object, new Object[]{});
//                                if (sourceUrl != null) {
//                                    XposedBridge.log("status sourceUrl ====" + sourceUrl.toString());
//                                }
//
//                                Method getForwardSummary = cardStatus.getMethod("getForwardSummary");
//                                Object forwardSummary = (Object) getForwardSummary.invoke(object, new Object[]{});
//                                if (forwardSummary != null) {
//                                    XposedBridge.log("MBlogListItemView$f forwardSummary ====" + forwardSummary.toString());
//
//                                    Method getForwardInfo = forwardSumClass.getMethod("getForwardInfo");
//                                    String forwardInfo = (String) getForwardInfo.invoke(forwardSummary, new Object[]{});
//                                    if (forwardInfo != null) {
//                                        XposedBridge.log("ForwardSummary forwardInfo ====" + forwardInfo.toString());
//                                    }
//
//                                    Method getForwards = forwardSumClass.getMethod("getForwards");
//                                    List<Object> forwardList = (List<Object>) getForwards.invoke(forwardSummary, new Object[]{});
//                                    if (forwardList != null) {
//                                        XposedBridge.log("ForwardSummary forwardList ====" + forwardList.toString());
//                                    }
//
//                                    Method getSummaryInfo = forwardSumClass.getMethod("getSummaryInfo");
//                                    String summaryInfo = (String) getSummaryInfo.invoke(forwardSummary, new Object[]{});
//                                    if (summaryInfo != null) {
//                                        XposedBridge.log("ForwardSummary summaryInfo ====" + summaryInfo.toString());
//                                    }
//
//                                }
//
//                                Method getIn_reply_to_user_id = cardStatus.getMethod("getIn_reply_to_user_id");
//                                String id = (String) getIn_reply_to_user_id.invoke(object, new Object[]{});
//                                if (id != null) {
//                                    XposedBridge.log("status id ====" + id.toString());
//                                }
//
//                                Method getKeyword_struct = cardStatus.getMethod("getKeyword_struct");
//                                List<Object> keyword_struct = (List<Object>) getKeyword_struct.invoke(object, new Object[]{});
//                                if (keyword_struct != null) {
//                                    XposedBridge.log("status keyword_struct ====" + keyword_struct.toString());
//                                }
//
//                                Method getLikeSummary = cardStatus.getMethod("getLikeSummary");
//                                Object likeSummary = getLikeSummary.invoke(object, new Object[]{});
//                                if (likeSummary != null) {
//                                    XposedBridge.log("status likeSummary ====" + likeSummary.toString());
//                                }
//
//                                Method getLongText = cardStatus.getMethod("getLongText");
//                                Object longtext = getLongText.invoke(object, new Object[]{});
//                                if (longtext != null) {
//                                    XposedBridge.log("status longtext ====" + longtext.toString());
//                                }
//
//                                Method getMBlogTag = cardStatus.getMethod("getMBlogTag");
//                                List<Object> tagList = (List<Object>) getMBlogTag.invoke(object, new Object[]{});
//                                if (tagList != null) {
//                                    XposedBridge.log("status tagList ====" + tagList.toString());
//                                }
//
//                                Method getMblogButtons = cardStatus.getMethod("getMblogButtons");
//                                List<Object> buttons = (List<Object>) getMblogButtons.invoke(object, new Object[]{});
//                                if (buttons != null) {
//                                    XposedBridge.log("status buttons ====" + buttons.toString());
//                                }
//
//
//                                //头部title
//                                Method getMblogTitle = cardStatus.getMethod("getMblogTitle");
//                                Object blogTitle = getMblogTitle.invoke(object, new Object[]{});
//                                if (blogTitle != null) {
//                                    XposedBridge.log("status blogTitle ====" + blogTitle.toString());
//
//                                    Method getIcon_url = blogTitleClass.getMethod("getIcon_url");
//                                    String iconurl = (String) getIcon_url.invoke(blogTitle, new Object[]{});
//                                    if (iconurl != null) {
//                                        XposedBridge.log("BlogTitle iconurl ====" + iconurl.toString());
//                                    }
//
//                                    Method getTitle = blogTitleClass.getMethod("getTitle");
//                                    String title = (String) getTitle.invoke(blogTitle, new Object[]{});
//                                    if (title != null) {
//                                        XposedBridge.log("BlogTitle title ====" + iconurl.toString());
//                                    }
//
//                                    Method getTitleInfos = blogTitleClass.getMethod("getTitleInfos");
//                                    List<Object> titleInfoList = (List<Object>) getTitleInfos.invoke(blogTitle, new Object[]{});
//                                    for (int i = 0; i < titleInfoList.size(); i++) {
//                                        if (titleInfoList != null && titleInfoList.get(i) != null) {
//                                            XposedBridge.log("BlogTitle titleinfo ====" + titleInfoList.get(i).toString());
//
//                                            Method getName = blogTitleInfoClass.getMethod("getName");
//                                            String name = (String) getName.invoke(titleInfoList.get(i), new Object[]{});
//                                            if (name != null) {
//                                                XposedBridge.log("BlogTitleInfo name ====" + name.toString());
//                                            }
//
//                                            Method getScheme = blogTitleInfoClass.getMethod("getScheme");
//                                            String scheme = (String) getScheme.invoke(titleInfoList.get(i), new Object[]{});
//                                            if (scheme != null) {
//                                                XposedBridge.log("BlogTitleInfo scheme ====" + scheme.toString());
//                                            }
//
//                                        }
//                                    }
//
//                                }
//
//                                Method getPicInfos = cardStatus.getMethod("getPicInfos");
//                                List<Object> picinfos = (List<Object>) getPicInfos.invoke(object, new Object[]{});
//
//                                for (int i = 0; i < picinfos.size(); i++) {
//                                    if (picinfos != null && picinfos.get(i) != null) {
//                                        XposedBridge.log("picinfos ====" + picinfos.get(i).toString());
//
//                                        Method getBmiddleUrl = picinfoClass.getMethod("getBmiddleUrl");
//                                        String middleUrl = (String) getBmiddleUrl.invoke(object, new Object[]{});
//                                        if (middleUrl != null) {
//                                            XposedBridge.log("picinfo middleUrl ====" + middleUrl.toString());
//                                        }
//
//                                        Method getButtonScheme = picinfoClass.getMethod("getButtonScheme");
//                                        String buttonScheme = (String) getButtonScheme.invoke(object, new Object[]{});
//                                        if (buttonScheme != null) {
//                                            XposedBridge.log("picinfo buttonScheme ====" + buttonScheme.toString());
//                                        }
//
//                                        Method getLarge = picinfoClass.getMethod("getLarge");
//                                        Object picinfosize = (String) getLarge.invoke(object, new Object[]{});
//                                        if (picinfosize != null) {
//                                            XposedBridge.log("picinfo picinfosize ====" + picinfosize.toString());
//
//                                            Method getCut_type = picinfoSizeClass.getMethod("getCut_type");
//                                            String cut_type = (String) getCut_type.invoke(picinfosize, new Object[]{});
//                                            if (cut_type != null) {
//                                                XposedBridge.log("picinfosize cut_type ====" + cut_type.toString());
//                                            }
//
//                                            Method getHeight = picinfoSizeClass.getMethod("getHeight");
//                                            String height = (String) getHeight.invoke(picinfosize, new Object[]{});
//                                            if (height != null) {
//                                                XposedBridge.log("picinfosize type ====" + height.toString());
//                                            }
//
//                                            Method getType = picinfoSizeClass.getMethod("getType");
//                                            String type = (String) getType.invoke(picinfosize, new Object[]{String.class});
//                                            if (type != null) {
//                                                XposedBridge.log("picinfosize type ====" + type.toString());
//                                            }
//
//                                            Method getUrl = picinfoSizeClass.getMethod("getUrl");
//                                            String url = (String) getUrl.invoke(picinfosize, new Object[]{});
//                                            if (url != null) {
//                                                XposedBridge.log("picinfosize url ====" + url.toString());
//                                            }
//
//                                            Method getWidth = picinfoSizeClass.getMethod("getWidth");
//                                            String width = (String) getWidth.invoke(picinfosize, new Object[]{});
//                                            if (width != null) {
//                                                XposedBridge.log("picinfosize width ====" + width.toString());
//                                            }
//
//                                        }
//
//                                    }
//                                }
//
//                                Method getPromotion = cardStatus.getMethod("getPromotion");
//                                Object promotion = getPromotion.invoke(object, new Object[]{});
//                                if (promotion != null) {
//                                    XposedBridge.log("Promotion promotion ====" + promotion.toString());
//
//                                    Method getMark = promotionClass.getMethod("getMark");
//                                    String mark = (String) getMark.invoke(promotion, new Object[]{});
//                                    if (mark != null) {
//                                        XposedBridge.log("Promotion mark ====" + mark.toString());
//                                    }
//
//                                    Method getMonitor_url = promotionClass.getMethod("getMonitor_url");
//                                    String monitorUrl = (String) getMonitor_url.invoke(promotion, new Object[]{});
//                                    if (monitorUrl != null) {
//                                        XposedBridge.log("Promotion monitorUrl ====" + monitorUrl.toString());
//                                    }
//
//                                    Method getRecommend = promotionClass.getMethod("getRecommend");
//                                    String recommend = (String) getRecommend.invoke(promotion, new Object[]{});
//                                    if (recommend != null) {
//                                        XposedBridge.log("Promotion recommend ====" + recommend.toString());
//                                    }
//
//                                    Method getType = promotionClass.getMethod("getType");
//                                    String type = (String) getType.invoke(promotion, new Object[]{});
//                                    if (type != null) {
//                                        XposedBridge.log("Promotion type ====" + type.toString());
//                                    }
//
//                                }
//
//                                Method getPromotionInfo = cardStatus.getMethod("getPromotionInfo");
//                                Object promotionInfo = getPromotionInfo.invoke(object, new Object[]{});
//                                if (promotionInfo != null) {
//                                    XposedBridge.log("MBlogListItemView$f promotionInfo ====" + promotionInfo.toString());
//                                }
//
//                                Method getReads_count = cardStatus.getMethod("getReads_count");
//                                String reads_count = (String) getReads_count.invoke(object, new Object[]{});
//                                if (reads_count != null) {
//                                    XposedBridge.log("MBlogListItemView$f reads_count ====" + reads_count.toString());
//                                }
//
//                                Method getUser = cardStatus.getMethod("getUser");
//                                Object user = getUser.invoke(object, new Object[]{});
//                                if (user != null) {
//                                    XposedBridge.log("MBlogListItemView$f user ====" + user.toString());
//
//                                    Method getBirthday = userInfoClass.getMethod("getBirthday");
//                                    String birthday = (String) getBirthday.invoke(user, new Object[]{});
//                                    if (birthday != null) {
//                                        XposedBridge.log("userinfo birthday ====" + birthday.toString());
//                                    }
//
//                                    //getProfileImageUrl 头像url
//                                    Method getProfileImageUrl = userInfoClass.getMethod("getProfileImageUrl");
//                                    String profileUrl = (String) getProfileImageUrl.invoke(user, new Object[]{});
//                                    if (profileUrl != null) {
//                                        XposedBridge.log("userinfo profileUrl ====" + profileUrl.toString());
//                                    }
//
//                                    //getVerifiedReason 认证信息
//                                    Method getVerifiedReason = userInfoClass.getMethod("getVerifiedReason");
//                                    String verifiInfo = (String) getVerifiedReason.invoke(user, new Object[]{});
//                                    if (verifiInfo != null) {
//                                        XposedBridge.log("userinfo verifiInfo ====" + verifiInfo.toString());
//                                    }
//
//                                    Method getBlogurl = userInfoClass.getMethod("getBlogurl");
//                                    String blogUrl = (String) getBlogurl.invoke(user, new Object[]{});
//                                    if (blogUrl != null) {
//                                        XposedBridge.log("userinfo blogUrl ====" + blogUrl.toString());
//                                    }
//
//                                    //用户信息 地点
//                                    Method getLocation = userInfoClass.getMethod("getLocation");
//                                    String location = (String) getLocation.invoke(user, new Object[]{});
//                                    if (location != null) {
//                                        XposedBridge.log("userinfo location ====" + location.toString());
//                                    }
//
//                                    Method getCoverUrl = userInfoClass.getMethod("getCoverUrl");
//                                    String coverUrl = (String) getCoverUrl.invoke(user, new Object[]{});
//                                    if (coverUrl != null) {
//                                        XposedBridge.log("userinfo coverUrl ====" + coverUrl.toString());
//                                    }
//
//                                    Method getCreatedAt = userInfoClass.getMethod("getCreatedAt");
//                                    String createdat = (String) getCreatedAt.invoke(user, new Object[]{});
//                                    if (createdat != null) {
//                                        XposedBridge.log("userinfo createdat ====" + createdat.toString());
//                                    }
//
//                                    Method getCreditScore = userInfoClass.getMethod("getCreditScore");
//                                    String creditScore = (String) getCreditScore.invoke(user, new Object[]{});
//                                    if (creditScore != null) {
//                                        XposedBridge.log("userinfo creditScore ====" + creditScore.toString());
//                                    }
//
//                                    Method getDescription = userInfoClass.getMethod("getDescription");
//                                    String desc = (String) getDescription.invoke(user, new Object[]{});
//                                    if (desc != null) {
//                                        XposedBridge.log("userinfo desc ====" + desc.toString());
//                                    }
//
//                                    Method getEmail = userInfoClass.getMethod("getEmail");
//                                    String email = (String) getEmail.invoke(user, new Object[]{});
//                                    if (email != null) {
//                                        XposedBridge.log("userinfo email ====" + email.toString());
//                                    }
//
//                                    Method getGender = userInfoClass.getMethod("getGender");
//                                    String gender = (String) getGender.invoke(user, new Object[]{});
//                                    if (gender != null) {
//                                        XposedBridge.log("userinfo gender ====" + gender.toString());
//                                    }
//                                }
//
//                                Method getUserScreenName = cardStatus.getMethod("getUserScreenName");
//                                String screenname = (String) getUserScreenName.invoke(object, new Object[]{});
//                                if (screenname != null) {
//                                    XposedBridge.log("MBlogListItemView$f screenname ====" + screenname.toString());
//                                }
//
//                                XposedBridge.log("==============================");
//
//                            }

                        }

                    }
                });

    }





}
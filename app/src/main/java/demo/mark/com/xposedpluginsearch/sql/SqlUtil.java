package demo.mark.com.xposedpluginsearch.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Berkeley on 4/26/16.
 */
public class SqlUtil {

    private static String DB_NAME = "news.db";
    private static int DB_VERSION = 1;
    private static int POSTION;

    public static List<News> queryAll(Context context,SQLiteDatabase db){
            List<News> cityList = new ArrayList<>();
            Cursor cursor = db.query(SQLiteHelper.TB_NAME, null, null, null, null, null, News.ID + " DESC");
            cursor.moveToFirst();
            while(!cursor.isAfterLast() && (cursor.getString(1) != null)){
                News city = new News();
                city.setId(cursor.getString(0));
                city.setTitle(cursor.getString(1));
                cityList.add(city);
                cursor.moveToNext();
            }

        return cityList;
    }

    public static int insert(String title,SQLiteDatabase db){
        int errorCode = 0;
        synchronized (DBInstance.getInstance()) {
            try {
                ContentValues values = new ContentValues();
                values.put(News.NEWS_TITLE, title);
                db.insertOrThrow(SQLiteHelper.TB_NAME, News.ID, values);
            } catch (Exception e) {
                e.printStackTrace();
                XposedBridge.log("c出入错误：" + e.toString());
                errorCode = 1;
            }

            return errorCode;


        }

    }


    public static boolean hasNews(String title, SQLiteDatabase db) {
        synchronized (DBInstance.getInstance()) {
            String sqlCity = title.length() > 0 ? "title='" + title + "'" : "";
            Cursor cursor = db.query(true, SQLiteHelper.TB_NAME,
                    new String[]{"_id", "title"},
                    sqlCity,
                    null, null, null, null, null);
            cursor.moveToFirst();
            News news = new News();

            while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
                news.setId(cursor.getString(0));
                news.setTitle(cursor.getString(1));
                cursor.moveToNext();
            }
            XposedBridge.log("news.getTitle()：＝＝＝" + news.getTitle());
            if(news.getTitle() == null){
                return false;
            }

            return true;
        }
    }
}

package demo.mark.com.xposedpluginsearch.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * 数据库创建类
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String TB_NAME = "news";

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * 创建数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME + "(" +
				News.ID + " integer primary key," +
				News.NEWS_TITLE + " varchar)");
	}
	
	/**
	 * 数据库更新
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
		onCreate(db);
	}
	
	/**
	 * 更新数据库列名
	 *
	 * @param db
	 * @param oldColumn
	 * @param newColumn
	 * @param typeColumn
	 */
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
		try{
			db.execSQL("ALTER TABLE " +
					TB_NAME + " CHANGE " +
					oldColumn + " "+ newColumn +
					" " + typeColumn
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

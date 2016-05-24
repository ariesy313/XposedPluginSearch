package demo.mark.com.xposedpluginsearch.sql;//package me.chiontang.wechatmomentexport.sql;
//
///**
// * Created by wudi on 16/4/26.
// */
//import java.util.ArrayList;
//
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDiskIOException;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteFullException;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//
///**
// *
// @Title: DBHelper
//  * @Package com.coupon.activity
// * @Description: TODO
// * @author wudi
// * @date 2012-8-2 2012
// * @version V1.0
// */
//
//public class DBHelper {
//
//    /** errorCode 成功是0 失败为-1 */
//
//    private final String CLASSTAG = DBHelper.class.getSimpleName();
//
//    Context mCotext;
//    private final static String _DB_NAME = "WdDB";
//    private final static int _DB_VERSION = 1;
//
//    OpenHelper mDbOpenHelper;
//
//    public DBHelper(Context context) {
//        super();
//        mCotext = context;
//        mDbOpenHelper = new OpenHelper(context);
//    }
//
//    /**
//     * task表
//     * */
//    final static String TASK_LIST_EXCEL = "TASK_LIST_EXCEL";
//
//    /** task表 */
//
//    final static String CREAT_TB_TASK_EXCEL = "CREATE TABLE "
//            + TASK_LIST_EXCEL
//            + "("
//            + " TASK_ID TEXT Integer NOT NULL,"
//            + " TITLE TEXT varchar(50),"
//            + " GIVE_UP_FLAG TEXT varchar," // 是否是放弃打卡, 1为是 0为不是
//            + " TOTAL_DATE_COUNT TEXT Integer," // 打卡天数
//            + " COLOR TEXT Integer," // 颜色
//            + " ALL_DATE TEXT varchar(50),"// 所有天数 “，”隔开
//            + " CURRENT_STREAK TEXT Integer," // 当前连续打卡天数
//            + " TIME_LIMIT_FLAG TEXT Integer," // 限时打卡
//            + " CHECK_TIME TEXT varchar(50)," // 当日最后两次打卡的时间,
//            // 格式为HH:mm,若jobCount为2则用","分隔,
//            // 若jobCount为0或大于2则不不显示
//            + " JOB_COUNT TEXT Integer," // 当日打卡次数
//            + " BEGIN_TIME TEXT varchar(50)," + " END_TIME TEXT varchar(50),"
//            + " STICK_FLAG TEXT Integer," // 置顶 1 置顶
//            + " STICK_TIME TEXT timestamp," // 置顶时间
//            + " CTIME TEXT timestamp," // 创建时间
//            + " ISCONN_SUCCESS TEXT Integer," // 是否聯網成功
//            + " STATUS TEXT Integer" + ");"; // 1-正常， 2-暂停
//
//    /**
//     * job表
//     * */
//    final static String JOB_EXCEL = "JOB_EXCEL";
//
//    /** job表 */
//
//    final static String CREAT_TB_JOB_EXCEL = "CREATE TABLE " + JOB_EXCEL
//            + "("
//            + " JOB_ID TEXT varchar(50) NOT NULL,"
//            + " GIVE_UP_FLAG TEXT Integer," // 是否放弃
//            + " TASK_ID TEXT Integer NOT NULL,"
//            + " QUANTITY TEXT varchar(2) NOT NULL," // 数值
//            + " CHECK_DATE TEXT varchar(50)," // 打卡时间 yyyy-MM-dd
//            + " CTIME TEXT timestamp," // 创建时间
//            + " GIVE_UP_REASON TEXT varchar(50)," // 放弃理由
//            + " ISCONN_SUCCESS TEXT Integer," // 是否聯網成功
//            + " PIC_COUNT TEXT Integer NOT NULL," // 图片数量
//            + " CHECK_TIME TEXT timestamp," // 打卡时间 时间戳
//            + " ALL_PATH TEXT varchar(50)," // 打卡图片
//            + " SIGNATURE TEXT varchar(50)," // 打卡写的话
//            + " STATUS TEXT Integer" + ");";
//
//    // =========================================================
//
//    /**
//     * 连续打卡表
//     * */
//    final static String DAILY_PERFORMANCE_EXCEL = "DAILY_PERFORMANCE_EXCEL";
//
//    /** 连续打卡表 */
//
//    final static String CREAT_TB_DAILY_PERFORMANCE_EXCEL = "CREATE TABLE "
//            + DAILY_PERFORMANCE_EXCEL + "(" + " PERFORMANCE Integer NOT NULL,"
//            + " RECORD_DATE varchar(50)" + ");";
//
//    /** 提醒表表名 */
//    final static String ALARM_EXCEL = "ALARM_EXCEL";
//
//    /** 提醒表 */
//    final static String CREATE_TB_ALARM_EXCEL = "CREATE TABLE ALARM_EXCEL"
//            + "(id integer primary key autoincrement,"
//            + "alarm_id varchar(50) NOT NULL," + // 闹表Id, 客户端生成(必须)
//            "taskId varchar(50) NOT NULL," + // 对应的taskId, (必须)
//            "days varchar(50)," + // 需要提醒的星期, 以","分隔, 例如"1,2,4,6,7"
//            "time varchar(50)," + // 提醒时间, 格式为HH:MM
//            "ringtone varchar(50)," + // 提醒铃声, 根据平台对应不同铃声
//            "vibrationFlag varchar(50)," + // 是否震动, 1为是 0为不是(必须)
//            "remark varchar(50)," + // 备注
//            "status varchar(50)," + // 闹表状态, 1为开启, 2为关闭(必须)
//            "pickervisity varchar(50)," + // 自己加的Timerpicker是否显示 1 为不显示 2 为显示
//            "title varchar(50));";
//
//    // =========================================================
//    /***
//     * 添加task list
//     */
//    public int insertTaskList(ArrayList<ByDateBean> list) {
//        int errorCode = 0;
//        synchronized (DBInstance.getInstance()) {
//            SQLiteDatabase mDb = openDatabase(true);
//
//            if (mDb != null) {
//                mDb.beginTransaction();
//
//                mDb.delete(TASK_LIST_EXCEL, "", null);
//
//                try {
//                    // JSONArray result = new JSONArray(list);
//
//                    for (int i = 0; i < list.size(); i++) {
//                        // JSONObject item = result.getJSONObject(i);
//
//                        ContentValues insertValues = new ContentValues();
//                        ByDateBean bean = list.get(i);
//
//
//                        mDb.insertOrThrow(TASK_LIST_EXCEL, null, insertValues);
//                        Tools.getLog(Tools.i, "aaa", "insert------------------"
//                                + bean.getTitle());
//                    }
//
//                    mDb.setTransactionSuccessful();
//
//                } catch (SQLException e) {
//                    errorCode--;
//                    Log.e("kkk",
//                            "insertProductsList SQLException:" + e.getMessage());
//
//                } finally {
//                    mDb.endTransaction();
//                    mDb.close();
//                    // returnExcelCount(PRODUCT_LIST_EXCEL, null);
//                }
//            } else {
//                errorCode--;
//            }
//        }
//        return errorCode;
//
//    }
//
//
//
//    /** 获得全部的alarm提醒的数据 */
//    public TaskItemBean getFialJobBean(String jobId) {
//        TaskItemBean bean = new TaskItemBean();
//        synchronized (DBInstance.getInstance()) {
//
//            Cursor c = null;
//            SQLiteDatabase mDb = openDatabase(false);
//
//            if (mDb != null) {
//                try {
//                    /**
//                     * JOB_ID TEXT varchar(50) NOT NULL," +
//                     * " GIVE_UP_FLAG TEXT Integer," // 是否放弃 +
//                     * " TASK_ID TEXT Integer NOT NULL," +
//                     * " QUANTITY TEXT varchar(2) NOT NULL," // 数值 +
//                     * " CHECK_DATE TEXT varchar(50)," // 打卡时间 yyyy-MM-dd +
//                     * " CTIME TEXT timestamp," // 创建时间 +
//                     * " GIVE_UP_REASON TEXT varchar(50)," // 放弃理由 +
//                     * " ISCONN_SUCCESS TEXT Integer," // 是否聯網成功 +
//                     * " PIC_COUNT TEXT Integer NOT NULL," // 图片数量 +
//                     * " CHECK_TIME TEXT timestamp," // 打卡时间 时间戳 +
//                     * " ALL_PATH TEXT varchar(50)," // 打卡图片 +
//                     * " SIGNATURE TEXT varchar(50)," // 打卡写的话 +
//                     * " STATUS TEXT Integer" + ");";
//                     * */
//                    c = mDb.query(JOB_EXCEL, null, "JOB_ID='"+jobId+"'", null, null, null, null);
//
//                    c.moveToFirst();
//                    bean.setJobId(c.getString(0));
//                    bean.setGive_up_flag(c.getString(1));
//                    bean.setTaskId(c.getString(2));
//                    bean.setQuantity(c.getString(3));
//                    bean.setDate(c.getString(4));
//                    bean.setTime(c.getString(5));
//                    bean.setGive_up_reason(c.getString(6));
//                    bean.setIsConnSucces(c.getInt(7));
//                    bean.setPicCount(c.getString(8));
//                    bean.setCheck_time(c.getLong(9));
//                    bean.setAllPath(c.getString(10));
//                    bean.setSignature(c.getString(11));
//                    c.moveToNext();
//
//                } catch (SQLException e) {
//
//                    Log.e(CLASSTAG + " read table exception : ", e.getMessage());
//
//                } finally {
//                    if (c != null && !c.isClosed()) {
//                        c.close();
//                        c = null;
//                    }
//                    mDb.close();
//                    mDb = null;
//                }
//            }
//        }
//
//        return bean;
//    }
//
//    public int deleteFialJobBean() {
//        int errorCode = 0;
//        synchronized (DBInstance.getInstance()) {
//            SQLiteDatabase mDb = openDatabase(true);
//
//            if (mDb != null) {
//                mDb.beginTransaction();
//
//                // mDb.delete(SEARCH_HISTORY_LIST_EXCEL, "", null);
//
//                try {
//                    mDb.delete(JOB_EXCEL, "", null);
//                    mDb.setTransactionSuccessful();
//
//                } catch (SQLException e) {
//                    errorCode--;
//                    Log.e("kkk",
//                            "JOB_EXCEL SQLException:" + e.getMessage());
//
//                } finally {
//                    mDb.endTransaction();
//                    mDb.close();
//                }
//            } else {
//                errorCode--;
//            }
//        }
//        return errorCode;
//
//    }
//
//
//
//    // ///////////////////////////////////////////////////////////////////
//    // DB MANAGER
//    // ////////////////////////////////////////////////////////////////////
//    private SQLiteDatabase openDatabase(boolean writable) {
//
//        SQLiteDatabase db = null;
//
//        if (mDbOpenHelper != null) {
//            mDbOpenHelper.close();
//        }
//
//        try {
//            if (writable) {
//                db = mDbOpenHelper.getWritableDatabase();
//            } else {
//                db = mDbOpenHelper.getReadableDatabase();
//            }
//        } catch (SQLiteFullException e) {
//            e.printStackTrace();
//        } catch (SQLiteDiskIOException e) {
//            e.printStackTrace();
//        } catch (SQLiteException e) {
//            e.printStackTrace();
//        }
//        return db;
//    }
//
//    public void createTable() {
//
//    }
//
//    private class OpenHelper extends SQLiteOpenHelper {
//
//        public OpenHelper(Context context) {
//            super(context, _DB_NAME, null, _DB_VERSION);
//        }
//
//        public void onCreate(SQLiteDatabase db) {
//
//            db.beginTransaction();
//
//            try {
//                db.execSQL(CREAT_TB_TASK_EXCEL);
//                db.execSQL(CREAT_TB_JOB_EXCEL);
//                db.execSQL(CREAT_TB_DAILY_PERFORMANCE_EXCEL);
//                db.execSQL(CREATE_TB_ALARM_EXCEL);
//                db.setTransactionSuccessful();
//
//            } catch (Exception ex) {
//
//                ex.printStackTrace();
//
//            } finally {
//
//                if (db != null) {
//
//                    db.endTransaction();
//                }
//            }
//        }
//
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//            db.beginTransaction();
//
//            try {
//
//                db.execSQL("DROP TABLE IF EXISTS " + CREAT_TB_TASK_EXCEL);
//                db.execSQL("DROP TABLE IF EXISTS " + CREAT_TB_JOB_EXCEL);
//                db.execSQL("DROP TABLE IF EXISTS "
//                        + CREAT_TB_DAILY_PERFORMANCE_EXCEL);
//                db.execSQL("DROP TABLE IF EXISTS " + ALARM_EXCEL);
//                db.setTransactionSuccessful();
//
//            } catch (Exception ex) {
//
//                ex.printStackTrace();
//
//            } finally {
//
//                if (db != null) {
//                    db.endTransaction();
//                    onCreate(db);
//                }
//            }
//        }
//    }

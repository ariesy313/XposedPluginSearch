package demo.mark.com.xposedpluginsearch.sql;

/**
 * Created by wudi on 16/4/26.
 */
public final class DBInstance {

    private static final DBInstance ins = new DBInstance();

    public static DBInstance getInstance(){
        return ins;
    }
}
package cn.adminzero.helloword;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.adminzero.helloword.db.DbUtil;

import cn.adminzero.helloword.util.MyStorage;

public class App extends Application {

    private static final String TAG = "Helloword";
    private static String Dbname = "HelloWord";
    /**
     * 是否第一次运行APP  数据保存在同名字段"isFirstRunApp"
     */
    private static int isFirstRunApp = 0;
    private static int Version = 1;
    private static int userid = 1;
    private static Context context;

    public static String getTAG() {
        return TAG;
    }

    public static int getVersion() {
        return Version;
    }

    public static String getDbname() {
        return Dbname;
    }

    public static int getuserid() {
        return userid;
    }

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        /**
         * 判断数据库初始化
         * */
        MyStorage myStorage = new MyStorage();
        /**
         * 初始化判断
         * */
        int isFirstRunApp = myStorage.getInt("isFirstRunApp");
        if (isFirstRunApp == 0) {
            /** 初始化 word数据库
             *  添加csv单词数据
             *  */
            initword();
            myStorage.storeInt("isFirstRunApp", 1);
        }

    }

    /**
     * 初始化单词数据库
     */
    private static void initword() {
        SQLiteDatabase db = DbUtil.getDatabase();
        AssetManager assetManager = context.getApplicationContext().getAssets();

        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String[] buffer;
        /*"word_id integer primary key," +
                "word text NOT NULL," +
                "phonetic text ," +
                "definition text ," +
                "translation text," +
                "exchange text," +
                "tag integer," +
                "sentence text)";*/
        try {
            inputStream = assetManager.open("target.csv");
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String line = null;
            ContentValues contentValues = new ContentValues();

            int word_id = 0;
            String word = null;
            String phonetic = null;
            String definition = null;
            String translation = null;
            String exchange = null;
            int tag = 0;
            /**
             * 主动开启事务
             * */
            db.beginTransaction();
            while ((line = br.readLine()) != null) {
                buffer = line.split("#", -1);

                assert (buffer.length == 7);
                word_id = Integer.valueOf(buffer[0]);
                word = buffer[1];
                phonetic = buffer[2];
                definition = buffer[3];
                translation = buffer[4];
                exchange = buffer[5];
                tag = Integer.valueOf(buffer[6]);

                contentValues.put("word_id", word_id);
                contentValues.put("word", word);
                contentValues.put("phonetic", phonetic);
                contentValues.put("definition", definition);
                contentValues.put("translation", translation);
                contentValues.put("exchange", exchange);
                contentValues.put("tag", tag);
                db.insert("WORDS", null, contentValues);
                contentValues.clear();
            }
            db.setTransactionSuccessful();

            br.close();
            isr.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
package cn.adminzero.helloword.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.adminzero.helloword.db.DbUtil;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * author : zhaojunchen
 * date   : 2019/11/2015:09
 * desc   : none
 */
public class WordsUtil {


    /**
     * 输入单词的ID，返回单词的对象
     * 查询不到或者失败返回NULL
     */
    public static @Nullable
    Words getWordById(short word_id) {
        SQLiteDatabase db = DbUtil.getDatabase();
        Words words = new Words();
        String word;
        String phonetic;
        String definition;
        String translation;
        String exchange;
        short tag;
        String sentence;
        try {
            Cursor cursor = db.rawQuery("select * from WORDS where word_id = ?", new String[]{String.valueOf(word_id)});
            if (cursor.getCount() == 1 && cursor.moveToFirst()) {
                /** 得到数据*/
                word = cursor.getString(cursor.getColumnIndex("word"));
                phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
                definition = cursor.getString(cursor.getColumnIndex("definition"));
                translation = cursor.getString(cursor.getColumnIndex("translation"));
                exchange = cursor.getString(cursor.getColumnIndex("exchange"));
                tag = cursor.getShort(cursor.getColumnIndex("tag"));
                sentence = cursor.getString(cursor.getColumnIndex("sentence"));
                /** 赋值*/
                words.setWord_id(word_id);
                words.setWord(word);
                words.setPhonetic(phonetic);
                words.setDefinition(definition);
                words.setTranslation(translation);
                words.setExchange(exchange);
                words.setTag(tag);
                words.setSentence(sentence);
            } else {
                Log.d(TAG, "getWordById: " + "查询失败");
                words = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getWordById: " + e.getMessage());
        }
        if (db != null) {
            db.close();
        }
        return words;
    }

    /**
     * 输入单词的ID数组，返回单词的对象的列表
     * 查询不到id对应的对象对应对象列表为NULL
     */
    public static List<Words> getWordArrayByIdArray(short[] word_id) {
        SQLiteDatabase db = DbUtil.getDatabase();
        String word;
        String phonetic;
        String definition;
        String translation;
        String exchange;
        short tag;
        String sentence;
        List<Words> result = new ArrayList<Words>();
        try {
            db.beginTransaction();
            for (int i = 0; i < word_id.length; i++) {
                Cursor cursor = db.rawQuery("select * from WORDS where word_id = ?", new String[]{String.valueOf(word_id)});
                @Nullable Words words = new Words();
                try {
                    if (cursor.getCount() == 0) {
                        throw new Exception("此条ID数据不存在");
                    } else if (cursor.getCount() != 1) {
                        throw new Exception("查询到多条ID数据");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "getWordArrayByIdArray: " + e.getMessage());
                    words = null;
                    result.add(words);
                    continue;
                }
                if (cursor.getCount() == 1 && cursor.moveToFirst()) {
                    /** 得到数据*/
                    word = cursor.getString(cursor.getColumnIndex("word"));
                    phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
                    definition = cursor.getString(cursor.getColumnIndex("definition"));
                    translation = cursor.getString(cursor.getColumnIndex("translation"));
                    exchange = cursor.getString(cursor.getColumnIndex("exchange"));
                    tag = cursor.getShort(cursor.getColumnIndex("tag"));
                    sentence = cursor.getString(cursor.getColumnIndex("sentence"));

                    words.setWord_id(word_id[i]);
                    words.setWord(word);
                    words.setPhonetic(phonetic);
                    words.setDefinition(definition);
                    words.setTranslation(translation);
                    words.setExchange(exchange);
                    words.setTag(tag);
                    words.setSentence(sentence);
                    result.add(words);//
                } else {
                    words = null;
                    cursor = null;
                    result.add(words);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getWordArrayByIdArray: " + e.getMessage());
            Log.d(TAG, "getWordArrayByIdArray: " + "查询失败");
        } finally {
            db.endTransaction();
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    /**
     * 输入单词，返回单词的对象
     * 查询不到单词 对应的对象
     * 返回值为NULL
     */
    public static @Nullable
    Words getWordByWord(String word) {
        SQLiteDatabase db = DbUtil.getDatabase();
        Words words = new Words();
        short word_id;
        String phonetic;
        String definition;
        String translation;
        String exchange;
        short tag;
        String sentence;
        try {
            Cursor cursor = db.rawQuery("select * from WORDS where word = ?", new String[]{word});

            if (cursor.getCount() == 1 && cursor.moveToFirst()) {
                /** 得到数据*/
                word_id = cursor.getShort(cursor.getColumnIndex("word_id"));
                word = cursor.getString(cursor.getColumnIndex("word"));
                phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
                definition = cursor.getString(cursor.getColumnIndex("definition"));
                translation = cursor.getString(cursor.getColumnIndex("translation"));
                exchange = cursor.getString(cursor.getColumnIndex("exchange"));
                tag = cursor.getShort(cursor.getColumnIndex("tag"));
                sentence = cursor.getString(cursor.getColumnIndex("sentence"));

                words.setWord_id(word_id);
                words.setWord(word);
                words.setPhonetic(phonetic);
                words.setDefinition(definition);
                words.setTranslation(translation);
                words.setExchange(exchange);
                words.setTag(tag);
                words.setSentence(sentence);
            } else {
                Log.d(TAG, "getWordById: " + "查询失败");
                words = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getWordByWord: " + e.getMessage());
        }

        if (db != null) {
            db.close();
        }
        return words;
    }

}

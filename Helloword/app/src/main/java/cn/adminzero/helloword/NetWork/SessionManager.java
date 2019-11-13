package cn.adminzero.helloword.NetWork;

import android.util.Log;

import com.example.myapplication.Common.Message;

import org.apache.mina.core.session.IoSession;

public class SessionManager {
    private static SessionManager mInstance = null;
    private IoSession mSession;

    public static SessionManager getInstance() {
        if (mInstance == null) {
            //对当前类对象进行加锁，保证线程安全
            synchronized (SessionManager.class) {
                if (mInstance == null) {
                    mInstance = new SessionManager();
                }
            }
        }
        return mInstance;
    }

    private SessionManager() {
    }

    public void setSeesion(IoSession session) {
        this.mSession = session;
    }

    public boolean writeToServer(Message msg) {
        if (mSession != null) {
            Log.e("tag", "客户端准备发送消息");
            mSession.write(msg);
            return true;
        }
        return false;
    }

    public void closeSession() {
        if (mSession != null) {
            mSession.closeOnFlush();
        }
    }

    public void removeSession() {
        this.mSession = null;
    }
}

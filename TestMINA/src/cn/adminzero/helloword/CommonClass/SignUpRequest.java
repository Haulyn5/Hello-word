package cn.adminzero.helloword.CommonClass;

import java.io.Serializable;

// Author : whl
// Date : 2019/11/17
// 用于存储注册请求的类，由用户在注册时构建并发往服务器

public class SignUpRequest implements Serializable {
    private final long serialVersionUID = 1L;
    String email;
    String password;
    String nickName;

    public SignUpRequest(String email, String password, String nickName)
    {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

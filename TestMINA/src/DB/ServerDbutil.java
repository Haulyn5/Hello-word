package DB;

import cn.adminzero.helloword.CommonClass.SignInRequest;
import cn.adminzero.helloword.CommonClass.SignUpRequest;
import cn.adminzero.helloword.CommonClass.UserNoPassword;

import java.sql.*;

public class ServerDbutil {

    public static UserNoPassword signup(SignUpRequest sur){
        UserNoPassword userNoPassword = new UserNoPassword(-1,sur.getNickName(),sur.getEmail());
        String email=sur.getEmail();
        String user_name=sur.getNickName();
        String password= sur.getPassword();
        int user_id= -1;
        //  userNoPassword.setValid(false);
        Statement stmt = null;
        try {
            stmt = GlobalConn.getConn().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql_qurey= "SELECT user_id,email FROM USER";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql_qurey);
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索,搜索到最后一个字段
                user_id  = rs.getInt("user_id");
                if(email.equals(rs.getString("email"))){
                    //已经存在这个邮箱名了
                    userNoPassword.setValid(false);
                    System.out.println("该邮箱已经已经存在");
                }
            }
            if(user_id==-1){
                user_id=10000;
            }
            else{
                user_id=user_id+1;
                userNoPassword.setUserID(user_id);
            }
            if(userNoPassword.isValid()) {
                //如果合法 就插入该用户数据
               // stmt.execute("insert into user(user_id,user_name,password,email) values(?,?,?,?)", new String[]{user_id + "", user_name, password, email});
                PreparedStatement statement=GlobalConn.getConn().prepareStatement("insert into USER(user_id,user_name,password,email) values(?,?,?,?)");
                statement.setObject(1,user_id);
                statement.setObject(2,user_name);
                statement.setObject(3,password);
                statement.setObject(4,email);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 完成后关闭数据库链接
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNoPassword;
    }

    public static UserNoPassword signin(SignInRequest sur) throws SQLException {
        UserNoPassword userNoPassword = new UserNoPassword(sur.getEmail());
        //TODO：数据库处理注册
        String email=sur.getEmail();
        String password= sur.getPassword();
        int user_id=-1;
        //  userNoPassword.setValid(false);

        PreparedStatement stmt=GlobalConn.getConn().prepareStatement("SELECT * FROM USER WHERE email=? ");
        stmt.setObject(1,email);
       // String sql_qurey= "SELECT * FROM USER WHERE email="+email;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            String password_q="";
            int days_q=0;
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索,搜索到最后一个字段
                if(password.equals(rs.getString("password"))){
                    //密码正确
                   // password=rs.getString("password");
                    user_id= rs.getInt("user_id");
                    password_q= rs.getString("password");
                    userNoPassword.UserNoPassword(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("email"),
                            rs.getInt("isPunch"),
                            rs.getInt("goal"),
                            rs.getInt("days"),
                            rs.getInt("group_id"),
                            rs.getInt("user_level"),
                            rs.getInt("points")
                            );
                    days_q=rs.getInt("days");
                    //TODO 查询上次登录时间距离这次的时间差，看是否需要登陆天数+1
                    {
                        PreparedStatement statement = GlobalConn.getConn().prepareStatement("update USER set days=? where user_id=?");
                        statement.setObject(1, days_q + 1);
                        statement.setObject(2, user_id);
                        statement.execute();
                        userNoPassword.setDays(days_q+1);//返回结果天数也+1
                    }
                }

            }//需要注意 这里rs已经跑完了，rs.next=null
            if(user_id==-1){
                //没有此邮箱地址
                System.out.println("邮箱错误！");
                userNoPassword.setValid(false);
            }
            else if(!password.equals(password_q)){
                //密码错误
                System.out.println("密码错误！");
                userNoPassword.setValid(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNoPassword;
    }
}

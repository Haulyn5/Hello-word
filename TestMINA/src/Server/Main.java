package Server;

import Common.*;

import cn.adminzero.helloword.CommonClass.SignUpRequest;
import cn.adminzero.helloword.CommonClass.UserNoPassword;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.sql.*;


public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static int PORT = CMDDef.PORT;
    public static void main(String[] args) {
        IoAcceptor acceptor = null;
        try {
            acceptor = new NioSocketAcceptor();
            LoggingFilter lf = new LoggingFilter();
            lf.setMessageReceivedLogLevel(LogLevel.DEBUG);
            acceptor.getFilterChain().addLast("logger", lf);
            acceptor.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter(
                            new MessageCodecFactory(new Decoder(), new Encoder())
                    )
            );
            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 50);
            // 绑定逻辑处理器
            acceptor.setHandler(new ServerHandle()); // 添加业务处理
            acceptor.bind(new InetSocketAddress(PORT));
            logger.info("服务端启动成功...     端口号为：" + PORT);
          //initDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void initDataBase() {
        SqlConnection sqlConnection=new SqlConnection();
        sqlConnection.TheSqlConnection();
        Connection conn=sqlConnection.conn;
        SignUpRequest sur= new SignUpRequest("sairen@whu.edu.cn","1234567","chenyuan");
        UserNoPassword userNoPassword = new UserNoPassword(-1,sur.getNickName(),sur.getEmail());
        //TODO：数据库处理注册
        String email=sur.getEmail();
        String user_name=sur.getNickName();
        String password= sur.getPassword();
        int user_id=-1;
        //  userNoPassword.setValid(false);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql_qurey= "SELECT * FROM user";
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
                PreparedStatement statement=conn.prepareStatement("insert into user(user_id,user_name,password,email) values(?,?,?,?)", new String[]{user_id + "", user_name, password, email});
                statement.setObject(1,user_id);
                statement.setObject(2,user_name);
                statement.setObject(3,password);
                statement.setObject(4,email);
                statement.execute();
                //  stmt.execute("insert into user(user_id,user_name,password,email) values(?,?,?,?)", new String[]{user_id + "", user_name, password, email});
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
        sqlConnection.SqlConnectionClose();
    }
}
package Server;

import Common.*;

import DB.GlobalConn;
import DB.ServerDbutil;
import Game.Gamer;
import cn.adminzero.helloword.CommonClass.GroupMember;
import cn.adminzero.helloword.CommonClass.MemberItem;
import cn.adminzero.helloword.CommonClass.UserNoPassword;
import cn.adminzero.helloword.CommonClass.WordsLevel;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static DB.ServerDbutil.*;


public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static int PORT = CMDDef.PORT;
    public static void main(String[] args) {
        //定时器
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
            GlobalConn.initDBConnection();
            //定时器，打卡清空
            Timer();
            Gamer.initGamer();
            //开启随机数产生器的线程
            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            //执行10s，每隔30s更新一次随机数表
            service.scheduleAtFixedRate(Gamer.runnable,10,30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
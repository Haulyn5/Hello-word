package Common;

/**
 * @author: 王翔
 * @date: 2019/11/6-23:44
 * @description: <br>
 *     该类定义了所有的CMD控制命令，限制如下(闭区间)：
 *     ①：CMD的类型为short类型，大小两个字节
 *     ②：从0-0xFFF被预留为常量定义
 *     ③：从0x1000-0x6FFF被预留为Server端使用的指令
 *     ④：从0x7000-0xFFFF被预留位Client使用的指令
 *     为了防止四个人定义冲突，对四个人的区间限制如下：
 *     王翔:   0x0   - 0xFF   0x1000 - 0x1FFF   0x7000 - 0x7FFF
 *     吴昊林: 0x100 - 0x1FF  0x2000 - 0x2FFF   0x8000 - 0x8FFF
 *     赵君臣: 0x200 - 0x2FF  0x3000 - 0x3FFF   0x9000 - 0x9FFF
 *     陈渊:   0x300 - 0x3FF  0x4000 - 0x4FFF   0xA000 - 0xAFFF
 *     剩余区间预留
 * <EndDescription>
 */
public class CMDDef{
    //常量定义区
    public static final int PORT = 3005;

    //服务端身份
    public static final short SERVER_IDENTITY = 0x0;
    //客户端身份
    public static final short CLIENT_IDENTITY = 0x1;

    //通信协议消息传递的数据类型
    //只传递控制指令
    public static final byte PROTOCOL_MESSAGE_NULL = 0x2;
    //传递一个BYTE
    public static final byte PROTOCOL_MESSAGE_BYTE = 0x3;
    //传递一个SHORT
    public static final byte PROTOCOL_MESSAGE_SHORT = 0x4;
    //传递一个INT
    public static final byte PROTOCOL_MESSAGE_INT = 0x5;
    //传递一个object
    public static final byte PROTOCOL_MESSAGE_OBJECT = 0x6;

    //协议标记
    public static final byte PROTOCOL_FLAG = (byte) 0xE8;


    //服务端控制指令定义区
    public static final short TEST_CONNECTION_SERVER = 0x1000;


    //客户端控制指令定义区
    public static final short TEST_CONNECTION = 0x7000;
}

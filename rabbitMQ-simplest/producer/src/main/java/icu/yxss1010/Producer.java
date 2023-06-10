package icu.yxss1010;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置参数
        factory.setHost("10.1.19.110"); // ip 默认值： localhost
        factory.setPort(5672);// 端口 默认值：5672
        factory.setVirtualHost("/");// 虚拟机 默认值：/
        factory.setUsername("guest");// 用户名 默认值：guest
        factory.setPassword("guest");// 密码 默认值：guest
        // 3.创建连接 Connection
        Connection connection = factory.newConnection();
        // 4.创建 Channel
        Channel channel = connection.createChannel();
        // 5.创建队列 Queue
        /*
        * @param：
        *   1：queue：队列名称
        *   2：durable：是否持久化，当 MQ 重启之后，还在
        *   3：exclusive：1）是否独占，只能有一个消费者监听这队列 2）当 Connection 关闭时，是否删除队列
        *   4：autoDelete：是否自动删除，当没有 Consumer 时，自动删除掉
        *   5: arguments：参数
        * */
        channel.queueDeclare("test_queue", true, false, false, null);
        // 6.发送消息
        /*
        * @param:
        *   1：exchange：1）交换机名称 2）简单模式下 设置为：""
        *   2：routingKey：1）路由名称 2）简单模式下需和队列名称保持一致
        *   3：props：配置信息
        *   4：body：发送消息数据
        * */
        String body = "hello world!";
        channel.basicPublish("", "test_queue", null, body.getBytes());
        // 释放资源
        channel.close();
        connection.close();
    }

}

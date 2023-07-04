package icu.yxss1010;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerWorkQueues {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 3、创建连接
        Connection connection = factory.newConnection();
        // 4、创建通道
        Channel channel = connection.createChannel();
        // 5、创建队列
        /*
         * @param：
         *   1：queue：队列名称
         *   2：durable：是否持久化，当 MQ 重启之后，还在
         *   3：exclusive：1）是否独占，只能有一个消费者监听这队列 2）当 Connection 关闭时，是否删除队列
         *   4：autoDelete：是否自动删除，当没有 Consumer 时，自动删除掉
         *   5: arguments：参数
         * */
        channel.queueDeclare("workqueues", true, false, false, null);
        // 6.发送消息
        /*
         * @param:
         *   1：exchange：1）交换机名称 2）简单模式下 设置为：""
         *   2：routingKey：1）路由名称 2）简单模式下需和队列名称保持一致
         *   3：props：配置信息
         *   4：body：发送消息数据
         * */
        for(int i = 0; i < 10; i++){
            String body = i + "=>workqueues";
            channel.basicPublish("", "workqueues", null, body.getBytes() );
        }
        channel.close();
        connection.close();

    }
}
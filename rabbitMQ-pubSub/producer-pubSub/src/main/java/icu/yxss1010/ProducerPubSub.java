package icu.yxss1010;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerPubSub {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、创建连接
        Connection connection = factory.newConnection();
        //4、创建通道
        Channel channel = connection.createChannel();
        //5、创建交换机
        String exchangeName = "pub_sub_exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);
        //6、创建队列
        /*
        * 1、exchange 交换机名称
        * 2、type 交换机类型
        *       DIRECT("direct") 定向
        *       FANOUT("fanout") 广播，将消息发送到每个队列
        *       TOPIC("topic") 通配符的方式
        *       HEADERS("headers") 参数匹配
        * 3、durable 是否持久化
        * 4、autoDelete 自动删除
        * 5、internal 内部使用 一般 false
        * 6、arguments 参数
        * */
        String firstQueue = "first_queue";
        String secondQueue = "second_queue";
        channel.queueDeclare(firstQueue, true, false, false, null);
        channel.queueDeclare(secondQueue, true, false, false, null);
        //7、绑定队列和交换机
        channel.queueBind("first_queue", "pub_sub_exchange", "");
        channel.queueBind("second_queue", "pub_sub_exchange", "");
        //8、发送消息
        String body = "日志信息：hello programmer";
        channel.basicPublish("pub_sub_exchange", "", null, body.getBytes());

        //9、释放资源
        channel.close();
        connection.close();
    }
}
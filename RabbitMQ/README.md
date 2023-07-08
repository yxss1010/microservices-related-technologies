# RabbitMQ的安装

## RabbitMQ官网

- 官网：www.rabbitmq.com
- Get Started -->  Download + installation 

## 使用 docker 镜像

```docker
// 拉取 rabbitmq 镜像
docker pull rabbitmq:3.12-management(版本号可以去 hub.docker.com 查询)
// 查看镜像
docker images
// 创建并启动容器
docker run -id --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
// 进入容器
docker exec -it 容器id /bin/bash
// 开启管理界面(默认开启)
（在容器中运行）rabbitmq-plugins enable rabbitmq_management
```

## 访问管理界面

- 通过浏览器访问：host:15672
- username：guest（默认）
- password：guest（默认）


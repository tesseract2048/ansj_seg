daemonized ansj-seg
==================

基于 ansj-seg，提供 HTTP API 调用方式。

适合代码级别无耦合的后端服务使用。

感谢 ansjsun。

## Run
JAR:

````
org.ansj.AnsjServer <port>
````
MAVEN:

````
mvn exec:java -Dexec.mainClass="org.ansj.AnsjServer" -Dexec.args="<port> [contextPath]"
````
其中 ```<port>``` 是监听的端口号，如 8080。

## Usage
分词:

````
http://127.0.0.1:8080/segment?input=待分词内容
````

## 原作者说明

这是一个ictclas的java实现.基本上重写了所有的数据结构和算法.词典是用的开源版的ictclas所提供的.并且进行了部分的人工优化

内存中中文分词每秒钟大约100万字(速度上已经超越ictclas)

文件读取分词每秒钟大约30万字

准确率能达到96%以上

目前实现了.中文分词. 中文姓名识别 . 用户自定义词典

可以应用到自然语言处理等方面,适用于对分词效果要求高的各种项目.

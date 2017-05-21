# 系统发布文档

## 系统说明

### 运行环境
系统基于java技术实现，依赖于java平台，因此系统需要linux或windows平台上安装JDK，版本为*1.8.0_121*。

系统采用spring-boot方式实现，housedata/com.wula.housedata-1.0-SNAPSHOT.jar包中内置了一个内嵌式tomcat，不需要额外的web容器。启动该项目相对于运行一个jar包。

### 数据库
系统需要使用MySQL作为数据库持久层，MySQL版本为*5.7.17*，最好为该系统创建一个独立的数据库和链接用户名密码。

## 发布方式
将housedata.zip包解压到合适的目录下，解压后目录结构如下所示：

├── com.wula.housedata-1.0-SNAPSHOT.jar
├── config
│   └── application.properties
├── doc
│   └── 发布文档档.md
├── logs
│   ├── springboot.log
│   ├── springboot.log.2017-05-07
│   └── warn.log
└── scripts
    ├── city_data_init.sql
    └── database_init.sql
  
系统目录说明  
- com.wula.housedata-1.0-SNAPSHOT.jar 系统运行jar包
- config/application.properties 系统配置文件，主要是数据库以及日志配置
- 发布文档档.md 是本文档
- logs 目录是日志文件目录
- scripts 数据库脚步
- scripts/database_init.sql 数据库和表创建脚步
- scripts/city_data_init.sql 城市初始化脚步

### 其他发布步骤

1. 在目标数据库创建数据库和相关用户，参考database_init.sql
2. 在目标数据库执行数据初始化脚本city_data_init.sql，初始化城市数据
3. 修改config/application.properties配置文件，主要是修改数据库连接的数据库、用户以及密码
4. 执行启动系统的命令`java -jar com.wula.housedata-1.0-SNAPSHOT.jar`，启动系统
5. 打开浏览器，请求系统页`http://ip:8080/home/index`验证系统正确性。


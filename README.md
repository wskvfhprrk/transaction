# 数据库学习笔记

## 事务的基本要素（ACID）

1. 原子性（Atomicity）
2. 一致性（Consistency）
3. 隔离性（Isolation）
4. 持久性（Durability）

### 数据库隔离级别（mysql）

| 事务隔离级别                 | 脏读 | 不可重复读 | 幻读（自己commit的数据） |
| ---------------------------- | ---- | ---------- | ------------------------ |
| 读未提交（read-uncommitted） | 是   | 是         | 是                       |
| 不可重复读（read-committed） | 否   | 是         | 是                       |
| 可重复读（repeatable-read）  | 否   | 否         | 是                       |
| 串行化（serializable）       | 否   | 否         | 否                       |

- mysql的默认级别是可重复读

#### 实验：

##### 数据库：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
                          `id` int(11) NOT NULL,
                          `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
                          `amount` bigint(255) NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'SuperMan', 100);
INSERT INTO `tb_user` VALUES (2, 'BatMan', 100);

SET FOREIGN_KEY_CHECKS = 1;

```

##### 实验过程sql:

```sql
SELECT @@GLOBAL.tx_isolation, @@tx_isolation;

SELECT * FROM tb_user;
START TRANSACTION;
UPDATE tb_user SET amount = amount + 100 WHERE username ='SuperMan';
UPDATE tb_user SET amount = amount - 100 WHERE username ='BatMan';
COMMIT;
```
**新建数据表时使用innodb引擎**

##### java代码：

```xml
<dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
        </dependency>
    </dependencies>
```



```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-10-13 21:30
 * @Description: jdbc事务示例
 */
public class LocalJdbcTranApplication {
  private static final Logger LOG=LoggerFactory.getLogger(LocalJdbcTranApplication.class);
  public static void main(String[] args) throws SQLException {
    Connection connection= getConnection();
    //关闭自动提交事务
    connection.setAutoCommit(false);
    String sq1="UPDATE tb_user SET amount = amount + 100 WHERE username =?";
    PreparedStatement ps1=connection.prepareStatement(sq1);
    String sq2="UPDATE tb_user SET amount = amount - 100 WHERE username =?";
    PreparedStatement ps2=connection.prepareStatement(sq2);
    ps1.setString(1,"SuperMan");
    ps1.execute();
    //加入错误——模拟程序运行出错
    error();
    ps2.setString(1,"BatMan");
    ps2.execute();
    //提交事务
    connection.commit();
    ps1.close();
    ps2.close();
    connection.close();
  }

  private static void error() throws SQLException {
    throw new SQLException("error");
  }

  private static Connection getConnection() throws SQLException {
    String driver="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/test?useSSL=false";
    String user="root";
    String password="123456";
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      LOG.error(e.getLocalizedMessage());
    }
    return DriverManager.getConnection(url,user,password);
  }
}
```

```java
		String query = "select * from test";
        PreparedStatement querryTest = connection.prepareStatement(query);
        ResultSet resultSet = querryTest.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString(2);
            int age = resultSet.getInt(3);
            System.out.printf("username:%s  age:%d%n", username, age);
        }
```

在查询使用锁表使用 `FOR UPDATE`，如：

```sql
select * from test FOR UPDATE
```

但注意的事，如果表中没有主键时会对全表锁，效率比较低，如果有主键应加上主键，只锁定某一行：

```sql
select * from test where id=1 FOR UPDATE
```

`serializable`隔离与`FOR UPDATE`区别：

当使用`serializable`隔离级别时，相当于把每一个sql加上锁，而`FOR UPDATE`仅针对某个sql进行加锁。

## spring事务管理

- 提供统一的API接口支持不同的资源
- 提供声明式事务管理（AOP）
- 方便与spring集成
- 多个资源事务同步

## spring事务的抽象

- PlatformTransactionManager 事务管理器
- TransactionDefinition 事务定义（事务隔离级别、事务传播机制）
- TransactionStatus 事务管理器（事务运行状态）

## spring事务机制

- 代码方式实现

- 标签方式实现——@Transaction(有两种标签javax和spring两种标签，spring4.2版本之前不支持javax事务签标签)，在spring内部标签方式是使用代理方式实现的，在spring启动时会生成代理类：

  - ​	表面上看是直接直接调用service方法，但实际上是调用者通过以下方式来调用：

    调用者——》APO proxy（代理理的业务方法）——》TransactionAdvisor(调用业务方法开启事务)——》service方法类

##### PlatformTransactionManager的常见实现方式

- DataSourceTransactionManager （常见但不常用的——直接使用jdbc）
- JpaTransactionManager (spring data Jpa)
- JmsTransactionManager (消息中间件事务管理)
- JtaTransactionManager 

## Jpa事务

- 代码和标签方式实现事务管理
- JPA事务管理
- 使用H2数据库（对事务支持）

##### h2数据库依赖

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```



##### 标签事务

```java
 //注解事务
    @Transactional
    public User save(User user) {
        User user1 = userRepository.save(user);
        error();
        return user1;
    }
```

##### 代码方式事务

```java
public User saveTransactional(User user) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
    TransactionStatus ts = platformTransactionManager.getTransaction(transactionDefinition);
    User user1 = userRepository.save(user);
    error();
    platformTransactionManager.commit(ts);
    return user1;
}
private void error() {
    throw new RuntimeException("data error");
}
```

## JMS事务机制（实例）

- spring boot中使用JMS
- spring boot activeMq starter
- 内置可运行的activeMq服务器
- 实现可读写activeMq的事务

##### spring boot activeMq依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

##### spring jms session

- 通过session进行事务管理
- 通过session进行thread-bound
- 事务上下文：在一个线程中存在一个session

##### spring Jms事务的类型

- 原生事务——spring 管理的事务

  JmsTemplate\JmsListener——》通过CF（connectionFactory）创建的session——》MQ

- 外部事务——JmsTransactionManager JtaTransactionManager

```java
 	@Autowired
    JmsTemplate jmsTemplate;
    
    //如果不加@Transactional注解，handle就不能关联到消息事物，引发jmsTemplate.convertAndSend回滚
    @Transactional
    @JmsListener(destination = "customer:msg1:new",containerFactory = "msgFactory")
    public void handle(String msg){
        log.info("listener接收到msg===={}",msg);
        String reply="reply_"+msg;
        jmsTemplate.convertAndSend("customer:msg:reply",reply);
        if(msg.contains("error")){
            error();
        }
    }

    private void error() {
        throw new RuntimeException("data error");
    }
```


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
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'he', '200');
INSERT INTO `test` VALUES ('2', 'li', '0');
```

##### 实验过程sql:

```sql
SELECT * FROM test;
-- 开启事务
START TRANSACTION;
UPDATE test set unit=unit+100 WHERE username='he';
UPDATE test set unit=unit-100 WHERE username='li';
-- 提交事务
COMMIT;
```

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conn {

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String addSql = "UPDATE test set unit=unit+100 WHERE username=?";
        PreparedStatement addpre = connection.prepareStatement(addSql);
        addpre.setString(1, "he");
        addpre.executeUpdate();
        String reduceSql = "UPDATE test set unit=unit-100 WHERE username=?";
        PreparedStatement reducepre = connection.prepareStatement(reduceSql);
        reducepre.setString(1, "li");
        reducepre.executeUpdate();
        connection.commit();
        addpre.close();
        reducepre.close();
        connection.close();
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mds_order";
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
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

当使用`serializable`隔离级别时，会把每一个sql加上锁，而`FOR UPDATE`仅针对某个sql进行加锁。

## spring事务管理


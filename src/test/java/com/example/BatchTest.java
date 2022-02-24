package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/02/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchTest {

    @Autowired
    SimpleJobLauncher simpleJobLauncher;
    @Autowired
    Job importJob;

    @Test
    public void test() throws Exception {
        // 后置参数：使用JobParameters中绑定参数
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        simpleJobLauncher.run(importJob, jobParameters);
    }

    @Test
    public void testPre() {
        //1.导入驱动jar包
        //2.注册驱动(mysql5之后的驱动jar包可以省略注册驱动的步骤)
        //Class.forName("com.mysql.jdbc.Driver");
        //3.获取数据库连接对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        {
            try {
                //"&rewriteBatchedStatements=true",一次插入多条数据，只插入一次
                String url = "jdbc:mysql://10.1.3.24:3306/jiaobk001?" + "&rewriteBatchedStatements=true";
                conn = DriverManager.getConnection(url, "root", "123456");
                //4.定义sql语句
                String sql = "insert into person(name,age, gender) values(?,?,?)";
                //5.获取执行sql的对象PreparedStatement
                pstmt = conn.prepareStatement(sql);
                //6.不断产生sql
                int cnt = 2000000;
                for (int i = 0; i < cnt; i++) {
                    pstmt.setString(1, "name" + i);
                    pstmt.setInt(2, 1000);
                    pstmt.setString(3, "M");
                    pstmt.addBatch();
                }
                //7.往数据库插入一次数据
                pstmt.executeBatch();
                System.out.println("添加" + cnt + "条信息成功！");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //8.释放资源
                //避免空指针异常
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}

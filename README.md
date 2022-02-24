SpringBatch 技术测试项目
[参考连接](https://www.cnblogs.com/jian0110/p/10838744.html)

测试结果：
SpringBatch
1000  1s134ms
5000  3s838ms
10000 6s66ms
50000 21s977ms

[参考链接](https://qinyi.blog.csdn.net/article/details/108191988)
PreparedStatement
rewriteBatchedStatements=true (**必须打开**, 否则就不能用到批量插入的功能，跟单条插入无异)
50000   2.298ms
100000  2.331ms 4.308ms 2.288 seconds
500000  2.274ms
1000000 2.283ms
2000000 2.309ms

测试时间为数据库执行时间
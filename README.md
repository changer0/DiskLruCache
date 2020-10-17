journal 文件解析

```

// journal文件头
libcore.io.DiskLruCache //固定的字符串，标志着我们使用的是DiskLruCache技术
1                       //DiskLruCache的版本号，这个值是恒为1
101                     //应用程序的版本号，在open()方法里传入的版本号
1                       //valueCount

DIRTY key
CLEAN key 0
DIRTY key
CLEAN key 0
DIRTY key
CLEAN key 24
```
参考：https://blog.csdn.net/guolin_blog/article/details/28863651
﻿добавить в .git/info/exclude:
*.log
.settings
.project
.classpath
target
*.cmd
*.bat
.idea
*.iml

и другие расширения файлов,не относящиеся к исходникам напрямую

To get started, please complete the following steps:

1. Download and install a MySQL 5.x database from 
   http://dev.mysql.com/downloads/mysql/5.0.html#downloads.

2. Run "mvn jetty:run-war" and view the application at http://localhost:8080.

3. add in jvm property next values: -Xms256m -Xmx512m -XX:MaxPermSize=256m;

4.all have to use "jQuery" instead "$" for jQuery-function invoking!


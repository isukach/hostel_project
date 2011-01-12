после инициализации git-репозитория, обязательно добавить в .git/info/exclude строки:
*.log
.settings
.project
.classpath
target
.cmd
.bat

и другую свои файлы напрямую не относящиеся к исходникам проекта

To get started, please complete the following steps:

1. Download and install a MySQL 5.x database from 
   http://dev.mysql.com/downloads/mysql/5.0.html#downloads.

2. Run "mvn jetty:run-war" and view the application at http://localhost:8080.


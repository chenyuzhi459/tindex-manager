# tindex-manager程序使用说明

## 一. 发布版使用说明
> 发布版是在生产环境中使用,前后端的程序都整合在后端工程一起使用.  
默认环境中已安装maven,git工具

使用步骤:  
1. 从`git`上克隆本项目
```shell
git clone git@github.com:Datafruit/tindex-manager.git
```
2. 进入项目根目录下,使用`maven`对程序进行编译打包
```shell
cd tindex-manager
mvn clean package
```
3. 从项目根目录下进入`target`目录解压相应的`tar`包
```
cd target
tar -zxvf tindex-manager-1.0-SNAPSHOT-bin.tar.gz 
```

4. 进入解压目录并启动程序脚本
```
cd tindex-manager-1.0-SNAPSHOT
./start.sh start
```
5. 在解压目录下查看日志输出确保程序正常启动
```
tail -200f nohup.out 
```
输入以上命令后,若日志最后一行输出以下信息说明程序正常启动
``` 
INFO [io.sugo.http.ResourcesManager]: start...in 6660
```
6. 开始使用系统   
在浏览器中输入以下url进行访问,并输入账号和密码(目前账号密码均为:`admin`)
```
localhost:6660
```

>注意事项:
1. 在生产环境中利用脚本启动程序前,先确认配置文件中配置信息没有出错,系统配置文件位于以下路径:
```
tindex-manager/src/main/resources/config/system.properties
```
配置文件的内容为:
```
develop.mode=false
http.port=6660
username=admin
password=admin
```

配置项 | 说明
--- | ---
develop.mode | 指定程序状态是开发模式还是生产模式,生产模式下设为`false`
http.port=6660 | 指定浏览器访问的端口,默认为`6660`
username | 登录系统要使用的用户名,默认为`admin`
password | 登录系统要使用的密码,默认为`admin`



## 二. 开发版使用说明
>本系统的开发版是将前后端分开管理和开发的,所以需要分别下载前端的工程与后端的工程.其中前端工程主要是利用`vue`进行页面的渲染和发送`http请求`,而后端工程则主要是利用`jersy`进行`rest api`的转发从druid当中获取数据.


### 后端工程的创建和使用

使用步骤:  
1. 从`git`上克隆后端工程
```shell
git clone git@github.com:Datafruit/tindex-manager.git
```

2. 使用`idea`或`eclipse`等`java`集成开发工具打开后端工程

3. 从`src/main/java/io/sugo/http/ResourcesManager.java`源文件中的`main`方法启动后台程序,若控制台最后一行输出以下信息说明程序正常启动
``` 
INFO [io.sugo.http.ResourcesManager]: start...in 6660
```
>后端工程注意事项:    
1. 在第三步启动前要确保`tindex-manager/src/main/resources/config/system.properties`配置文件中的`develop.mode`属性设置为`true`   


### 前端工程的创建和使用

使用步骤:

1. 安装`nodejs`
>若本地环境中已经装有`8.0`版本以上的`nodejs`,则可忽略此步骤   
此处以linux系统下安装`nodejs`为例

- 下载`nodejs`安装文件   
```
wget https://nodejs.org/download/release/v8.4.0/node-v8.4.0-linux-x64.tar.gz    //若要安装其它版本,可从https://nodejs.org/en/download/releases网站上自行选择
```

- 验证`nodejs`及其自带的`npm`可用
```
tar -zxvf node-v8.4.0-linux-x64.tar.gz   //解压

cd node-v8.4.0-linux-x64.tar.gz/bin   

pwd   //获取目录绝对路径,以便下面建立软链接,此处输出为:/opt/apps/nodejs/node-v8.4.0-linux-x64/bin   

sudo ln -s /opt/apps/nodejs/node-v8.4.0-linux-x64/bin/node /usr/local/bin/node   //为node建立软链接,注意使用绝对路径建立软链接,否则可能会报 链接层数过多的问题

sudo ln -s /opt/apps/nodejs/node-v8.4.0-linux-x64/bin/npm /usr/local/bin/npm    //为npm建立软链接

node -v   //若输出类似v8.4.0的版本信息,说明nodejs安装成功

npm -v   //若输出类似5.3.0的版本信息,说明npm安装成功
```

2. 从`git`上克隆前端项目
```
git clone git@github.com:Datafruit/tindex-manager-front.git
```

3. 使用`npm`对程序进行依赖安装,并启动
```
cd tindex-manager-front
npm install
npm run dev
```
>当输入`npm run dev`后,等待命令行输出以下信息即可视为启动成功:  
`Listening at http://localhost:8080`

4. 使用`vscode`等代码管理工具打开前端工程进行开发

5. 在浏览器中输入`http://localhost:8080`访问前端页面,实时查看前端的开发效果(登录的账号密码参考发布版使用说明中注意事项的说明)

>前端工程注意事项:  
1. 在第三步启动前端工程前,可在`tindex-manager-front/config/index.js`文件下的`dev.port`属性中设置前端的访问端口
2. 在启动前端工程前,先确保后端工程已正常启动





## 三. 开发版整合到发布版使用说明
>当开发版中的前后端工程都开发完成后,应该通过以下步骤整合至发布版中,以便部署

使用步骤:
1. 利用`npm`工具在前端工程根目录中进行资源打包
```
npm run build
```

2. 将前端工程根目录中的`dist`目录`copy`至后端工程根目录中的`src/main/resources`路径下,若该路径原来已有`dist`目录,则将其覆盖

3. 将后端工程根目录下`src/main/resources/config/system.properties`配置文件的`develop.mode`属性设置为`false`

4. 参考发布版和开发版里关于后端工程的使用说明,既可以使用脚本启动,也可以在打开工程的集成工具内启动




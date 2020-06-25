echo "===========进入git项目community目录============="
cd /data/wwwbluer/community


echo "==========git切换分支到v1.0==============="
git checkout v1.0

echo "==================git fetch======================"
git fetch

echo "==================git pull======================"
git pull


echo "===========编译并跳过单元测试===================="
mvn clean package -Dmaven.test.skip=true


echo "============删除旧的ROOT.war==================="
rm /data/wwwbluer/default/ROOT.war

echo "======拷贝编译出来的war包到tomcat下-ROOT.war======="

cp /data/wwwbluer/community/target/community.war /data/wwwbluer/default/ROOT.war

echo "============删除tomcat下旧的ROOT文件夹============="
rm -rf /data/wwwbluer/default/ROOT


echo "====================关闭tomcat====================="
/usr/local/tomcat/bin/shutdown.sh

echo "================sleep 10s========================="
for i in {1..10}
do
	echo $i"s"
	sleep 1s
done


echo "====================启动tomcat====================="
/usr/local/tomcat/bin/startup.sh



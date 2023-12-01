#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed)
#tips=(2 Failed)
# 日志文件的前缀
log1=sys_dbgprint_
#esip的回传路径,该路径是51.192上的路径
uploadPath=/watch/log/esipLog


ifs=$(ifconfig | grep "^e" | awk -F: '{print $1}')

for i in "${ifs[@]}";do
echo -e "${i}\n\t`ifconfig ${i} | awk 'NR==2{print $2}'`"
done


uploadDir=$(sortFilelist "/imsfz/esip/log" $log1 "${tips[0]}")
reUpload $uploadDir $uploadPath








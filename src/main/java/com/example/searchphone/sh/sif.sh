#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed)
#tips=(2 Failed)
# 日志文件的前缀
dir=/imsfz/esip/log
log=(sys_dbgprint_ sys_desk_)
#esip的回传路径,该路径是51.192上的路径
uploadPath=/watch/log/sipLog

str=''
for i in "${tips[@]}" ; do
  str=" $str|$i"
done
str=${str#*|}

for i in "${log[@]}" ; do
    uploadDir=$(sortFilelist $dir $i $str)
    reUpload $uploadDir $uploadPath
done

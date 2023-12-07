#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed ERROR)
# 日志文件的前缀
dir=/imsfz/ivps/log
log=(ivps_)
#回传路径,该路径是51.192上的路径
uploadPath=/watch/log/ivpsLog

str=''
for i in "${tips[@]}" ; do
  str=" $str|$i"
done
str=${str#*|}

for i in "${log[@]}" ; do
    uploadDir=$(sortFilelist $dir $i $str)
    reUpload $uploadDir $uploadPath
done

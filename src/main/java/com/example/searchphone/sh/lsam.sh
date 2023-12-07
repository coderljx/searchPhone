#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed)
# 日志文件的前缀
dir=/imsfz/scmd/log
log=(scmddbgprint_ scmddbgprint_0_ sys_desk)
#回传路径,该路径是51.192上的路径
uploadPath=/watch/log/scmdLog

str=''
for i in "${tips[@]}" ; do
  str=" $str|$i"
done
str=${str#*|}

for i in "${log[@]}" ; do
    uploadDir=$(sortFilelist $dir $i $str)
    reUpload $uploadDir $uploadPath
done

#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed failed)
# 日志文件的前缀
dir=/imsfz/rtds/log
log=(rtds_8_sys_dbgprint_ rtds_8_sys_desk_)
#回传路径,该路径是51.192上的路径
uploadPath=/watch/log/rtdsLog

str=''
for i in "${tips[@]}" ; do
  str=" $str|$i"
done
str=${str#*|}

for i in "${log[@]}" ; do
    uploadDir=$(sortFilelist $dir $i $str)
    reUpload $uploadDir $uploadPath
done

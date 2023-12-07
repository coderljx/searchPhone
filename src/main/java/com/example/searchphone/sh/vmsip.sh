#! /bin/bash

. ./utils.sh

# 需要进行过滤的关键字
tips=(error Failed ERROR)
# 日志文件的前缀
dir=/imsfz/vmsip/log
log=(msip_trace_ omp_trace_ sys_dbgprint_)
#回传路径,该路径是51.192上的路径
uploadPath=/watch/log/vmsipLog

str=''
for i in "${tips[@]}" ; do
  str=" $str|$i"
done
str=${str#*|}

for i in "${log[@]}" ; do
    uploadDir=$(sortFilelist $dir $i $str)
    reUpload $uploadDir $uploadPath
done

#!/bin/bash

. $shRootDIr/utils.sh

# 监控es，nacos，mq，kibana的脚本
# 14,24,34 三台机器

#该机器上需要监控的进程名称
processNames=(Elasticsearch nacos rocketmq  Kibana)

for i in "${processNames[@]}" ; do
  ip=$(getCurrentIp)
  times=$(getTimes)
  dir=$rootDir/$i-$ip-$times.txt
  df -k >> $dir
  ps -ef | grep $i |grep -v grep >> $dir
  reup $i-$ip-$times.txt
  rm -f $dir
done
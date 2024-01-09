#!/bin/bash

. $shRootDIr/utils.sh

# 15-38服务器上脚本

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






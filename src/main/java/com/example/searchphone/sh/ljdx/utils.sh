#!/bin/bash

# ljdx的公共处理脚本,包含所有通用的函数

#存放了所有的ip记录
IPS_FILE="ip"
#所有服务器上都有的一个路径，用于后面存放记录文件
rootDir="/ljdx"
#每台服务器上脚本存在的路径
shRootDIr="/ljdx/watchSh"

#活动当前服务器的ip地址
function getCurrentIp() {
  ips=($(cat "$IPS_FILE"))
  zero=0
  ipAddr=""
  for i_p in "${ips[@]}"; do
    result=$(ip a | grep "inet $i_p")
    #      如果当前的这个ip在遍历的时候找匹配到了，则表示当前循环的遍历ip正确
    if [[ ${#result} -gt $zero ]]; then
      #      ipAddr=${i_p##*.}
      ipAddr=$i_p
      break
    fi
  done
  echo $ipAddr
}

# 汇总到总服务器上
# params1 : 需要传输的文件名
function reup() {
  ftp -vn 192.168.150.11 <<eof
    user dxzl Eastcom@123
    cd /home/dxzl
    lcd $rootDir
    put $1
    bye
eof

#    fileExit /home/dxzl/ljdx/$2
#    mv /home/dxzl/$1 /home/dxzl/ljdx/$2
}

function getTimes() {
  data=$(date "+%Y%m%d-%H")
  echo $data
}

#判断一个文件夹是否存在，如果不存在 则创建该文件夹
function fileExit() {
#  文件夹名称
  dir=$1
  if [ ! -d $dir ]; then
      mkdir $dir
  fi
}
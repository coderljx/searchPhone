#!/bin/bash

# ljdx的公共处理脚本,包含所有通用的函数

#所有服务器上都有的一个路径，用于后面存放记录文件
rootDir="/ljdx"
#每台服务器上脚本存在的路径
shRootDIr="/ljdx/watchSh"
#192.168.150.11上的根路径，所有的日志记录都会记录到这个路径下，再调用归类方法来细分
rootPath="/home/dxzl"
#存放了所有的ip记录
IPS_FILE="/ljdx/watchSh/ip"
# ip的txt文件
ipTxt="/ljdx/watchSh/ip2.txt"

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
    cd $rootPath
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

# 如果后期需要修改整体逻辑 可以从写该方法
function addCheck() {
  echo "null"
}

#真实巡检的逻辑
# params1 : 进程的集合
function doCheck() {
  processNames=$1
  for i in "${processNames[@]}"; do
    ip=$(getCurrentIp)
    times=$(getTimes)
    dir=$rootDir/$i-$ip-$times.txt
    df -k >>$dir
    ps -ef | grep $i | grep -v grep >>$dir
    addCheck
    reup $i-$ip-$times.txt
    rm -f $dir
  done
}

# 归类方法，其他机器的汇总记录在上传到总服务器后，需要使用该方法将不同的日志文件进行归类
function Classified() {
  allIp="$rootPath/$shRootDIr/ip"
  ips=($(cat "$allIp"))
  zero=0
  for i_p in "${ips[@]}"; do
    dataPath=$rootPath/ljdx/$i_p
    fileExit $dataPath
    # count=$(ls *$i_p* | wc -l)

    count=$(find $rootPath -maxdepth 1 -type f -name "*$i_p*" | wc -l)
    # 如果找到了这个ip的日志文件，那么就进行归类
    if [[ $count -gt $zero ]]; then
      echo "查到数据 ---- $i_p"
      files=$(find $rootPath -maxdepth 1 -type f -name "*$i_p*")
      for file in "${files[@]}"; do
        echo $file
        cd $rootPath
        mv -f $file $dataPath
      done
    else
      echo "没有找到---------$i_p"
    fi

  done
}

# 读取ip的txt文件
function getIp() {
  zero=0
  ipAddr=""
  for i_p in $(cat $ipTxt); do
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

#! /bin/bash

#公用的用户和密码，方便传输文件
user="root"
psd="8i9o*I(O"
ip=192.168.51.192
#存放了所有的ip记录
IPS_FILE="allIp"

# 便利该目录下所有的文件，将需要排序的文件进行筛选出来,并写入到对应的文件下面，方便后期传输
# par1 : 需要过滤的文件夹路径
# par2 ：需要过滤的文件名称
# par3 ： 需要过滤的错误关键字
function sortFilelist() {
  filePath=$1
  fileName=$2
  filter=$3

  currentIp=$(getCurrentIp)

  if [ -d $filePath ]; then
    if [ ! -d $filePath/data ]; then
      mkdir $filePath/data
    fi
    currentName=$(ls -t $filePath | grep $fileName | head -n 1)
    newfileName=${currentName%.*}

    creatFileName=$filePath/data/"$newfileName($currentIp).log"
    grep -B 3 -E $filter $filePath/$currentName >$creatFileName

    #    在将错误信息过滤后，将该文件的信息进行返回,方便后面回传
    echo $creatFileName
  else

    echo "$filePath 这不是文件夹，无法操作"
    exit
  fi
}

#回传方法，将生成的文件进行回传到51.192  方便查看
function reUpload() {
  #  防止后续传递不上来
  ssh -o StrictHostKeyChecking=no $ip
  sshpass -p $psd scp -r $1 $user@$ip:$2
}

function getCurrent() {
  current_time=$(date "+%Y%m%d")
  echo $current_time
}

function getCurrentTime() {
  times=$(date "+%Y-%m-%d")
  echo $times
}

#活动当前服务器的ip地址
function getCurrentIp() {
  ips=($(cat "$IPS_FILE"))
  zero=0
  ipAddr=""
  for i_p in "${ips[@]}"; do
    result=$(ip a | grep "inet $i_p")
    #      如果当前的这个ip在遍历的时候找匹配到了，则表示当前循环的遍历ip正确
    if [[ ${#result} -gt $zero ]]; then
      ipAddr=${i_p##*.}
      break
    fi
  done
  echo $ipAddr
}

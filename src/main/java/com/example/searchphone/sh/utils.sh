#! /bin/bash

#公用的用户和密码，方便传输文件
user="root"
psd="8i9o*I(O"
ip=192.168.51.192

# 便利该目录下所有的文件，将需要排序的文件进行筛选出来,并写入到对应的文件下面，方便后期传输
# par1 : 需要过滤的文件夹路径
# par2 ：需要过滤的文件名称
# par3 ： 需要过滤的错误关键字
function sortFilelist() {
  filePath=$1
  fileName=$2
  filter=$3

  time=$(getCurrent)

  if [ -d $filePath ]; then
    if [ ! -d $filePath/data ]; then
      mkdir $filePath/data
    fi
    currentName=$(ls -t $filePath | grep $fileName | head -n 1)
    grep -B 3 $filter $filePath/$currentName > $filePath/data/$currentName

#    在将错误信息过滤后，将该文件的信息进行返回,方便后面回传
    echo $filePath/data/$currentName
  else

    echo "$filePath 这不是文件夹，无法操作"
    exit
  fi
}

#回传方法，将生成的文件进行回传到51.192  方便查看
function reUpload() {
  sshpass -p $psd scp -r $1 $user@$ip:$2
}


function getCurrent() {
  current_time=$(date "+%Y%m%d")
  echo $current_time
}



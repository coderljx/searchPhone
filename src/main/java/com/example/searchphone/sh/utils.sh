#! /bin/bash


# 便利该目录下所有的文件，将需要排序的文件进行筛选出来
function sortFilelist() {
  filePath=$1
  fileName=$2

  time=$(getCurrent)
  
  if [ -d $filePath ]; then
    if [ ! -d $filePath/data ]; then
        mkdir $filePath/data
    fi
     cp $filePath $filePath/data

      currentName=$(ls -A $filePath)
#     对该目录下的文件进行排序，然后遍历该文件是否包含名称
#      currentName=$(ls -lt $filePath | sort)


      for file in $currentName ; do
        echo $file
#        data=$(stat -c %w $file)
#         data=$(ls -lt)
      done


      else
        echo "$filePath 这不是文件夹，无法操作"
        exit
  fi
}

function getCurrent() {
    current_time=$(date "+%Y%m%d")
    echo $current_time
}
#!/bin/bash

# 启动脚本,通过该脚本，调用其他服务器上的脚本执行

. /home/dxzl/ljdx/utils.sh

allip="allip"

ips=($(cat "$allip"))
zero=0
ipAddr=""
for i_p in "${ips[@]}"; do

  sshpass -p '8i9o*I(O' scp -r "/home/dxzl/ljdx/watchSh" root@$i_p:/ljdx

  sshpass -p '8i9o*I(O' ssh root@$i_p 'cd $shRootDIr && bash main.sh'
done

#!/bin/bash

ip="ip"
ips=($(cat "$ip"))


for i_p in "${ips[@]}" ; do
   echo "当前机器-------- $i_p"
   sshpass -p '8i9o*I(O' ssh root@$i_p 'mkdir /ljdx'
   sshpass -p '8i9o*I(O' scp -r /home/dxzl/ljdx/installFtp/installFtp.sh  root@$i_p:/ljdx
   sshpass -p '8i9o*I(O' ssh root@$i_p 'bash /ljdx/installFtp.sh'
done


#安装脚本
#function install() {
#     yum install -y vsftpd
#
#     yum install ftp
#
#     sleep 2
#
#     service vsftpd start
#}
#
#install




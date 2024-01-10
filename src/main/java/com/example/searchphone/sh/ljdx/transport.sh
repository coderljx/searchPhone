#!/bin/bash
# 传输文件

IPS_FILE="/home/dxzl/ljdx/watchSh/ip"
echo '目标主机清单：'
cat $IPS_FILE

# 遍历IP列表并执行命令
ips=($(cat "$IPS_FILE"))

for i_p in "${ips[@]}"; do

    echo 目标机器: $i_p

    # sshpass -p '8i9o*I(O' ssh root@$i_p 'cd /imsfz/esip/log/shell && bash test.sh'

    sshpass -p '8i9o*I(O' scp -r /home/dxzl/ljdx/installFtp/crontab.sh root@$i_p:/ljdx
    sshpass -p '8i9o*I(O' ssh root@$i_p 'bash /ljdx/crontab.sh'

done

echo -e "--------------------------------------------------------------\n执行结束"

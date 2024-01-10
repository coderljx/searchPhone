#!/bin/bash
# 对该服务器新增一个crontab定时任务脚本

# 将原有的crontab导出的路径
exportCrontab="/ljdx/cron.cron"

IPS_FILE="/home/dxzl/ljdx/installFtp/ip"

# 新增crontab
crontab -l >$exportCrontab
# 修改为需要新增的表达式
echo '0 1 * * * bash /ljdx/watchSh/main.sh' >>$exportCrontab
crontab $exportCrontab

rm -f $exportCrontab

crontab -l

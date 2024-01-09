#!/bin/bash
#定义一下要删除的目录
del_url="/imsfz/dump"

if [ ! -d $del_url ]; then
    exit
fi

crontab -l > /imsfz/cron.cron
echo '0 1 * * * bash /imsfz/clear.sh' >> /imsfz/cron.cron
crontab /imsfz/cron.cron

# * * * * * /imsfz/elb/elb_cron >/dev/null 2>&1

#执行删除命令
#find $del_url -name *2021-04* -type f | xargs rm -f
find $del_url -mtime -15 -type f | xargs rm -f

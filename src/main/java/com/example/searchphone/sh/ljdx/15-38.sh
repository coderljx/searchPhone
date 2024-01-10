#!/bin/bash

. /ljdx/watchSh/utils.sh

# 15-36服务器上脚本,不包含redis

processNames=(smas xscf dmca)

doCheck $processNames

#! /bin/bash


function getCurrentTime() {
  times=$(date "+%Y-%m-%d")
  newDate=$(date -d "$times - 1 days" "+%Y-%m-%d")
  echo $newDate
}


getCurrentTime
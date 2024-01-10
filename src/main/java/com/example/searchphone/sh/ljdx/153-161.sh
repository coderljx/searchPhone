#!/bin/bash

. /ljdx/watchSh/utils.sh

processNames=(smas xscf redis mysql)

doCheck $processNames

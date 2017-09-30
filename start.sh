#!/bin/bash -eu

## Initializtion script for druid nodes
## Runs druid nodes as a daemon and pipes logs to log/ directory

usage="Usage: run.sh (start|stop)"

startStop=$1
pid=robot.pid

case $startStop in

  (start)

    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo manager running as process `cat $pid`.  Stop it first.
        exit 1
      fi
    fi


    nohup java -cp ./tindex-manager-1.0-SNAPSHOT.jar:lib/* io.sugo.http.ResourcesManager &
    nodeType_PID=$!
    echo $nodeType_PID > $pid
    echo "Started manager ($nodeType_PID)"
    ;;

  (stop)

    if [ -f $pid ]; then
      TARGET_PID=`cat $pid`
      if kill -0 $TARGET_PID > /dev/null 2>&1; then
        echo Stopping process `cat $pid`...
        kill $TARGET_PID
      else
        echo No robot to stop
      fi
      rm -f $pid
    else
      echo No robot to stop
    fi
    ;;

  (*)
    echo $usage
    exit 1
    ;;
esac



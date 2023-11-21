#!/bin/bash
have_tty=0
# shellcheck disable=SC2006
if [[ "`tty`" != "not a tty" ]]; then
    have_tty=1
fi

if [[ ${have_tty} -eq 1 ]]; then
  RAINBOW="
    $(printf '\033[38;5;196m')
    $(printf '\033[38;5;202m')
    $(printf '\033[38;5;226m')
    $(printf '\033[38;5;082m')
    $(printf '\033[38;5;021m')
    $(printf '\033[38;5;093m')
    $(printf '\033[38;5;163m')
  "
  RED=$(printf '\033[31m')
  GREEN=$(printf '\033[32m')
  YELLOW=$(printf '\033[33m')
  BLUE=$(printf '\033[34m')
  BOLD=$(printf '\033[1m')
  RESET=$(printf '\033[0m')

else
  RAINBOW=""
  RED=""
  GREEN=""
  YELLOW=""
  BLUE=""
  BOLD=""
  RESET=""
fi

#颜色函数
echo_r () {
    # Color red: Error, Failed
    [[ $# -ne 1 ]] && return 1
    # shellcheck disable=SC2059
    printf "[%sCzl%s] %s$1%s\n"  $BLUE $RESET $RED $RESET
}

echo_g () {
    # Color green: Success
    [[ $# -ne 1 ]] && return 1
    # shellcheck disable=SC2059
    printf "[%sCzl%s] %s$1%s\n"  $BLUE $RESET $GREEN $RESET
}

echo_y () {
    # Color yellow: Warning
    [[ $# -ne 1 ]] && return 1
    # shellcheck disable=SC2059
    printf "[%sCzl%s] %s$1%s\n"  $BLUE $RESET $YELLOW $RESET
}

echo_w () {
    # Color yellow: White
    [[ $# -ne 1 ]] && return 1
    # shellcheck disable=SC2059
    printf "[%sCzl%s] %s$1%s\n"  $BLUE $RESET $WHITE $RESET
}

#自动获取到当前目录
PRG="$0"
PRG_DIR=`dirname "$PRG"`
APP_HOME=`cd "$PRG_DIR/.." >/dev/null; pwd`
APP_BASE="$APP_HOME"
APP_CONF="$APP_BASE"/conf
APP_LIB="$APP_BASE"/lib
APP_MAIN_CLASS=com.czl.console.backend.CzlApplication

initial_pid=0
checkPid() {
   #拼接java ps查找进程指令
   java_ps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN_CLASS`
   #指令非空获取进程号
   if [ -n "$java_ps" ]; then
      #print1得到进程号，print2得到类名
      initial_pid=`echo $java_ps | awk '{print $1}'`
   else
      initial_pid=0
   fi
}

start() {
  checkPid
  if [ $initial_pid -ne 0 ]; then
    echo_r "============================================"
    echo_r " Czl system is already started! in (pid=$initial_pid)"
    echo_r "============================================"
    exit 1
  fi

  if [ $initial_pid -eq 0 ]; then
    echo_w "Using APP_HOME:    $APP_HOME"
  fi

  PROPER="${APP_CONF}/application.yml"
  echo_w "Using: properties file:    $PROPER"

  CLASSPATH=$APP_HOME
  for i in "$APP_LIB"/*.jar; do
     CLASSPATH="$CLASSPATH":"$i"
  done
  echo_w "Begin to start Czl system"
  JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m"
  JAVA_CMD="nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH -Dspring.config.location=$PROPER $APP_MAIN_CLASS >/dev/null 2>&1 &"
  eval "$JAVA_CMD"
  checkPid
  if [ $initial_pid -ne 0 ]; then
         echo_w "running Czl system successfully! (pid=$initial_pid)"
  else
         echo_r "$APP_MAIN_CLASS start failed"
  fi

}

stop() {
  checkPid
  if [ $initial_pid -ne 0 ]; then
      echo_w "Begin to stop Czl system (pid=$initial_pid)"
      eval "kill -9 $initial_pid"
      if [ $? -eq 0 ]; then
         echo_w "Stopping Czl system successfully"
      else
         echo_r "Stopping Czl system Failed"
      fi

      checkPid
      if [ $initial_pid -ne 0 ]; then
         stop
      fi
  else
     echo_r "============================================"
     echo_r "Czl system is not running"
     echo_r "============================================"
  fi
}

status() {
   checkPid
   if [ $initial_pid -ne 0 ];  then
      echo_w "Czl system is running! (pid=$initial_pid)"
   else
      echo_r "Czl system is not running"
   fi
}

main() {
 case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
     *)
     echo_r "Usage: $0 包含这些{start|stop|restart|status}指令"
     ;;
 esac
}
main "$@"
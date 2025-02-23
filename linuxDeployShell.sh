APP_DIR=/opt/account-token-service
APP_NAME=account-token-service-0.0.1-SNAPSHOT
echo $PATH
usage() {
    echo "Usage: sh linuxDeployShell.sh [start|stop|deploy|check]"
    exit 1
}

kills(){
    tpid=`ps -ef|gre4p $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    if [[ $tpid ]]; then
        echo 'Kill Process!'
        kill -9 $tpid
    fi
}

start(){
    rm -f $APP_DIR/tpid
    nohup java -jar $APP_DIR/"$APP_NAME".jar > /dev/null 2>&1 &
    echo $! > $APP_DIR/tpid
    echo Start Success!
}

stop(){
        tpid1=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    echo tpid1-$tpid1
        if [[ $tpid1 ]]; then
        echo 'Stop Process...'
        kill -15 $tpid1
    fi
    sleep 5
    tpid2=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
        echo tpid2-$tpid2
    if [[ $tpid2 ]]; then
        echo 'Kill Process!'
        kill -9 $tpid2
    else
        echo 'Stop Success!'
    fi

}

check(){
    tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    if [[ tpid ]]; then
        echo '$APP_NAME is running.'
    else
        echo '$APP_NAME is NOT running.'
    fi
}

deploy() {
    kills
    rm -rf $APP_DIR/"$APPNAME".jar
    cp "$APP_NAME".jar $APP_DIR
}

case "$1" in
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    "kill")
        kills
        ;;
    "deploy")
        deploy
        ;;
    *)
        usage
        ;;
esac

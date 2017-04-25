### BEGIN INIT INFO
  # Provides:   minecraft
  # Required-Start: $local_fs $remote_fs
  # Required-Stop:  $local_fs $remote_fs
  # Should-Start:   $network
  # Should-Stop:    $network
  # Default-Start:  2 3 4 5
  # Default-Stop:   0 1 6
  # Short-Description:    Minecraft server
  # Description:    Starts the minecraft server
  ### END INIT INFO

#Settings
SERVICE="@@SERVICE@@"  
USERNAME="@@USERID@@"  
MCPATH="@@MCPATH@@"
SESSIONID="@@SESSIONID@@"
MEMORY="@@MEMORY@@"

ME=`whoami`  
as_user() {
  if [ "$ME" = "$USERNAME" ] ; then
    bash -c "$1"
  else
    su - "$USERNAME" -c "$1"
  fi
}

mc_start() {  
  if ps ax | grep -v grep | grep $SESSIONID | grep java > /dev/null
  then
    echo "0"
  else
    #echo "$SERVICE was not running... starting."
    cd $MCPATH
    as_user " cd $MCPATH &&  screen -dmS $SESSIONID java -Xmx$MEMORY -Xms$MEMORY -jar $SERVICE nogui"
    sleep 7
    if ps ax | grep -v grep | grep $SESSIONID | grep java > /dev/null
    then
      echo "0"
    else
      echo "-1"
    fi
  fi
}

mc_stop() {  
        if ps ax | grep -v  grep | grep $SESSIONID | grep java > /dev/null
        then
               #echo "$SERVICE is running... stopping."
                as_user "screen -p 0 -S $SESSIONID -X eval 'stuff \"say SERVER SHUTTING DOWN IN 10 SECONDS. Saving map...\"\015'"
                
                as_user "screen -p 0 -S $SESSIONID -X eval 'stuff \"save-all\"\015'"
                
                sleep 10
                as_user "screen -p 0 -S $SESSIONID -X eval 'stuff \"stop\"\015'"
                
                sleep 7
        else
                echo "-1"
        fi
        if ps ax | grep -v grep | grep $SESSIONID | grep java > /dev/null
        then
                echo "-1"
        else
                echo "0"
        fi
}

#Start-Stop here
case "$1" in  
  start)
    mc_start
    ;;
  stop)
    mc_stop
    ;;
  restart)
    mc_stop
    mc_start
    ;;
  status)
    if ps ax | grep -v grep | grep $SESSIONID | grep java > /dev/null
    then
      echo "0"
    else
      echo "-1"
    fi
    ;;

  *)
  echo "Usage: /etc/init.d/minecraft {start|stop|status|restart}"
  exit 1
  ;;
esac

exit 0  

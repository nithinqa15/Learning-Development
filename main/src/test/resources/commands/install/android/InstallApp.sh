#!/bin/bash
#set -e
#usage: $0 gridMachine

USER=$1

APP_INSTALL_DIR="/Users/admin/AppPackages"

VIMN_APP_PACKAGE_PATH=""
AWS_DOWNLOAD=false
VIMN_SHORT_FILE_NAME=""
VIMN_URL=$AWS_APK_URL

VIMN_APP_ID=""
if [[ "$APP_NAME" == "MTV_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.mtvn.mtvPrimeAndroid.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.mtvn.mtvPrimeAndroid"
    fi
elif [[ "$APP_NAME" == "MTV_INTL" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex"
    fi
elif [[ "$APP_NAME" == "CC_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex"
    fi
elif [[ "$APP_NAME" == "CC_INTL" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.comedycentral.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.comedycentral"
    fi
elif [[ "$APP_NAME" == "TVLand" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.tvland.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.tvland"
    fi
elif [[ "$APP_NAME" == "BET_INTL" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.bet.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.bet"
    fi
elif [[ "$APP_NAME" == "BET_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.bet.shows.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.bet"
    fi
elif [[ "$APP_NAME" == "CMT" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.cmt.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.cmt"
    fi
elif [[ "$APP_NAME" == "VH1" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.vh1.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.playplex.vh1"
    fi
elif [[ "$APP_NAME" == "PARAMOUNT" ]]
then
    if [[ "$APP_TYPE" == "QA" ]]
    then
        VIMN_APP_ID="com.vmn.android.spike.qa"
    elif [[ "$APP_TYPE" == "RELEASE" ]]
    then
        VIMN_APP_ID="com.vmn.android.spike"
    fi
fi

if [[ "$PRE_INSTALLED" != "true" ]]
then
    echo "DOWNLOAD THE VIMN APP PACKAGE FROM AWS FOR $USER"
    echo "Downloading VIMN app package from $VIMN_URL"
    FILE_DATE=$(date +"%m%d%Y%H%M%S")
    VIMN_SHORT_FILE_NAME="VIMN_"$BUILD_NUMBER"_"$FILE_DATE".apk"
    VIMN_APP_PACKAGE_PATH=$APP_INSTALL_DIR"/"$VIMN_SHORT_FILE_NAME
    ssh $USER curl -o $VIMN_APP_PACKAGE_PATH -L $VIMN_URL

    echo "VIMN App package saved to: $VIMN_APP_PACKAGE_PATH for $USER"
    
    AWS_DOWNLOAD=true
else
    echo "using a pre-installed app"
fi

if [ "$PRE_INSTALLED" != "true" ]
    then

    echo "INSTALL THE APPLICATION PACKAGE FOR $USER"
    ADB_PATH="/usr/local/Cellar/android-platform-tools/23.0.1/bin/adb"

    OLD_IFS="$IFS"
    IFS=$'\n'

    echo "Getting physical devices from $USER"
    ALL_DEVICES=( $(ssh $USER $ADB_PATH devices | grep device) )
    IFS="$OLD_IFS"

    REAL_DEVICES=()
    for i in "${ALL_DEVICES[@]}"
    do
        if [[ "$i" != *"List of devices attached"* ]]
        then
            REAL_DEVICES+=("$i")
        fi
    done

    REAL_DEVICE_IDS=()
    DEVICE_ID=
    for REAL_DEVICE in "${REAL_DEVICES[@]}"
    do
        DEVICE_ID=$(echo $REAL_DEVICE | cut -d ' ' -f 1)
        REAL_DEVICE_IDS+=("$DEVICE_ID")
    done

    for DEVICE in "${REAL_DEVICE_IDS[@]}"
    do
        echo "Physical device found for $USER with id: $DEVICE"
        INSTALL_COUNT=
        INSTALL_COUNT=$(ssh $USER $ADB_PATH -s $DEVICE shell 'pm list packages -f' | grep -c $VIMN_APP_ID)
        if [ "$INSTALL_COUNT" -gt 0 ]
        then
            echo "uninstalling the existing app package $VIMN_APP_ID for user $USER on device $DEVICE"
            ssh $USER $ADB_PATH -s $DEVICE uninstall $VIMN_APP_ID
        fi
        echo "installing the new app package $VIMN_APP_ID for user $USER on device $DEVICE"
        ssh $USER $ADB_PATH -s $DEVICE install $VIMN_APP_PACKAGE_PATH
        
        INSTALL_COUNT=$(ssh $USER $ADB_PATH -s $DEVICE shell 'pm list packages -f' | grep -c $VIMN_APP_ID)
        if [ "$INSTALL_COUNT" -gt 0 ]
        then
            echo "app package $VIMN_APP_ID for user $USER on device $DEVICE was successfully installed"
        else
            echo "app package $VIMN_APP_ID for user $USER on device $DEVICE failed to install"
            echo "retry installing the new app package $VIMN_APP_ID for user $USER on device $DEVICE"
            ssh $USER $ADB_PATH -s $DEVICE install $VIMN_APP_PACKAGE_PATH
            INSTALL_COUNT=$(ssh $USER $ADB_PATH -s $DEVICE shell 'pm list packages -f' | grep -c $VIMN_APP_ID)
            if [ "$INSTALL_COUNT" -gt 0 ]
            then
                echo "app package $VIMN_APP_ID for user $USER on device $DEVICE was successfully installed on retry"
            else
                echo "app package $VIMN_APP_ID for user $USER on device $DEVICE failed to install on retry"
            fi
            
        fi
    done

    echo "INSTALLATION COMPLETE FOR $USER."

else
    echo "using a pre-installed app"
fi

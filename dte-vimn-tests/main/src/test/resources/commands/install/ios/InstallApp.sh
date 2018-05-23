#!/bin/bash
#set -e
#usage: $0 gridMachine

USER=$1

APP_INSTALL_DIR="/Users/admin/AppPackages"
I_DEVICE_PATH="/usr/local/Cellar/ideviceinstaller/HEAD/bin/ideviceinstaller"

VIMN_APP_PACKAGE_PATH=""
AWS_DOWNLOAD=false
VIMN_SHORT_FILE_NAME=""
VIMN_URL=$AWS_IPA_URL

VIMN_APP_BUNDLE_ID=""
if [[ "$APP_NAME" == "MTV_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.mtvPrimeiPad.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.mtvPrimeiPad"
    fi
elif [[ "$APP_NAME" == "MTV_INTL" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.mtvplayplex.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.playplex.dev"
    fi
elif [[ "$APP_NAME" == "TVLand" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.tvlandplayplex.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.playplex.dev"
    fi
elif [[ "$APP_NAME" == "CC_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.ccnetwork.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.ccnetwork"
    fi
elif [[ "$APP_NAME" == "CC_INTL" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.ccplayplex.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.playplex.dev"
    fi
elif [[ "$APP_NAME" == "BET_INTL" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.betplayplex.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.playplex.dev"
    fi
elif [[ "$APP_NAME" == "BET_DOMESTIC" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.bet.betshows.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.bet.betshows"
elif [[ "$APP_NAME" == "CMT" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.cmtplayplex.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.playplex.dev"
    fi
elif [[ "$APP_NAME" == "PARAMOUNT" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.SPIKE"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.SPIKE"
    fi
elif [[ "$APP_NAME" == "VH1" ]]
then
    if [[ "$APP_TYPE" == "Dev" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.vh1tvtunein.dev"
    elif [[ "$APP_TYPE" == "Release" ]]
    then
        VIMN_APP_BUNDLE_ID="com.mtvn.vh1tvtunein"
    fi
fi

if [[ "$PRE_INSTALLED" != "true" ]]
then
    echo "DOWNLOAD THE VIMN APP PACKAGE FROM AWS FOR $USER"
    echo "Downloading VIMN app package from $VIMN_URL"
    FILE_DATE=$(date +"%m%d%Y%H%M%S")
    VIMN_SHORT_FILE_NAME="VIMN_"$BUILD_NUMBER"_"$FILE_DATE".ipa"
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
    
    OLD_IFS="$IFS"
    IFS=$'\n'

    echo "Getting physical devices from $USER"
    ALL_DEVICES=( $(ssh $USER instruments -s | grep -v Simulator) )
    IFS="$OLD_IFS"

    REAL_DEVICES=()
    for i in "${ALL_DEVICES[@]}"
    do
        if [[ "$i" == *"iPad"* ]] || [[ "$i" == *"iPhone"* ]]
            then
                if [[ "$i" != *"-"* ]]
                then
                    REAL_DEVICES+=("$i")
                fi
            fi
    done

    REAL_DEVICE_IDS=()
    DEVICE_ID=
    for REAL_DEVICE in "${REAL_DEVICES[@]}"
    do
        DEVICE_ID=$(echo $REAL_DEVICE | cut -d "[" -f2 | cut -d "]" -f1)
        REAL_DEVICE_IDS+=("$DEVICE_ID")
    done

    for DEVICE in "${REAL_DEVICE_IDS[@]}"
    do
        echo "Physical device found for $USER with id: $DEVICE"
        VIMN_INSTALL_COUNT=$(ssh $USER $I_DEVICE_PATH -u $DEVICE -l | grep -c $VIMN_APP_BUNDLE_ID)
        if [ "$VIMN_INSTALL_COUNT" -gt 0 ]
        then
            echo "uninstalling the existing app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE"
            ssh $USER $I_DEVICE_PATH -u $DEVICE -U $VIMN_APP_BUNDLE_ID
        fi
        echo "installing the new app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE"
        ssh $USER $I_DEVICE_PATH -u $DEVICE -i $VIMN_APP_PACKAGE_PATH
        
        VIMN_INSTALL_COUNT=$(ssh $USER $I_DEVICE_PATH -u $DEVICE -l | grep -c $VIMN_APP_BUNDLE_ID)
        if [ "$VIMN_INSTALL_COUNT" -gt 0 ]
        then
            echo "app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE was successfully installed"
        else
            echo "app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE failed to install"
            echo "retry installing the new app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE"
            ssh $USER $I_DEVICE_PATH -u $DEVICE -i $VIMN_APP_PACKAGE_PATH
            VIMN_INSTALL_COUNT=$(ssh $USER $I_DEVICE_PATH -u $DEVICE -l | grep -c $VIMN_APP_BUNDLE_ID)
            if [ "$VIMN_INSTALL_COUNT" -gt 0 ]
            then
                echo "app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE was successfully installed on retry"
            else
                echo "app package $VIMN_APP_BUNDLE_ID for user $USER on device $DEVICE failed to install on retry"
            fi
            
        fi
    done

    echo "INSTALLATION COMPLETE FOR $USER."

else
    echo "using a pre-installed app"
fi

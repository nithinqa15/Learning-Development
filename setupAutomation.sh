#!/bin/bash

function log {
  echo "======= $1 ======"
}

function brewInstall {
  log "Checking/Installing homebrew"
  if [ ! -d /usr/local/Cellar/ ]; then
    ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
  else
    log "Homebrew is already installed"
  fi;
}

function javaInstall {
  log "Checking/Installing java"
  if [ ! -d /Library/Java/ ]; then
    brew tap caskroom/cask; brew install brew-cask; brew cask install java
  else
    log "Java is already installed"
  fi;
}

function mavenInstall {
  log "Checking/Installing maven"
  if [ ! -d /usr/local/Cellar/maven ]; then
    brew install maven
  else
    log "Maven is already installed"
  fi;
  
}

function nodeInstall {
  log "Checking/Installing node"
  if [ ! -f /usr/local/bin/node ]; then
    brew install node
  else
    log "Node is already installed"
  fi;
}

function appiumInstall {
  log "Checking/Installing Appium"
  if [ ! -f /usr/local/bin/appium ]; then
    npm install –g appium
  else
    log "Appium is already installed"
  fi;
}

function seleniumInstall {
  log "Checking/Installing Selenium Server"
  if [ ! -d /usr/local/Cellar/selenium-server-standalone ]; then
    brew install selenium-server-standalone
  else
    log "Selenium server is already installed"
  fi;
}

function allureInstall {
  log "Checking/Installing allure"
  if [ ! -f /usr/local/bin/allure ]; then
    brew tap qatools/formulas
    brew install allure-commandline
  else
    log "Allure is already installed"
  fi;
}

function bashGitPrompt {
  log "Checking/Installing Bash-Git-Prompt"
  if [ -f "$(brew --prefix)/opt/bash-git-prompt/share/gitprompt.sh" ]; then
    log "Bash-Git-Prompt is already installed"
  else
    brew install bash-git-prompt
  fi;
}

brewInstall
javaInstall
mavenInstall
nodeInstall
appiumInstall
seleniumInstall
allureInstall
bashGitPrompt

log "iOS Setup"

function ideviceInstall {
  log "Checking/Installing ideviceinstaller"
  if [ ! -f /usr/local/bin/ideviceinstaller ]; then
    brew install --HEAD ideviceinstaller
  else
    log "ideviceinstaller is already installed"
  fi;
}

function libimobiledevice {
  log "Checking/Installing libimobiledevice"
  if [ ! -d /usr/local/Cellar/libimobiledevice ]; then
    brew install --HEAD libimobiledevice
  else
    log "libimobiledevice is already installed"
  fi;
}

function carthageInstall {
  log "Checking/Installing carthage"
  if [ ! -f /usr/local/bin/carthage ]; then
    brew install carthage
  else
    log "carthage is already installed"
  fi;
}

function deployInstall {
  log "Checking/Installing ios-deploy"
  if [ ! -f /usr/local/bin/ios-deploy ]; then
    npm install -g ios-deploy
  else
    log "ios-deploy is already installed"
  fi;
}

function openLockdownPermissions {
  log "Checking/Opening permissions of /var/db/lockdodwn"
  permissions=$(stat /var/db/lockdown | sed "s/drwxrwxrwx//g" )
  if [[ $permissions != "drwxrwxrwx" ]]; then
    log "Run 30 second admin tool and get ready to enter your password"
    sudo chmod 777 /var/db/lockdown
  else
    log "/var/db/lockdown permissions are already 777"
  fi;
}

function webkitInstall {
  log "Checking/Installing ios-webkit-debug-proxy"
  if [ ! -d /usr/local/Cellar/ios-webkit-debug-proxy ]; then
    brew install ios-webkit-debug-proxy
  else
    log "ios-webkit-debug-proxy is already installed"
  fi;
}

ideviceInstall
libimobiledevice
carthageInstall
deployInstall
openLockdownPermissions
webkitInstall

######## Android ########
function androidSDKInstall {
  log "Checking/Installing android-sdk"
  if [ ! -d /usr/local/Cellar/android-sdk ]; then
    brew install android-sdk
  else
    log "android-sdk is already installed"
  fi;  
}

echo "###########  Android Setup  ###########"
androidSDKInstall

echo "Here is a list of all the stuff you just downloaded/installed"
echo "     Homebrew"
echo "https://brew.sh/"
echo ""
echo "     Java "
echo "https://docs.oracle.com/javase/8/docs/api/"
echo ""
echo "     Maven"
echo "https://github.com/github/maven-plugins/blob/master/README.md"
echo ""
echo "     Node"
echo "https://nodejs.org/en/"
echo ""
echo "     Appium"
echo "http://appium.io/slate/en/master/?java#introduction-to-appium"
echo ""
echo "     Selenium"
echo "http://www.seleniumhq.org/"
echo ""
echo "     Allure"
echo "http://allure.qatools.ru/"
echo ""
echo "     iDeviceInstall"
echo "https://github.com/libimobiledevice/ideviceinstaller"
echo ""
echo "     Carthage"
echo "https://github.com/Carthage/Carthage/blob/master/README.md"
echo ""
echo "     DeviceConsole"
echo "https://github.com/rpetrich/deviceconsole"
echo ""
echo "     WebKit"
echo "https://github.com/google/ios-webkit-debug-proxy/blob/master/README.md"
echo ""
echo "     Bash-Git-Prompt"
echo "https://github.com/magicmonty/bash-git-prompt/blob/master/README.md"
echo ""

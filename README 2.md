# PlayPlex Android and iOS Automation
============ 

## 3rd Party Dependencies
1. Run `./setupAutomation.sh` to install all dependencies
2. Install XCode 9 (http://stackoverflow.com/questions/10335747/how-to-download-xcode-4-5-6-7-8-and-get-the-dmg-or-xip-file) 


## Install Android Specific 3rd Party Dependencies
1. Install [IntelliJ](https://www.jetbrains.com/idea/download/#section=mac)
During Installation:
A. Install latest Android API's. `android` Select latest build tools and platform tools
B. Set ANDROID_HOME, ANDROID_HOME/tools, ANDROID_HOME/platforms, and ANDROID_HOME/platform-tools variables as needed.


## Setup iOS device
1. USB device and 'Trust' it for the runtime machine.
2. Provision device by Settings > Developer > Enable Ui Automation (on)
3. Provision webkit on device by Settings > Safari > Advanced > Web Inspector (on)
4. While Device is wired, open XCode > Windows > Devices and enable device for development

## Setup Android Device
1. Enable Developer Options - [Instructions](http://developer.android.com/tools/device.html#developer-device-options).
2. Enable USB Debugging - [Instructions](http://developer.android.com/tools/help/adb.html#Enabling).
3. Wire device and 'Authorize' it for the runtime machine.

## Setup settings.xml for Maven
1. Add your LDAP credentials in <username> and <password> tags in the settings.xml file

EXAMPLE settings.xml
```
<settings xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <!-- used for artifact deployment -->
        <server>
            <id>mtvn-proximity</id>
            <username></username>
            <password></password>
        </server>

        <server>
            <id>nexus.mtvi.com</id>
            <username></username>
            <password></password>
        </server>

    </servers>

    <mirrors>
        <mirror>
            <id>mtvn-proximity</id>
            <mirrorOf>central</mirrorOf>
            <name>Nexus-aggregated repository</name>
            <url>http://nexus.mtvi.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>

    <profiles>
        <profile>
            <id>mtvn-proximity</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
                <repository>
                    <id>mtvn-proximity</id>
                    <url>http://nexus.mtvi.com/nexus/content/groups/public</url>
                </repository>
            </repositories>

        </profile>


    </profiles>

    <pluginGroups>
        <pluginGroup>com.mtvi.plateng.maven</pluginGroup>
    </pluginGroups>
 ```
2. Place settings.xml in your ~/.m2 directory (if this directory doesnt exist: `mkdir ~/.m2`)

## Setup SSH Keys
1. Generate 4096 bit ssh key`ssh-keygen -t rsa -b 4096 -C "your_email@example.com"` replace email with your viacom email
2. Enter filename
3. Enter passphrase (ENTER will omit this step)
4. Navigate to [Stash SSH Settings]()https://stash.mtvi.com/plugins/servlet/ssh/account/keys)
5. Select Add Key
6. Copy your key `pbcopy < ~/.ssh/id_rsa.pub`
7. Paste your key
8. Select Add Key

## iOS 10 WebDriverAgent Setup
1. Clone WebDriverAgent from [Github](https://github.com/facebook/WebDriverAgent) into your ~/Workspace `git clone git@github.com:facebook/WebDriverAgent.git`
2. `cd ~/Workspace/WebDriverAgent`
3. `mkdir -p Resources/WebDriverAgent.bundle`
4. `./Scripts/bootstrap.sh -d`
5. Open WebDriverAgent.xcodeproj in XCode 9
6. XCode > Preferences > Accounts > Sign into your account thats added to our Apple Developer Account
7. Select WebDriverAgentLib Target
8. Select Automatically Manage Signing
9. Change team to Viacom International Inc.
10. Select WebDriverAgentRunner Target
11. Select Automatically Manage Signing
12. Change team to Viacom International Inc.
13. CMD + B to build

## iOS Runtime Instructions
1. Clone the project code base from the source code repository. `git clone ssh://git@stash.mtvi.com/dte/dte-vimn-tests.git`
2. Start up a Selenium Grid Hub server instance - `selenium-server -role hub -timeout 120 -port 4445`
Reference [Instructions](https://code.google.com/p/selenium/wiki/Grid2). for more info
3. Start up an Appium server instance and register it with the Selenium Grid Hub instance.
`appium -a localhost -p 4734 -U deviceID --command-timeout 180 --nodeconfig ~/path/to/node-config.json`
4. If interacting with a webview in the project, Enable iOS webkit debug proxy `ios_webkit_debug_proxy -c UDID:27753 -d` where UDID is the device UDID

## Android Runtime Instructions
1. Clone the project code base from the source code repository. `git clone ssh://git@stash.mtvi.com/dte/dte-vimn-tests.git`
2. Start up a Selenium Grid Hub server instance - `selenium-server -role hub -timeout 120 -port 4446`
Reference [Instructions](https://code.google.com/p/selenium/wiki/Grid2). for more info
3. Start up an Appium server instance and register it with the Selenium Grid Hub instance.
`appium -a localhost -p 4734 -U deviceID --command-timeout 180 --nodeconfig ~/path/to/node-config.json`

Notes:
1. 'nodeIPAddress' must be changed to either 'localhost' if local or the IP address of the running machine if connecting to a remote hub.
2. 'deviceID' must be changed to the unique device ID.
3. An existing json node configuration file must exist in the path specificed above by the '--nodeconfig' property. An example file:

EXAMPLE iOS node-config.json
```
{ 
	"capabilities":
	[
		{	
			"browserName": "iOS",
	  		"deviceName": "3bbb8950cd2dafbbab01fc053b9a27bcb7c10d61",
            "maxInstances": 1,
            "platform": "MAC"
	  	}
	],

	"configuration":
	{
		"autoAcceptAlerts": true,
		"screenshotWaitTimeout": 5,
		"newCommandTimeout": 60,
		"cleanUpCycle": 2000,
	  	"timeout": 180000,
	  	"proxy": "org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
	  	"url": "http://localhost:4734/wd/hub",
	  	"host": "localhost",
	  	"port": 4734,
	  	"maxSession": 1,
	  	"register": true,
	  	"registerCycle": 5000,
	 	"hubPort": 4445,
	  	"hubHost": "localhost"
	}
}

```


EXAMPLE Android node-config.json

```
{ "capabilities":
	[
		{
		  	"browserName": "Android",
		  	"maxInstances": 1,
		  	"platform": "MAC",
			"newCommandTimeout": 180, 
			"takesScreenshot": true, 
			"platformName": "Android",
			"deviceName": "Nexus",
			"androidPackage": "com.vmn.playplex.tvland",
			"appActivity": "com.vmn.playplex.splash.SplashActivity"
		}
	],

	"configuration":
	{
		"cleanUpCycle": 2000,
		"timeout": 180000,
		"proxy": "org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
		"url": "http://localhost:4735/wd/hub",
		"host": "localhost",
		"port": 4735,
		"maxSession": 1,
		"register": true, 
		"registerCycle": 5000,
		"hubPort": 4446,
		"hubHost": "localhost"
	}
}
```


Notes:
1. 'nodeIPAddress' must be changed to either 'localhost' if local or the IP address of the running machine if connecting to a remote hub.
2. 'hubHost' must be changed to either 'localhost' if local or the IP address of the Hub machine if connecting to a remote hub.

## Executing iOS Tests Locally From Command Line

1. Tests can be run by pointing to the project pom.xml file `cd ~/Workspace/dte-vimn-tests/main/` and running `mvn test` with any combination of relevant system parameters. 
Example: `mvn test -Dgroups=Full -DexcludedGroups=Broken -Dsystem.test.mobileos=iOS -Dsystem.test.devicecategory=Phone`
Example: `mvn test -Dgroups=Debug -DexcludedGroups=Broken -Dsystem.test.mobileos=iOS -Dsystem.test.devicecategory=Tablet`
Example: `mvn test -Dgroups=HomeScreen -DexcludedGroups=Broken -Dsystem.test.mobileos=iOS -Dsystem.test.devicecategory=Tablet`


## Executing Android Tests Locally From Command Line

1. Tests can be run by pointing to the project pom.xml file `cd ~/Workspace/dte-vimn-tests/main/` and running `mvn test` with any combination of relevant system parameters. 
Example: `mvn test -Dgroups=Full -DexcludedGroups=Broken -Dsystem.test.mobileos=Android -Dsystem.test.devicecategory=Phone`
Example: `mvn test -Dgroups=Debug -DexcludedGroups=Broken -Dsystem.test.mobileos=Android -Dsystem.test.devicecategory=Tablet`
Example: `mvn test -Dgroups=HomeScreen -DexcludedGroups=Broken -Dsystem.test.mobileos=Android -Dsystem.test.devicecategory=Tablet`


## Web Sites of Note
* [Selenium](http://www.seleniumhq.org/docs/) 
* [WebDriver Wiki](https://code.google.com/p/selenium/wiki/GettingStarted)
* [Appium](http://appium.io/)
* [Appium Java](https://github.com/appium/java-client)
* [TestNG](http://www.tutorialspoint.com/testng/)
* [Maven](http://www.tutorialspoint.com/maven/)

## Filters

### How to get the series titles of series with episodes only and with public episodes
```java 
  // 
    String promoListFeedURL = FeedFactory.getPromoListFeedURL();
    SeriesFilter seriesFilter = new SeriesFilter(promoListFeedURL);
    
    SeriesResults seriesResults = seriesFilter.withEpisodesOnly().withPublicEpisodes().filter();
    seriesResults.getSeriesTitles();
```

### How to get the public episode titles of series with episodes and clips and public episodes
```java   
    String promoListFeedURL = FeedFactory.getPromoListFeedURL();
    SeriesFilter seriesFilter = new SeriesFilter(promoListFeedURL);
    
    EpisodeResults episodeResults = seriesFilter.withEpisodesOnly().withEpisodesFilter().withClips().withPublicEpisodes().filter();
    episodeResults.getEpisodeTitles();
```
### How to get the public clip titles sorted in ascending order by duration in milliseconds of series with clips and with title Nobodies 
```java
    String promoListFeedURL = FeedFactory.getPromoListFeedURL();
    SeriesFilter seriesFilter = new SeriesFilter(promoListFeedURL);
    
    SeriesResults seriesResults = seriesFilter.withClips().withSeriesTitle("Nobodies").filter();
    seriesResults.getClips().orderBy(Order.ASC, Order.clipDurationInMilliSeconds).getClipTitles();
```

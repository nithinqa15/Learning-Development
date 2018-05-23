
buildNumber=371

    devices=( "Phone" "Tablet" )
    for i in "${devices[@]}"
    do
        curl -X POST http://192.168.199.40:8080/job/ComedyCentral-iOS-$i/build \
            --data token=M8fSdfgD7Cx6Ce8jS4aWvVowfv4Slr88pKwFGzJR \
            --data-urlencode json='{"parameter": [{"name":"CodeBranch", "value":"master"}, {"name":"FileName", "value":"https://s3.amazonaws.com/mobileapps-pipeline-ota/ios/trex-platform-ios-enterprise/'$buildNumber'/CCNetwork-'$buildNumber'.ipa"}, {"name":"BuildNumber", "value":"'$buildNumber'"}, {"name":"ParallelTestCount", "value":"1"}, {"name":"ReRunOnFailure", "value":"true"}, {"name":"IncludedTestGroups", "value":"Full"}, {"name":"ExcludedTestGroups", "value":"Broken"}, {"name":"SendChatReport", "value":"true"}, {"name":"SendReportAutoEmails", "value":"true"}, {"name":"SendReportEmailAddress", "value":"Brandon.Clark@viacom.com,aurelie.gaudry@viacom.com,Karl.Catigbe@viacom.com,Karami.Lenhardt@viacom.com"}]}'

        sleep 5s
    done
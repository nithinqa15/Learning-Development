
BUILD_NUMBER=408

curl -X POST http://192.168.199.40:8080/job/TRex-Native-iOS/build \
    --data token=M8fSdfgD7Cx6Ce8jS4aWvVowfv4Slr88pKwFGzJR \
    --data-urlencode json='{"parameter": [{"name":"CODE_BRANCH", "value":"develop"}, {"name":"PRE_INSTALLED", "value":"false"}, {"name":"BUILD_NUMBER", "value":"'$BUILD_NUMBER'"}, {"name":"PARALLEL_TEST_COUNT", "value":"4"}, {"name":"RE_RUN_ON_FAILURE", "value":"true"}, {"name":"INCLUDED_TEST_GROUPS", "value":"Full"}, {"name":"EXCLUDED_TEST_GROUPS", "value":"Broken"}, {"name":"SEND_CHAT_REPORT", "value":"false"}, {"name":"SEND_REPORT_AUTO_EMAILS", "value":"true"}, {"name":"SEND_REPORT_EMAIL_ADDRESS", "value":"Brandon.Clark@viacom.com"}]}'
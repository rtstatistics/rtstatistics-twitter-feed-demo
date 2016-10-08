# rtstatistics.com Twitter Feed Demo

This is the demo code for sending sample Twitter data into rtstatistics.com.

## Build

`./gradlew build`

## Getting credentials

* Get credentials for accessing Twitter API.
    * Visit https://apps.twitter.com .
    * Log in.
    * Create an application.
    * Find consumer key, consumer secret, access token, and access token secret.
* Get credentials for accessing rtstatistics.com API.
    * Visit https://manage.rtstatistics.com . 
    * Log in with your GitHub, Bitbucket, Google/Gmail, or Microsoft/Hotmail account.
    * Create a dataset.
    * Find the ID of the dataset, and one of the send keys of the dataset.

## Run

After a successful build, run the program with credentials passed in as command line arguments, like this:
```
java -Dtwitter4j.oauth.consumerKey=YOUR_KEY \
     -Dtwitter4j.oauth.consumerSecret=YOUR_SECRET \
     -Dtwitter4j.oauth.accessToken=YOUR_TOKEN \
     -Dtwitter4j.oauth.accessTokenSecret=YOUR_TOKEN_SECRET \
     -DtwitterFeedDemoDatasetId=YOUR_DATASET_ID \
     -DtwitterFeedDemoDatasetSendkey=YOUR_DATASET_SEND_KEY \
     -jar build/libs/twitter-feed-demo-0.0.1-SNAPSHOT.jar
```
(Please note that you may need to escape the `$` sign in dataset ID with `\$`.)

Or, first set these environment variables
```
$ export twitter4j.oauth.consumerKey=YOUR_KEY
$ export twitter4j.oauth.consumerSecret=YOUR_SECRET
$ export twitter4j.oauth.accessToken=YOUR_TOKEN
$ export twitter4j.oauth.accessTokenSecret=YOUR_TOKEN_SECRET
$ export TWITTER_FEED_DEMO_DATASET_ID=YOUR_DATASET_ID
$ export TWITTER_FEED_DEMO_DATASET_SENDKEY=YOUR_DATASET_SEND_KEY
```
(Please note that in many systems, `.` is not allowed in the name of environment variables.)

and then just run:
`java -jar build/libs/twitter-feed-demo-0.0.1-SNAPSHOT.jar`

## Stop

Just press `Ctrl-C`

## Change logging

Logging configuration can be changed by modifying `logback.xml`.

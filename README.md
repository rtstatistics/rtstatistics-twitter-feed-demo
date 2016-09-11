# rtstatistics.com Twitter Feed Demo

This is the demo code for sending sample Twitter data into rtstatistics.com.

## Setup

* Get credentials for accessing Twitter API
* Get credentials for accessing rtstatistics.com API 

## Build and run

* Build: `./gradlew build`
* Run: 
```
java -Dtwitter4j.oauth.consumerKey=YOUR_KEY \
     -Dtwitter4j.oauth.consumerSecret=YOUR_SECRET \
     -Dtwitter4j.oauth.accessToken=YOUR_TOKEN \
     -Dtwitter4j.oauth.accessTokenSecret=YOUR_TOKEN_SECRET \
     -Dtwitter.feed.demo.dataset.id=YOUR_DATASET_ID \
     -Dtwitter.feed.demo.dataset.sendkey=YOUR_DATASET_SEND_KEY \
     -jar build/libs/twitter-feed-demo-0.0.1-SNAPSHOT.jar
```
* Stop: `Ctrl-C`
package com.rtstatistics.demo.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterFeedDemo implements StatusListener {
	static private final Logger logger = LoggerFactory.getLogger(TwitterFeedDemo.class);
	
	public static void main(String[] args) {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(new TwitterFeedDemo());
		
		logger.debug("Start to receive sampled feeds from twitter.com");
		twitterStream.sample();
		logger.info("Successfully started");
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				logger.debug("Cleaning up...");
				twitterStream.cleanUp();
				logger.debug("Successfully cleaned up");
			}
		});
	}
	
	@Override
	public void onStatus(Status status) {
		logger.debug("Received: {}", status);
	}

	@Override
	public void onException(Exception ex) {
		logger.error("Happened when receiving from twitter.com", ex);
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
	}

	@Override
	public void onStallWarning(StallWarning warning) {
	}
}

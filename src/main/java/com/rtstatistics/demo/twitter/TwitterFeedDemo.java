package com.rtstatistics.demo.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rtstatistics.client.DataApiClient;

import net.sf.jabb.util.text.DurationFormatter;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterFeedDemo implements StatusListener {
	static private final Logger logger = LoggerFactory.getLogger(TwitterFeedDemo.class);
	
	String datasetId = System.getProperty("twitter.feed.demo.dataset.id", System.getenv("twitter.feed.demo.dataset.id"));
	String datasetSendKey = System.getProperty("twitter.feed.demo.dataset.sendkey", System.getenv("twitter.feed.demo.dataset.sendkey"));
	DataApiClient apiClient = new DataApiClient(datasetId, datasetSendKey, null);
	
	public static void main(String[] args) {
		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(new TwitterFeedDemo());
		
		logger.debug("Start to receive sampled feeds from twitter.com");
		twitterStream.sample();
		logger.info("Successfully started. Press Ctrl-C to stop.");
		
		
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
		//logger.debug("Received: {}", status);
		long startTime = System.currentTimeMillis();
		apiClient.send(status);
		//logger.debug("Sent: {}", status);
		logger.debug("Sent. ID={}, duration: {}", status.getId(), DurationFormatter.formatSince(startTime));
	}

	@Override
	public void onException(Exception ex) {
		logger.error("Error happened when receiving from twitter.com", ex);
		
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

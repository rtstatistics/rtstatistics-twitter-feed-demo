package com.rtstatistics.demo.twitter;

import net.sf.jabb.util.prop.PropertiesLoader;
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
	
	String datasetId = PropertiesLoader.getPropertyFromSystemOrEnv("twitter.feed.demo.dataset.id");
	String datasetSendKey = PropertiesLoader.getPropertyFromSystemOrEnv("twitter.feed.demo.dataset.sendkey");
	DataApiClient apiClient = new DataApiClient(datasetId, datasetSendKey, null, null);
	
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
				logger.info("Successfully cleaned up");
			}
		});
	}
	
	@Override
	public void onStatus(Status status) {
		//logger.debug("Received: {}", status);
		long startTime = System.currentTimeMillis();
		apiClient.send(status);
		if (logger.isDebugEnabled()){
			logger.debug("Sent. Twitter Status ID: {}, round trip: {}", status.getId(), DurationFormatter.formatSince(startTime));
		}
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

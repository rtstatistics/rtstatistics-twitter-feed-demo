package com.rtstatistics.demo.twitter;

import net.sf.jabb.util.parallel.WaitStrategies;
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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TwitterFeedDemo implements StatusListener {
	static private final Logger logger = LoggerFactory.getLogger(TwitterFeedDemo.class);
	
	String datasetId = PropertiesLoader.getPropertyFromSystemOrEnv("twitterFeedDemoDatasetId");
	String datasetSendKey = PropertiesLoader.getPropertyFromSystemOrEnv("twitterFeedDemoDatasetSendkey");
	DataApiClient apiClient = new DataApiClient(datasetId, datasetSendKey, null, null);

	AtomicLong sentCount = new AtomicLong(0);

	public static void main(String[] args) {
		TwitterFeedDemo worker = new TwitterFeedDemo();

		System.out.println("Connect to twitter.com");
		logger.info("Connect to twitter.com");

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(worker);
		
		twitterStream.sample();
		System.out.println("Successfully started. Press Ctrl-C to stop.");
		logger.info("Successfully started receiving sampled feeds from twitter.com");

		AtomicBoolean stop = new AtomicBoolean(false);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				stop.set(true);
				logger.debug("Cleaning up...");
				twitterStream.cleanUp();
				logger.info("Successfully cleaned up");
			}
		});

		while(!stop.get()){
			logger.info("Sent: {}", worker.sentCount.get());
			try {
				WaitStrategies.threadSleepStrategy().await(1000L * 60);
			} catch (InterruptedException e) {
				WaitStrategies.threadSleepStrategy().handleInterruptedException(e);
			}
		}
	}
	
	@Override
	public void onStatus(Status status) {
		//logger.debug("Received: {}", status);
		long startTime = System.currentTimeMillis();
		apiClient.send(status);
		sentCount.incrementAndGet();
		if (logger.isDebugEnabled()){
			logger.debug("Sent. Twitter Status ID: {}, round trip: {}", status.getId(), DurationFormatter.formatSince(startTime));
		}
	}

	@Override
	public void onException(Exception ex) {
		logger.error("Error happened when receiving or processing", ex);
		
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

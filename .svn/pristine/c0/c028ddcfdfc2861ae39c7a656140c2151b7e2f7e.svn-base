package com.yuchengtech.mobile.server.web.utils;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.common.Constants;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class PushAndroid  implements PushInfomation {
	
	protected static final Logger LOG = LoggerFactory
			.getLogger(PushAndroid.class);
	
	private static final String appKey=Constants.appkey;
	private static final String masterSecret=Constants.masterSecret;


	
	/* (non-Javadoc)
	 * @see com.yuchengtech.core.utils.push.PushInfomation#SendAllPush(java.lang.String, java.lang.String)
	 */
	public void SendAllPush(String alert, String title) throws APIConnectionException, APIRequestException  {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		PushAndroid tpushAndroid=new PushAndroid();
		PushPayload payload = tpushAndroid.buildPushObject_android_all_alertWithTitle(alert, title);
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			throw e;
		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			throw e;
		}
	}
	
	private PushPayload buildPushObject_android_alias_alertWithTitle(String ALIAS, String ALERT, String TITLE) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.alias(ALIAS))
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}

	private PushPayload buildPushObject_android_tag_alertWithTitle(String TAG, String ALERT, String TITLE) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.tag(TAG))
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}
	
	private PushPayload buildPushObject_android_all_alertWithTitle(String ALERT, String TITLE) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.all())
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}

	private PushPayload buildPushObject_android_REGISTRATIONID_alertWithTitle(Collection<String> regiester, String ALERT, String TITLE) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.registrationId(regiester))
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}




//	public  PushPayload buildPushObject_android_and_ios() {
//		return PushPayload
//				.newBuilder()
//				.setPlatform(Platform.android_ios())
//				.setAudience(Audience.tag("tag1"))
//				.setNotification(
//						Notification
//								.newBuilder()
//								.setAlert("alert content")
//								.addPlatformNotification(
//										AndroidNotification.newBuilder()
//												.setTitle("Android Title")
//												.build())
//								.addPlatformNotification(
//										IosNotification
//												.newBuilder()
//												.incrBadge(1)
//												.addExtra("extra_key",
//														"extra_value").build())
//								.build()).build();
//	}

//	public  PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//	return PushPayload
//			.newBuilder()
//			.setPlatform(Platform.android_ios())
//			.setAudience(
//					Audience.newBuilder()
//							.addAudienceTarget(
//									AudienceTarget.tag("tag1", "tag2"))
//							.addAudienceTarget(
//									AudienceTarget
//											.alias("alias1", "alias2"))
//							.build())
//			.setMessage(
//					Message.newBuilder().setMsgContent(MSG_CONTENT)
//							.addExtra("from", "JPush").build()).build();
//}
//
//public  PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
//	return PushPayload
//			.newBuilder()
//			.setPlatform(Platform.ios())
//			.setAudience(Audience.tag_and("tag1", "tag_all"))
//			.setNotification(
//					Notification
//							.newBuilder()
//							.addPlatformNotification(
//									IosNotification.newBuilder()
//											.setAlert(ALERT).setBadge(5)
//											.setSound("happy")
//											.addExtra("from", "JPush")
//											.build()).build())
//			.setMessage(Message.content(MSG_CONTENT))
//			.setOptions(
//					Options.newBuilder().setApnsProduction(true).build())
//			.build();
//}
	
	
}
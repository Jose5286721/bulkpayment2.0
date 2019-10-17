package com.global.spm.notificationmanager;

import java.util.Map;

public interface IDriverNotificationManager {

	public void initialize(Map<String, String> parameters);

	public abstract ResponseNotificationManager sendMessage(RequestNotificationManager message);
}

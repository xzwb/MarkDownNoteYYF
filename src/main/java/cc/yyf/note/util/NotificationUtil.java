package cc.yyf.note.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

/**
 * 发送通知
 */
public class NotificationUtil {
    public static void notification(String displayId, String content) {
        // 发送通知
        NotificationGroup firstPluginId = new NotificationGroup(displayId, NotificationDisplayType.BALLOON, true);
        Notification notification = firstPluginId.createNotification(content, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }
}

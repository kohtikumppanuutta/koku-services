package fi.arcusys.koku.common.service;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 1, 2011
 */
public interface KokuSystemNotificationsService {

    void sendNotification(final String subject, final List<String> receipients, final String content);
}

package fi.arcusys.koku.schedule;

import java.net.URL;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import org.jboss.varia.scheduler.Schedulable;

import fi.arcusys.koku.common.soa.KokuScheduledTasksService;
import fi.arcusys.koku.common.soa.KokuScheduledTasksService_Service;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Dec 21, 2011
 */
public class KokuScheduledTasksCaller implements Schedulable {
    private static final Logger log = Logger.getLogger(KokuScheduledTasksCaller.class);

    private String serviceEndpointBaseUrl;

    public KokuScheduledTasksCaller(String serviceEndpointBaseUrl) {
        this.serviceEndpointBaseUrl = serviceEndpointBaseUrl;
        log.info("serviceEndpointBaseUrl: " + serviceEndpointBaseUrl);
    }

    public void perform(Date now, long remainingRepetitions) {
        log.info("perform, now: " + now + ", remainingRepetitions: " + remainingRepetitions 
                + ", serviceEndpointBaseUrl: " + serviceEndpointBaseUrl);
        try {
            final InitialContext ctx = new InitialContext();
            try {
                final String serviceEndpointOverwrite = (String) ctx
                        .lookup("koku/urls/arcusys-common-baseurl");
                if (serviceEndpointOverwrite != null && !serviceEndpointOverwrite.isEmpty()) {
                    log.info("Overwrite arcusys-common-baseurl with " + serviceEndpointOverwrite);
                    serviceEndpointBaseUrl = serviceEndpointOverwrite;
                }
                final KokuScheduledTasksService_Service service = new KokuScheduledTasksService_Service(
                        new URL(serviceEndpointBaseUrl + "/KokuScheduledTasksServiceImpl?wsdl"), 
                        new QName("http://soa.common.koku.arcusys.fi/", "KokuScheduledTasksService"));
                final KokuScheduledTasksService port = service.getKokuScheduledTasksServicePort();
                port.perform();
            } finally {
                ctx.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
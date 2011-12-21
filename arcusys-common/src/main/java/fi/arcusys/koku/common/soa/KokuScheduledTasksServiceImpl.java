package fi.arcusys.koku.common.soa;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.ScheduledTaskExecutor;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 21, 2011
 */
@Stateless
@WebService(serviceName = "KokuScheduledTasksService", portName = "KokuScheduledTasksServicePort", 
      endpointInterface = "fi.arcusys.koku.common.soa.KokuScheduledTasksService",
      targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class KokuScheduledTasksServiceImpl implements KokuScheduledTasksService {

    private static final Logger logger = LoggerFactory.getLogger(KokuScheduledTasksServiceImpl.class);
    
    private String scheduledTaskNames = "arcusys-koku-0.1-SNAPSHOT/MessageServiceFacadeImpl/local-fi.arcusys.koku.common.service.ScheduledTaskExecutor";
    
    private String skipProcessingJndiKey = "koku/arcusys-common/skip-scheduled-tasks";
    
    /**
     * 
     */
    @Override
    public void perform() {
        if (skipProcessing()) {
            logger.info("Execution of scheduled processes is explicitly disabled. Skipping execution.");
            return;
        }
        for (final String taskExecutorJndiName : scheduledTaskNames.split(";")) {
            processSingleTask(taskExecutorJndiName.trim());
        }
    }

    /**
     * @return
     */
    private boolean skipProcessing() {
        try {
            final InitialContext ctx = new InitialContext();
            try {
                final String skipProcessing = (String)ctx.lookup(skipProcessingJndiKey);
                return Boolean.parseBoolean(skipProcessing);
            } finally {
                ctx.close();
            }
        } catch(javax.naming.NamingException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private void processSingleTask(final String taskExecutorJndiName) {
        try {
            final InitialContext ctx = new InitialContext();
            try {
                logger.info("Start scheduled task processing: " + taskExecutorJndiName);
                final ScheduledTaskExecutor taskExecutor = (ScheduledTaskExecutor)ctx.lookup(taskExecutorJndiName);
                final long startTime = System.currentTimeMillis();
                taskExecutor.performTask();
                final long endTime = System.currentTimeMillis();
                logger.info("Task [" + taskExecutor.getTaskDescription() + "], processing finished in: " + (endTime - startTime) + "ms" );
            } finally {
                ctx.close();
            }
        } catch(javax.naming.NamingException e) {
            logger.error(e.getMessage());
        }
    }

}

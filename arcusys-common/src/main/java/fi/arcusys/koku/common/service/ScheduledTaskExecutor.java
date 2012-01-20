package fi.arcusys.koku.common.service;

/**
 * Common DAO interface for services, enabled for scheduled operations processing. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 21, 2011
 */
public interface ScheduledTaskExecutor {
    
    String getTaskDescription();
    
    void performTask();
}

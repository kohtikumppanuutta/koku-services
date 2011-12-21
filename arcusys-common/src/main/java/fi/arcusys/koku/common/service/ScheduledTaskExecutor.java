package fi.arcusys.koku.common.service;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 21, 2011
 */
public interface ScheduledTaskExecutor {
    
    String getTaskDescription();
    
    void performTask();
}

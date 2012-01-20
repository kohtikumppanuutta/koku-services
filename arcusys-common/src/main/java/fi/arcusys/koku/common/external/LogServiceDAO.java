package fi.arcusys.koku.common.external;

/**
 * DAO interface for accessing LOK-component.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public interface LogServiceDAO {
    void logMessage(final LogMessage message);
}

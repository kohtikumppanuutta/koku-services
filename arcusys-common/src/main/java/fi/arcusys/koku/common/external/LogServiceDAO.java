package fi.arcusys.koku.common.external;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public interface LogServiceDAO {
    void logMessage(final LogMessage message);
}

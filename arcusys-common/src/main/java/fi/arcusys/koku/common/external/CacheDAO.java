package fi.arcusys.koku.common.external;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 27, 2011
 */
public interface CacheDAO {
    
    Object get(Class<?> clazz, Object key);
    
    void put(Class<?> clazz, Object key, Object value);
}

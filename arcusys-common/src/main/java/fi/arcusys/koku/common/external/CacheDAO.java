package fi.arcusys.koku.common.external;

/**
 * DAO interface to cache implementation.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 27, 2011
 */
public interface CacheDAO {
    
    Object get(Class<?> clazz, Object key);
    
    void put(Class<?> clazz, Object key, Object value);
}

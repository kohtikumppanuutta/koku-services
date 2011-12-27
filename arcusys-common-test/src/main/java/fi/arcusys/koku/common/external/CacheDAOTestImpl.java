package fi.arcusys.koku.common.external;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 27, 2011
 */
public class CacheDAOTestImpl implements CacheDAO {

    private Map<String, Object> cache = new HashMap<String, Object>();
    
    /**
     * @param clazz
     * @param key
     * @return
     */
    @Override
    public Object get(Class<?> clazz, Object key) {
        return cache.get(getKey(clazz, key));
    }

    private String getKey(Class<?> clazz, Object key) {
        return clazz.getName() + String.valueOf(key);
    }

    /**
     * @param clazz
     * @param key
     * @param value
     */
    @Override
    public void put(Class<?> clazz, Object key, Object value) {
        cache.put(getKey(clazz, key), value);
    }

}

package fi.arcusys.koku.cache;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheManager;
import org.jboss.cache.Fqn;
import org.jboss.ha.framework.server.CacheManagerLocator;

import fi.arcusys.koku.common.external.CacheDAO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 27, 2011
 */
@Stateless
public class CacheDAOImpl implements CacheDAO {

    private Cache localCache;

    @PostConstruct
    public void init() {
        CacheManager cacheManager = CacheManagerLocator.getCacheManagerLocator().getCacheManager( null );
        try {
            localCache = cacheManager.getCache( "local-query", true );
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @param clazz
     * @param key
     * @return
     */
    @Override
    public Object get(Class<?> clazz, Object key) {
        return localCache.get( Fqn.fromString( clazz.getName() ), key );
    }

    /**
     * @param clazz
     * @param key
     * @param value
     */
    @Override
    public void put(Class<?> clazz, Object key, Object value) {
        localCache.put( Fqn.fromString( clazz.getName() ), key, value );
    }

}

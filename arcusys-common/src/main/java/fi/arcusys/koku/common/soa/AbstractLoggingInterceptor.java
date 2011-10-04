package fi.arcusys.koku.common.soa;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import fi.arcusys.koku.common.external.LogMessage;
import fi.arcusys.koku.common.external.LogServiceDAO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public abstract class AbstractLoggingInterceptor {

    @EJB
    private LogServiceDAO logDao;
    
    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
        final Object result = ctx.proceed();
        if (isLogNeeded(ctx)) {
            logDao.logMessage(createLogMessage(ctx, result));
        }
        return result;
    }

    protected abstract boolean isLogNeeded(InvocationContext ctx);

    protected abstract LogMessage createLogMessage(final InvocationContext ctx, final Object result);

    protected String getParameterAsString(final InvocationContext ctx, final int index) {
        return String.valueOf(ctx.getParameters()[index]);
    }

    protected <T> T getGenericParameter(final InvocationContext ctx, final int index) {
        return (T)ctx.getParameters()[index];
    }
}
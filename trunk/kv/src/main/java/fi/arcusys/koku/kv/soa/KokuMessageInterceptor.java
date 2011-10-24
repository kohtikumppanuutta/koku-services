package fi.arcusys.koku.kv.soa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.interceptor.InvocationContext;

import fi.arcusys.koku.common.external.LogMessage;
import fi.arcusys.koku.common.external.LoggedOperation;
import fi.arcusys.koku.common.external.SystemArea;
import fi.arcusys.koku.common.soa.AbstractLoggingInterceptor;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Oct 4, 2011
 */
public class KokuMessageInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "sendMessage",
            "receiveMessage",
            "receiveNewMessage",
            "archiveMessages",
            "deleteMessages"
            ));
    
    /**
     * @param ctx
     * @return
     */
    @Override
    protected boolean isLogNeeded(InvocationContext ctx) {
        return operations.contains(ctx.getMethod().getName());
    }

    /**
     * @param ctx
     * @return
     */
    @Override
    protected LogMessage createLogMessage(InvocationContext ctx, final Object result) {
        final LogMessage message = new LogMessage();
        message.setSystemArea(SystemArea.KV);
        message.setDataItemType("kv.message");
        final String methodName = ctx.getMethod().getName();
        if ("archiveMessages".equals(methodName)) {
            message.setOperation(LoggedOperation.Move);
            message.setDataItemId(getParameterAsString(ctx, 0));
        } else if ("deleteMessages".equals(methodName)) {
            message.setOperation(LoggedOperation.Delete);
            message.setDataItemId(getParameterAsString(ctx, 0));
        } else {
            message.setOperation(LoggedOperation.Create);
            message.setUserPic(getParameterAsString(ctx, 0));
            if (("sendMessage".equals(methodName) ||
                 "receiveNewMessage".equals(methodName)) &&
                result instanceof Long ) {
                message.setDataItemId(String.valueOf(result));
            } else if ("receiveMessage".equals(methodName)) {
                message.setDataItemId(getParameterAsString(ctx, 1));
            }
        }   
        message.setMessage(methodName);

        return message;
    }
}

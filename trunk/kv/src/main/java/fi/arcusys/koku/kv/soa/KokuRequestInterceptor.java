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
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public class KokuRequestInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "sendRequest",
            "replyToRequest",
            "createRequestTemplate",
            "updateRequestTemplate",
            "sendRequestWithTemplate"
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
        message.setDataItemType("kv.request");
        final String methodName = ctx.getMethod().getName();
        if ("updateRequestTemplate".equals(methodName)) {
            message.setOperation(LoggedOperation.Update);
        } else {
            message.setOperation(LoggedOperation.Create);
        }
        if ("sendRequest".equals(methodName) ||
                "sendRequestWithTemplate".equals(methodName)) {
            if (result instanceof Long) {
                message.setDataItemId(String.valueOf(result));
            }
        } else if ("replyToRequest".equals(methodName) ||
                "createRequestTemplate".equals(methodName) ||
                "updateRequestTemplate".equals(methodName)) {
            message.setDataItemId(getParameterAsString(ctx, 1));
        }  
        message.setUserPic(getParameterAsString(ctx, 0));
        message.setMessage(methodName);

        return message;
    }
}

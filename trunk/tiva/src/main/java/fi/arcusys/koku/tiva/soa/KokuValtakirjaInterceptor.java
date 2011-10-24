package fi.arcusys.koku.tiva.soa;

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
public class KokuValtakirjaInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "createAuthorization",
            "approveAuthorization",
            "declineAuthorization",
            "updateAuthorization",
            "revokeAuthorization",
            "revokeOwnAuthorization"
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
        message.setSystemArea(SystemArea.TIVA);
        message.setDataItemType("tiva.valtakirja");
        final String methodName = ctx.getMethod().getName();
        if ("createAuthorization".equals(methodName)) {
            message.setOperation(LoggedOperation.Create);
            message.setUserPic(getParameterAsString(ctx, 3));
            message.setCustomerPic(getParameterAsString(ctx, 5));
            if (result instanceof Long) {
                message.setDataItemId(String.valueOf(result));
            }
        } else if ("approveAuthorization".equals(methodName) ||
                "declineAuthorization".equals(methodName) ||
                "updateAuthorization".equals(methodName) ||
                "revokeAuthorization".equals(methodName) ||
                "revokeOwnAuthorization".equals(methodName)) {
            message.setOperation(LoggedOperation.Update);
            message.setUserPic(getParameterAsString(ctx, 1));
            message.setDataItemId(getParameterAsString(ctx, 0));
        } 
        message.setMessage(methodName);

        return message;
    }

}

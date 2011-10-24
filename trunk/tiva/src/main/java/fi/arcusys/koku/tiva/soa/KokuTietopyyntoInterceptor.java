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
public class KokuTietopyyntoInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "createInformationRequest",
            "approveRequest",
            "declineRequest"
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
        message.setDataItemType("tiva.tietopyynto");
        final String methodName = ctx.getMethod().getName();
        if ("createInformationRequest".equals(methodName)) {
            final InformationRequestTO request = getGenericParameter(ctx, 0);
            message.setOperation(LoggedOperation.Create);
            message.setUserPic(request.getSenderUid());
            message.setCustomerPic(request.getTargetPersonUid());
            if (result instanceof Long) {
                message.setDataItemId(String.valueOf(result));
            }
        } else if ("approveRequest".equals(methodName)) {
            final InformationRequestReplyTO reply = getGenericParameter(ctx, 0);
            message.setOperation(LoggedOperation.Update);
            message.setDataItemId(String.valueOf(reply.getRequestId()));
        } else if ("declineRequest".equals(methodName)) {
            message.setOperation(LoggedOperation.Update);
            message.setDataItemId(getParameterAsString(ctx, 0));
        } 
        message.setMessage(methodName);

        return message;
    }

}

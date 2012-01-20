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
 * Interceptor for performing logging about business valuable events in TIVA-Suostumus
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public class KokuSuostumusInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "createConsentTemplate",
            "requestForConsent",
            "writeConsentOnBehalf",
            "giveConsent",
            "declineConsent",
            "updateConsent",
            "revokeConsent",
            "revokeOwnConsent"
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
        message.setDataItemType("tiva.suostumus");
        final String methodName = ctx.getMethod().getName();
        if ("createConsentTemplate".equals(methodName)) {
            final ConsentTemplateTO consentTemplate = getGenericParameter(ctx, 0);
            message.setOperation(LoggedOperation.Create);
            message.setUserPic(consentTemplate.getCreatorUid());
            if (result instanceof Long) {
                message.setDataItemId(String.valueOf(result));
            }
        } else if ("requestForConsent".equals(methodName) ||
                "writeConsentOnBehalf".equals(methodName)) {
            message.setOperation(LoggedOperation.Create);
            message.setUserPic(getParameterAsString(ctx, 1));
            if (result instanceof Long) {
                message.setDataItemId(String.valueOf(result));
            }
            if ("requestForConsent".equals(methodName)) {
                message.setCustomerPic(getParameterAsString(ctx, 2));
            } else {
                message.setCustomerPic(getParameterAsString(ctx, 3));
            }
        } else if ("giveConsent".equals(methodName) || 
                "declineConsent".equals(methodName) || 
                "updateConsent".equals(methodName) || 
                "revokeConsent".equals(methodName) || 
                "revokeOwnConsent".equals(methodName)) {
            message.setOperation(LoggedOperation.Update);
            message.setDataItemId(getParameterAsString(ctx, 0));
            message.setUserPic(getParameterAsString(ctx, 1));
        }  
        message.setMessage(methodName);

        return message;
    }

}

package fi.arcusys.koku.av.soa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.interceptor.InvocationContext;

import fi.arcusys.koku.common.external.LogMessage;
import fi.arcusys.koku.common.external.LoggedOperation;
import fi.arcusys.koku.common.external.SystemArea;
import fi.arcusys.koku.common.soa.AbstractLoggingInterceptor;

/**
 * Interceptor for performing logging about business valuable events in AV.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public class KokuAppointmentInterceptor extends AbstractLoggingInterceptor {

    private static Set<String> operations = new HashSet<String>(Arrays.asList(
            "storeAppointment",
            "approveAppointment",
            "declineAppointment",
            "cancelAppointment",
            "cancelRespondedAppointment"
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
        message.setSystemArea(SystemArea.AV);
        message.setDataItemType("av.appointment");
        final String methodName = ctx.getMethod().getName();
        if ("storeAppointment".equals(methodName)) {
            final AppointmentForEditTO appointment = getGenericParameter(ctx, 0);
            if (appointment.getAppointmentId() > 0) {
                message.setOperation(LoggedOperation.Update);
                message.setDataItemId(String.valueOf(appointment.getAppointmentId()));
            } else {
                message.setOperation(LoggedOperation.Create);
                if (result instanceof Long) {
                    message.setDataItemId(String.valueOf(result));
                }
            }
            message.setUserPic(appointment.getSender());
            if (appointment.getReceipients() != null && appointment.getReceipients().size() == 1) {
                message.setCustomerPic(appointment.getReceipients().get(0).getTargetPerson());
            } 
        } else if ("approveAppointment".equals(methodName) ||
                "declineAppointment".equals(methodName)) {
            message.setOperation(LoggedOperation.Update);
            message.setDataItemId(getParameterAsString(ctx, 2));
            message.setUserPic(getParameterAsString(ctx, 1));
            message.setCustomerPic(getParameterAsString(ctx, 0));
        } else if ("cancelAppointment".equals(methodName)) {
            message.setOperation(LoggedOperation.Delete);
            message.setDataItemId(getParameterAsString(ctx, 0));
        } else if ("cancelRespondedAppointment".equals(methodName)) {
            message.setOperation(LoggedOperation.Delete);
            message.setDataItemId(getParameterAsString(ctx, 0));
            message.setUserPic(getParameterAsString(ctx, 2));
            message.setCustomerPic(getParameterAsString(ctx, 1));
        } 
        message.setMessage(methodName);

        return message;
    }

}

package fi.arcusys.koku.common.external;


/**
 * Entity for passing log events through LogServiceDAO to LOK component.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
public class LogMessage {
    private SystemArea systemArea;
    private String userPic = "";
    private String customerPic = "";
    private LoggedOperation operation;
    private String dataItemType = "";
    private String dataItemId = "";
    private String message = "";
    /**
     * @return the systemArea
     */
    public SystemArea getSystemArea() {
        return systemArea;
    }
    /**
     * @param systemArea the systemArea to set
     */
    public void setSystemArea(SystemArea systemArea) {
        this.systemArea = systemArea;
    }
    /**
     * @return the userPic
     */
    public String getUserPic() {
        return userPic;
    }
    /**
     * @param userPic the userPic to set
     */
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
    /**
     * @return the customerPic
     */
    public String getCustomerPic() {
        return customerPic;
    }
    /**
     * @param customerPic the customerPic to set
     */
    public void setCustomerPic(String customerPic) {
        this.customerPic = customerPic;
    }
    /**
     * @return the operation
     */
    public LoggedOperation getOperation() {
        return operation;
    }
    /**
     * @param operation the operation to set
     */
    public void setOperation(LoggedOperation operation) {
        this.operation = operation;
    }
    /**
     * @return the dataItemType
     */
    public String getDataItemType() {
        return dataItemType;
    }
    /**
     * @param dataItemType the dataItemType to set
     */
    public void setDataItemType(String dataItemType) {
        this.dataItemType = dataItemType;
    }
    /**
     * @return the dataItemId
     */
    public String getDataItemId() {
        return dataItemId;
    }
    /**
     * @param dataItemId the dataItemId to set
     */
    public void setDataItemId(String dataItemId) {
        this.dataItemId = dataItemId;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}

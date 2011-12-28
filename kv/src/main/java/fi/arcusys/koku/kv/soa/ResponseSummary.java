package fi.arcusys.koku.kv.soa;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 6, 2011
 */
public class ResponseSummary {
    private RequestShortSummary request;
    private Long responseId;
    private String replierUid;
    private UserInfo replierUserInfo;
    
    /**
     * @return the replierUserInfo
     */
    public UserInfo getReplierUserInfo() {
        return replierUserInfo;
    }
    /**
     * @param replierUserInfo the replierUserInfo to set
     */
    public void setReplierUserInfo(UserInfo replierUserInfo) {
        this.replierUserInfo = replierUserInfo;
    }
    /**
     * @return the request
     */
    public RequestShortSummary getRequest() {
        return request;
    }
    /**
     * @param request the request to set
     */
    public void setRequest(RequestShortSummary request) {
        this.request = request;
    }
    /**
     * @return the responseId
     */
    public Long getResponseId() {
        return responseId;
    }
    /**
     * @param responseId the responseId to set
     */
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
    /**
     * @return the replierUid
     */
    public String getReplierUid() {
        return replierUid;
    }
    /**
     * @param replierUid the replierUid to set
     */
    public void setReplierUid(String replierUid) {
        this.replierUid = replierUid;
    }
}

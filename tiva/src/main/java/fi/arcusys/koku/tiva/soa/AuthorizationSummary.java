package fi.arcusys.koku.tiva.soa;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
public class AuthorizationSummary extends AuthorizationShortSummary {
    private XMLGregorianCalendar createdAt;
    private XMLGregorianCalendar replyTill;
    private XMLGregorianCalendar givenAt;
    private AuthorizationCreateType type;
    /**
     * @return the createdAt
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }
    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(XMLGregorianCalendar createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * @return the replyTill
     */
    public XMLGregorianCalendar getReplyTill() {
        return replyTill;
    }
    /**
     * @param replyTill the replyTill to set
     */
    public void setReplyTill(XMLGregorianCalendar replyTill) {
        this.replyTill = replyTill;
    }
    /**
     * @return the givenAt
     */
    public XMLGregorianCalendar getGivenAt() {
        return givenAt;
    }
    /**
     * @param givenAt the givenAt to set
     */
    public void setGivenAt(XMLGregorianCalendar givenAt) {
        this.givenAt = givenAt;
    }
    /**
     * @return the type
     */
    public AuthorizationCreateType getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(AuthorizationCreateType type) {
        this.type = type;
    }
    
    
}

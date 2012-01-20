package fi.arcusys.koku.common.service.datamodel;

/**
 * Status of reply to authorization request in TIVA-Valtakirja function area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
public enum AuthorizationReplyStatus {
    Approved, Declined, Revoked;
}

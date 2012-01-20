package fi.arcusys.koku.tiva.soa;

/**
 * Current status of the authorization
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
public enum AuthorizationStatus {
    Open, Valid, Expired, Revoked, Declined;
}

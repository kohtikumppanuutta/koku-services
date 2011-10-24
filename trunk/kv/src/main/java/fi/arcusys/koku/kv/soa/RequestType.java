package fi.arcusys.koku.kv.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public enum RequestType {
	Valid, // before endDate and responded already
	Outdated; // after endDate
}

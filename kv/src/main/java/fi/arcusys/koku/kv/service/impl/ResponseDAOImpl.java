package fi.arcusys.koku.kv.service.impl;

import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.kv.service.ResponseDAO;
import fi.arcusys.koku.kv.service.datamodel.Response;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Stateless
public class ResponseDAOImpl extends AbstractEntityDAOImpl<Response> implements ResponseDAO {
	public ResponseDAOImpl() {
		super(Response.class);
	}
}

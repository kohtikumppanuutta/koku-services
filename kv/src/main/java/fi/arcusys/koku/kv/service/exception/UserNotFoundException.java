package fi.arcusys.koku.kv.service.exception;

import javax.ejb.ApplicationException;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@ApplicationException(rollback = true)
public class UserNotFoundException extends RuntimeException {

	/**
	 * @param string
	 */
	public UserNotFoundException(final String msg) {
		super(msg);
	}
}

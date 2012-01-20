package fi.arcusys.koku.common.service.exception;

import javax.ejb.ApplicationException;

/**
 * Exception for reporting error in case of incorrect/absent user requested.
 * 
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

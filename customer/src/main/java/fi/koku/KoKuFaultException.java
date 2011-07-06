package fi.koku;


/**
 * Fault exception base class.
 * 
 * @author aspluma
 */
public class KoKuFaultException extends Exception {
  private static final long serialVersionUID = 1L;

  public KoKuFaultException(String message) {
    super(message);
  }

  public KoKuFaultException(String message, Throwable cause) {
    super(message, cause);
  }

}

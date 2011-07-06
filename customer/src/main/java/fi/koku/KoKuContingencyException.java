package fi.koku;

/**
 * Contingency exception base class.
 * 
 * @author aspluma
 */
public class KoKuContingencyException extends Exception {
  private static final long serialVersionUID = 1L;

  public KoKuContingencyException(String message, Throwable cause) {
    super(message, cause);
  }

  public KoKuContingencyException(String message) {
    super(message);
  }
}

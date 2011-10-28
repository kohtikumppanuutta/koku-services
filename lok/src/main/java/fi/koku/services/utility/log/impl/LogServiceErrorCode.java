package fi.koku.services.utility.log.impl;

/**
 * Error codes for Lok Service
 * 
 * @author makinsu
 *
 */
public enum LogServiceErrorCode {

  LOG_ERROR_PARSING(2000, "Error in parsing."),
  LOG_ERROR_INVALID_LOGTYPE(2010, "Invalid logtype"),
  LOG_ERROR_MISSING_TIMESTAMP(2100, "Timestamp missing when writing to log"),
  LOG_ERROR_MISSING_USERPIC(2110, "User pic missing when writing to log"),
  LOG_ERROR_MISSING_OPERATION(2120, "Operation missing when writing to log"),
  LOG_ERROR_MISSING_DATAITEMID(2130, "Data item id missing when writing to log"),
  LOG_ERROR_MISSING_DATAITEMTYPE(2140, "Date item type missing when writing to log"),
  LOG_ERROR_MISSING_CLIENTSYSTEMID(2150, "Client system id missing when writing to log"),
  LOG_ERROR_ARCHIVE_LOG_NOT_AVAILABLE(2160, "Archive log not available"),
  LOG_ERROR_ADMIN_LOG(2170, "Error writing to Admin log"),
  LOG_ERROR_INVALID_QUERY_CRITERIA(2170, "Invalid query criteria"),
  LOG_ERROR_INVALID_ARCHIVE_DATE(2180, "Invalid archive date");
  
  private final int value;

  private final String description;
  
  LogServiceErrorCode(int value, String description) {
    this.value = value;
    this.description = description;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}
  
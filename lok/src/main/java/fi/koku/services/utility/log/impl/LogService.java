/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.utility.log.impl;

import java.util.List;

import javax.ejb.Local;

import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;

/**
 * Service API for log service. 
 * 
 * @author makinsu
 */
@Local
public interface LogService {

  void writeAdminLogEntry(AdminLogEntry entry);

  void writeNormalLogEntry(LogEntry entry);

  void writeAdminLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType);

  void writeNormalLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType);

  List<LogEntry> queryNormalLog(LogQueryCriteria criteria);

  List<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria);

  ArchivalResultsType archiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType);
}
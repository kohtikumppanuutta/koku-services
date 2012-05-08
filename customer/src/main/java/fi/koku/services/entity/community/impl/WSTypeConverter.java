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
package fi.koku.services.entity.community.impl;

/**
 * Converter interface for converting between two different types.
 * 
 * @author aspluma
 *
 * @param <T> WS type
 * @param <U> POJO type
 */
interface WSTypeConverter<T, U> {
  
  abstract U fromWsType(T from);
  
  abstract T toWsType(U from);
  
}

/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
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

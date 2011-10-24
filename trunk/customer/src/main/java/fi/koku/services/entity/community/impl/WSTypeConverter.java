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
  public abstract U fromWsType(T from);
  public abstract T toWsType(U from);
}
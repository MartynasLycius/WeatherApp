package com.proit.weather.api;

import feign.FeignException;

/**
 * 
 * @author wahid
 */
public class ApiException extends FeignException {

   private static final long serialVersionUID = -1394731440866207994L;
   private final transient Object body;
   private final String errorCode;

   public ApiException(int status, String message, Object body, String errorCode) {
      super(status, message);
      this.body = body;
      this.errorCode = errorCode;
   }

   public Object body() {
      return body;
   }

   public String errorCode() {
      return errorCode;
   }
}

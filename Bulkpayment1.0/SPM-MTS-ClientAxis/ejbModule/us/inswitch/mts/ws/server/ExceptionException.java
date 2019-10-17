
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

package us.inswitch.mts.ws.server;

public class ExceptionException extends java.lang.Exception{
    
    private us.inswitch.mts.ws.server.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(us.inswitch.mts.ws.server.ExceptionE msg){
       faultMessage = msg;
    }
    
    public us.inswitch.mts.ws.server.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    
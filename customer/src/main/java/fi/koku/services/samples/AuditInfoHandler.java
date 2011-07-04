package fi.koku.services.samples;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import fi.koku.services.common.AuditInfoType;

/**
 * Parse and process SOAP header and pass a message to the service endpoint implementation.
 *
 * TODO: SOAP mustUnderstand processing
 * 
 * @author aspluma
 */
public class AuditInfoHandler implements SOAPHandler<SOAPMessageContext> {
  private static final String NS_KOKU_COMMON = "http://services.koku.fi/common";
  private static final String ELEM_AUDIT_INFO = "auditInfo";
  private static final QName NAME_AUDIT_INFO = new QName(NS_KOKU_COMMON, ELEM_AUDIT_INFO);

  private JAXBContext jaxbContext;

  @PostConstruct
  public void init() {
    try {
      jaxbContext = JAXBContext.newInstance(fi.koku.services.common.AuditInfoType.class);
    } catch (JAXBException e) {
      System.out.println("error: "+e.getMessage());
      throw new RuntimeException("failed to create JAXBContext", e);
    }
  }

  @PreDestroy
  public void destroy() {
    System.out.println("PreDestroy");
  }
  
  @Override
  public void close(MessageContext msgCtx) {
    System.out.println("close");
  }

  @Override
  public boolean handleFault(SOAPMessageContext msgCtx) {
    System.out.println("handleFault");
    return true;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext msgCtx) {
    System.out.println("handleMessage");
    Boolean isOutgoing = (Boolean) msgCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if(isOutgoing)
      return true;
    
    SOAPMessage msg = msgCtx.getMessage();
    try {
      SOAPHeader hdr = msg.getSOAPHeader();
      
      for(Iterator<?> i = hdr.extractAllHeaderElements(); i.hasNext(); ) {
        SOAPHeaderElement h = (SOAPHeaderElement)i.next();
        if(!NAME_AUDIT_INFO.equals(h.getElementQName()))
          return true;
          
        JAXBElement<AuditInfoType> a = jaxbContext.createUnmarshaller().unmarshal(h, AuditInfoType.class);
        System.out.println("audit: "+a+", "+a.getValue().getComponent());
        
        msgCtx.put("myownmsg", "hello, world");
        msgCtx.setScope("myownmsg", MessageContext.Scope.APPLICATION);
      }
    } catch (SOAPException e) {
      System.out.println("soapexception: "+e.getMessage());
      e.printStackTrace();
    } catch (JAXBException e) {
      System.out.println("jaxb error: "+e.getMessage());
      e.printStackTrace();
    }
    
    return true;
  }

  @Override
  public Set<QName> getHeaders() { 
    System.out.println("getHeaders");
    return null; // FIXME
  }
  
}

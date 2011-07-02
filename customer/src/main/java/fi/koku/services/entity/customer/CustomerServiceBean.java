package fi.koku.services.entity.customer;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.common.AuditInfoType;


/**
 * KoKu Customer service implementation class.
 * 
 * @author Ixonos / aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
    endpointInterface="fi.koku.services.entity.customer.CustomerServicePortType",
    targetNamespace="http://services.koku.fi/entity/customer",
    portName="customerService-soap12-port",
    serviceName="customerService"
)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@RolesAllowed("koku-role")
public class CustomerServiceBean implements CustomerServicePortType {
  private Logger logger = LoggerFactory.getLogger(CustomerServicePortType.class);
  
	@Resource
	private WebServiceContext wsCtx;

  @Override
  public String opAddCustomer(CustomerType customer, AuditInfoType auditHeader) {
    logger.debug("foo");
    logger.info("opAddCustomer");
    System.out.println("opAddCustomer");

    // soap headers
    if(auditHeader != null)
      System.out.println("audit: "+auditHeader.getComponent()+", "+auditHeader.getUserId());
    
    // message context
    Set<String> keys = wsCtx.getMessageContext().keySet();
    for(Iterator<String> i = keys.iterator(); i.hasNext(); )
      System.out.println("i: "+i.next());
    
    String caller = wsCtx.getUserPrincipal().getName();
    System.out.println("caller: "+caller);
    return "xyz";
  }
	
	@Override
	public CustomerType opGetCustomer(String customerId) {
		System.out.println("opGetCustomer");
		return getCustomer();
	}

	@Override
	public void opUpdateCustomer(CustomerType customer) {
		System.out.println("opUpdateCustomer: "+customer);
	}

	@Override
	public void opDeleteCustomer(String customerId) {
		System.out.println("opDeleteCustomer: "+customerId);
	}

	@Override
	public CustomersType opQueryCustomers(
			CustomerSearchCriteriaType customerSearchCriteria) {
		System.out.println("opQueryCustomers");
		return getCustomers();
	}

	private CustomersType getCustomers() {
		CustomersType c = new CustomersType();
		c.getCustomer().add(getCustomer());
		c.getCustomer().add(getCustomer());
		return c;
	}
	
	private CustomerType getCustomer() {
		CustomerType c = new CustomerType();
		c.setId("1234567890");
		c.setHetu("123-456");
		c.setFirstName("simo");
		c.setLastName("salminen");
		return c;
	}

}

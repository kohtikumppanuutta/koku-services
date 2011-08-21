package fi.koku.services.entity.customer.impl;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;

/**
 * KoKu Customer service implementation class.
 * 
 * @author Ixonos / aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
    endpointInterface="fi.koku.services.entity.customer.v1.CustomerServicePortType",
    targetNamespace="http://services.koku.fi/entity/customer/v1",
    portName="customerService-soap11-port",
    serviceName="customerService"
)
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@RolesAllowed("koku-role")
@HandlerChain(file="auditInfoHandler.xml")

public class CustomerServiceBean implements CustomerServicePortType {
  private Logger logger = LoggerFactory.getLogger(CustomerServicePortType.class);
  
  // Temporarily disabled
  //@PersistenceContext
  private EntityManager em;
  
	@Resource
	private WebServiceContext wsCtx;
	
	private CustomerService customerService;
	private CustomerConverter customerConverter;
	
	@PostConstruct
	public void initialize() {
	  customerService = new CustomerServiceImpl(em);
	  customerConverter = new CustomerConverter();
	}

  @Override
  public String opAddCustomer(CustomerType customer) {
    logger.debug("foo");
    logger.info("opAddCustomer");
    System.out.println("opAddCustomer");

    // soap headers
//    if(auditHeader != null)
//      System.out.println("audit: "+auditHeader.getComponent()+", "+auditHeader.getUserId());
    
    System.out.println("msg: "+wsCtx.getMessageContext().get("myownmsg"));
    
    // message context
    Set<String> keys = wsCtx.getMessageContext().keySet();
    for(Iterator<String> i = keys.iterator(); i.hasNext(); )
      System.out.println("i: "+i.next());
    
    String caller = wsCtx.getUserPrincipal().getName();
    System.out.println("caller: "+caller);
    return "xyz2";
  }
	
	@Override
	public CustomerType opGetCustomer(String pic) {
		System.out.println("opGetCustomer: "+em);
		return customerConverter.toWsType(customerService.getCustomer(pic));
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
			CustomerQueryCriteriaType customerQueryCriteria) {
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
		return c;
	}
	
	public void setCustomerService(CustomerService cs) {
	  this.customerService = cs;
	}
 
	/**
	 * Convert between CustomerType and Customer types.
	 * 
	 * @author aspluma
	 */
	private static class CustomerConverter {
	  private DatatypeFactory datatypeFactory;
	  
	  public CustomerConverter() {
	    try {
        datatypeFactory = DatatypeFactory.newInstance();
      } catch (DatatypeConfigurationException e) {
        throw new RuntimeException("Failed to create DatatypeFactory", e);
      }
	  }
	  
	  public CustomerType toWsType(Customer c) {
	    CustomerType ct = new CustomerType();
	    ct.setId(c.getId().toString());
	    ct.setHenkiloTunnus(c.getPic());
	    ct.setEtunimetNimi(c.getFirstNames());
	    ct.setEtuNimi(c.getFirstName());
      ct.setSukuNimi(c.getLastName());
	    ct.setKansalaisuusKoodi(c.getNationality());
	    ct.setKuntaKoodi(c.getMunicipality());
	    ct.setStatus(c.getStatus());
	    
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(c.getStatusDate());
	    ct.setStatusDate(datatypeFactory.newXMLGregorianCalendar(gc));
	    
	    gc.setTime(c.getBirthDate());
	    ct.setSyntymaPvm(datatypeFactory.newXMLGregorianCalendar(gc));

	    ct.setTurvakieltoKytkin(c.isTurvakielto());
	    ct.setKieliKoodi("FI");
	    
	    return ct;
	  }
	  
	}
	
	
}

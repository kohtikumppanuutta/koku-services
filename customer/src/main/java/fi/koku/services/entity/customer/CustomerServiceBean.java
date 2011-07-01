package fi.koku.services.entity.customer;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPBinding;


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
    serviceName="svCustomer"
)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@RolesAllowed("koku-role")
public class CustomerServiceBean implements CustomerServicePortType {
	@Resource WebServiceContext wsCtx;
	
	@Override
	public String opAddCustomer(CustomerType customer) {
		System.out.println("opAddCustomer");
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

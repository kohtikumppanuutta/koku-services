package fi.koku.customer.service;

import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.koku.contract.customer.PtCustomer;
import fi.koku.schema.customer.CustomerSearchCriteriaType;
import fi.koku.schema.customer.CustomerType;
import fi.koku.schema.customer.CustomersType;

/**
 * KoKu Customer service implementation class.
 * 
 * @author aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
        endpointInterface="fi.koku.contract.customer.PtCustomer",
        targetNamespace="http://koku.fi/contract/customer",
        portName="customerService-http-soap12",
        serviceName="svCustomer"
)
public class CustomerServiceBean implements PtCustomer {

	@Override
	public String opAddCustomer(CustomerType customer) {
		System.out.println("opAddCustomer");
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

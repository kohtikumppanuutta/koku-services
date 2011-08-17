package fi.arcusys.koku.palvelukanava.palvelut.appender;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.ixonos.loggingserviceclient.LoggingServiceException_Exception;
import com.ixonos.loggingserviceclient.WSLoggingService;
import com.ixonos.loggingserviceclient.WSLoggingServiceService;

public class KuntaITAppender extends AppenderSkeleton {
	
	private static final String PROPERTY_FILE_NAME = "loggingservice.properties";
	private static final String WSDL_LOCATION_PROPERTY_NAME = "loggingServiceWSDLLocation";
	private static final String SERVICE_NAME_PROPERTY_NAME = "loggingServiceName";
	private static Log log = LogFactory.getLog(KuntaITAppender.class);
	
	private Properties properties;
	
	public KuntaITAppender() throws IOException {
		 this.properties = loadProperties(PROPERTY_FILE_NAME);
	}
	
	public boolean requiresLayout(){
		return true;
	}
	
	protected void append(LoggingEvent event) 
	{
		String message = (String)event.getMessage();
		Scanner scanner = new Scanner(message);
		scanner.useDelimiter(" ");
		String logMessage = null;
		String customerId= null;
		String applicationId= null;
		String userId= null;
		String action= "";
		String time= null;
		try {
			while (scanner.hasNext()) {
				logMessage = scanner.next();
				if (logMessage.equals("loggingservice")) {
					customerId = scanner.next();			
					applicationId = scanner.next();			
					userId = scanner.next();
					Scanner actionScanner = new Scanner(scanner.next());
					actionScanner.useDelimiter("_");					
					while (actionScanner.hasNext()) {						
						action+=actionScanner.next()+" ";
					}
					time = scanner.next();
					URL wsdlLocation = new URL(properties.get(WSDL_LOCATION_PROPERTY_NAME).toString());
					QName serviceName =  new QName(properties.get(SERVICE_NAME_PROPERTY_NAME).toString());
					WSLoggingServiceService service = new WSLoggingServiceService(wsdlLocation, serviceName);
					WSLoggingService port = service.getWSLoggingServicePort();
					Calendar cal = Calendar.getInstance();
					Date d = cal.getTime();
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(d);
					XMLGregorianCalendar date2 = null;
					try {
						date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(time);
					} catch (DatatypeConfigurationException e)
					{
						//
					}					
					try {
						port.addLogEntry(customerId, applicationId, userId, action, date2);						
					} catch (LoggingServiceException_Exception e) {
						log.debug(e.toString());
					}
				} else {break;}
				 
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private static Properties loadProperties(String propertyFileName) throws IOException {
		InputStream in = KuntaITAppender.class.getResourceAsStream(propertyFileName);
		Properties properties = new Properties();
		properties.load(in);
		return properties;
	}
	
	public void close() {
		
	}
	
}



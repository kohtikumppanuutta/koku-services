package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import fi.arcusys.koku.common.soa.UserInfo;


/**
 * Data transfer object for communication with UI/Intalio process. Holds detailed data about message.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@XmlType (name = "message", namespace = "http://soa.kv.koku.arcusys.fi/")
public class MessageTO extends fi.arcusys.koku.kv.soa.MessageSummary {
	private String content;
	private List<UserInfo> deliveryFailedTo;
	
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(final String content) {
		this.content = content;
	}
    /**
     * @return the deliveryFailedTo
     */
    public List<UserInfo> getDeliveryFailedTo() {
        return deliveryFailedTo;
    }
    /**
     * @param deliveryFailedTo the deliveryFailedTo to set
     */
    public void setDeliveryFailedTo(List<UserInfo> deliveryFailedTo) {
        this.deliveryFailedTo = deliveryFailedTo;
    }
}

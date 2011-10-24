package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 15, 2011
 */
public class Receipients {
	private List<String> receipients;

	/**
	 * @return the receipient
	 */
	@XmlElement (name = "receipient")
	public List<String> getReceipients() {
		return receipients;
	}

	/**
	 * @param receipient the receipient to set
	 */
	public void setReceipients(final List<String> receipients) {
		this.receipients = receipients;
	}
}

package py.com.global.spm.model.util;

import java.io.Serializable;
import java.util.Date;

import py.com.global.spm.entities.Company;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class MTSCompanySession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -881302345318422244L;

	private String idSession;
	private Company company;
	private int usedSession;
	private Date lastUpdate;

	public MTSCompanySession(Company company, String idSession, int usedSession) {
		super();
		this.company = company;
		this.idSession = idSession;
		this.usedSession = usedSession;
		this.lastUpdate = new Date();
	}

	public int getUsedSession() {
		return usedSession;
	}

	public void setUsedSession(int usedSession) {
		this.usedSession = usedSession;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "MTSCompanySession [idSession=" + idSession + ", company="
				+ company + ", sessionUsed=" + usedSession + ", lastUpdate="
				+ lastUpdate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MTSCompanySession other = (MTSCompanySession) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		return true;
	}

	public void incrementUsedSession() {
		usedSession++;
		lastUpdate = new Date();
	}

	public void decrementUsedSession() {
		usedSession--;
		lastUpdate = new Date();
	}

}

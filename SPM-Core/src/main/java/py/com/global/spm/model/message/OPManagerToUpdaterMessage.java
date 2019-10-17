package py.com.global.spm.model.message;



import py.com.global.spm.domain.entity.Logdraftop;

import java.io.Serializable;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class OPManagerToUpdaterMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2321267063819090715L;

	private Logdraftop logdraftop;

	public Logdraftop getLogdraftop() {
		return logdraftop;
	}

	public void setLogdraftop(Logdraftop logdraftop) {
		this.logdraftop = logdraftop;
	}

	@Override
	public String toString() {
		return "OPManagerToUpdaterMessage{" +
				"logdraftop=" + logdraftop +
				'}';
	}
}

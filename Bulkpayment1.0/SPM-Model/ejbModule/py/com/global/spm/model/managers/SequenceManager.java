package py.com.global.spm.model.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import py.com.global.spm.model.interfaces.SequenceManagerLocal;
import py.com.global.spm.model.util.Database;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class SequenceManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class SequenceManager implements SequenceManagerLocal {

	@Resource(mappedName = SpmConstants.DATASOURCE)
	private DataSource dataSource;

	private final Logger log = Logger.getLogger(SequenceManager.class);

	/**
	 * Default constructor.
	 */
	public SequenceManager() {
	}

	/**
	 * Obtener el siguiente valor del SEQUENCE
	 */
	public Long next(String sequenceName) {
		Long sequence = -1L;

		if (dataSource != null && sequenceName != null) {
			Connection conn = Database.openConnection(dataSource);
			Statement stmt = null;
			ResultSet res = null;
			if (conn != null) {
				try {
					String sql = "SELECT " + sequenceName
							+ ".NEXTVAL FROM DUAL";
					stmt = conn.createStatement();
					res = stmt.executeQuery(sql);
					if (res.next()) {
						sequence = res.getLong(1);
						res.close();
					} else {
						log.error("Error getting next seq value "
								+ "--> sequenceName=" + sequenceName);
						sequence = -1L;
					}
				} catch (Exception e) {
					log.error("Error getting next seq value --> sequenceName="
							+ sequenceName, e);
				} finally {
					try {
						if (res != null) {
							res.close();
						}
					} catch (SQLException e) {
						log.error("Closing connection error --> sequenceName="
								+ sequenceName, e);
					}
					try {
						if (stmt != null) {
							stmt.close();
						}
					} catch (SQLException e) {
						log.error("Closing connection error --> sequenceName="
								+ sequenceName, e);
					}
					try {
						conn.close();
					} catch (SQLException e) {
						log.error("Closing connection error --> sequenceName="
								+ sequenceName, e);
					}

				}

			}
		}
		return sequence;
	}
}

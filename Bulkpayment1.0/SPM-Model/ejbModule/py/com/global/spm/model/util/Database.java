package py.com.global.spm.model.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Database {

	private static Logger log = Logger.getLogger(Database.class);

	/**
	 * Obtener conexion con la Base de Datos
	 * 
	 * @param ds
	 * @return
	 */
	public static Connection openConnection(DataSource ds) {
		Connection conn = null;
		if (ds != null) {
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				log.error("Fallo al obtener una conexion con la Base de Datos --> error="
						+ e.getMessage());
				conn = null;
			}
		}

		return conn;
	}

	/**
	 * Cerrar conexion con la Base de Datos
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				log.error("Fallo al cerrar conexon con la Base de Datos --> error="
						+ e.getMessage());
			}
		}
	}

}

package py.com.global.spm.model.interfaces;

import java.util.List;

import javax.ejb.Local;

import py.com.global.spm.entities.Logmts;

/**
 * 
 * @author R2
 * 
 */
@Local
public interface LogmtsManagerLocal {

	/**
	 * Para actualizar campos del mts. Hace un merge.
	 * @param logMts
	 */
	
	public void updateLogmts(Logmts logMts);

	/**
	 * Obtiene todos los logs mts con estado satisfactorio y que sean de la
	 * orden de pago recibida como parametro.
	 * 
	 * @param paymentOrderId
	 * @return List con los logmts especificados, null en caso de no tener
	 *         ninguno.
	 */
	public List<Logmts> findLogmtsToRevert(Long paymentOrderId);

	/**
	 * Obtiene todos los logs mts con estado satisfactorio y que sean de la
	 * orden de pago recibida como parametro.
	 * 
	 * @param paymentOrderId
	 * @return List con los logmts especificados, null en caso de no tener
	 *         ninguno.
	 */
	public List<Logmts> findLogmtsToRetry(Long paymentOrderId);
	

}

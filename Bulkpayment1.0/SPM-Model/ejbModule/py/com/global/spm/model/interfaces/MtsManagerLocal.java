package py.com.global.spm.model.interfaces;

import java.util.List;

import javax.ejb.Local;

import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.ws.dto.BilleteraMts;
import py.com.global.spm.model.ws.dto.ReverseResponse;
import py.com.global.spm.model.ws.dto.SalaryPaymentResponse;
import py.com.global.spm.model.ws.dto.TransactionInfoResponse;

@Local
public interface MtsManagerLocal {

	/**
	 * El metodo obtiene los parametros de la tabla company e inicia la conexion
	 * con el MTS.
	 * 
	 * @param idCompany
	 *            Identificador de la empresa que realizara la operacion.
	 * @return El idSesion retornado por el MTS.
	 */
	public String bind(Long idCompany) throws MTSInterfaceException;

	public SalaryPaymentResponse salaryPayment(String idSession, String nroCel,
			String rol, Double monto, String currency, String band,
			String wallet) throws MTSInterfaceException;

	/**
	 * Consulta de saldo por Id de Session retornado por el metodo Bind.
	 * 
	 * @param idSession
	 *            Id de sesion retornado por el metodo bind.
	 * @return Lista de billeteras disponibles.
	 * @throws MTSInterfaceException
	 */
	public List<BilleteraMts> consultaSaldo(String idSession)
			throws MTSInterfaceException;

	/**
	 * Consulta de saldo por Id empresa. Retorna la billetera parametrizada.
	 * 
	 * @param idCompany
	 *            Empresa que quiere consultar el saldo.
	 * @return Lista de billeteras disponibles.
	 * @throws MTSInterfaceException
	 */
	public List<BilleteraMts> consultaSaldo(Long idCompany)
			throws MTSInterfaceException;

	/**
	 * Invoca el metodo unBind del MTS.
	 * 
	 * @param idSession
	 *            Identificador de la sesion a desconectar.
	 * @throws MTSInterfaceException
	 */
	public void unBind(long idCompany, String idSession);

	/**
	 * Invoca el metodo reversaar del MTS.
	 * 
	 * @param idSession
	 *            Identificador de la sesion a desconectar.
	 * @param idTransaction
	 *            Identificador de la transaccion a reversar.
	 * @return ReverseResponse con el id de transaccion y una cadena que
	 *         representa a la respuesta.
	 * @throws MTSInterfaceException
	 */

	public ReverseResponse reverseTransaction(String idSession,
			String idTransaction) throws MTSInterfaceException;

	public TransactionInfoResponse insertTransactionInfo( String idSession,
			String idTransaction, String key1, String value1, String key2, String value2, String key3, String value3);

}
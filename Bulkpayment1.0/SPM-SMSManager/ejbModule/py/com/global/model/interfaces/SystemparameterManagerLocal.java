package py.com.global.model.interfaces;


import py.com.global.model.systemparameters.*;

import javax.ejb.Local;

/**
 * 
 * @author R2
 * 
 */
@Local
public interface SystemparameterManagerLocal {

	public String getParameterValue(Long idProcess, String parameter);

	public Integer getParameterValue(Long idProcess, String parameter,
                                     Integer defaultValue);

	public String getParameterValue(Long idProcess, String parameter,
                                    String defaultValue);

	public Long getParameterValue(Long idProcess, String parameter,
                                  Long defaultValue);

	public String getParameterValue(NotificationParametersEnum param,
                                    String defaultValue);

	public String getParameterValue(GeneralParameters param, String defaultValue);

	public Long getParameterValue(GeneralParameters param, Long defaultValue);

	public String getParameterValue(MtsInterfaceParameters param);

	public boolean getBooleanParameterValue(Long idProcess, String parameter,
                                            boolean defaultValue);

	public boolean getBooleanParameterValue(Long idProcess, String parameter);

	public boolean getBooleanParameterValue(NotificationParametersEnum parameter);

	public Integer getParameter(OPManagerParameters parameter, Integer defaultValue);

	public Long getParameter(OPManagerParameters parameter, Long defaultValue);

	public String getParameter(OPManagerParameters parameter, String defaultValue);

	public String getParameter(MtsInterfaceParameters parameter, String defaultValue);

	public String getParameter(TransferProcessParameters parameter, String defaultValue);

	public Long getParameter(TransferProcessParameters parameter, Long defaultValue);

	public Long getParameter(ReversionProcessParameters parameter, Long defaultValue);

	public long getParameterValue(FlowManagerParameters parameter);

	public String getParameter(SMSManagerParameters parameter);

	public Long getParameter(CleanTimeoutPaymentParameters parameter,
                             Long defaultValue);

}

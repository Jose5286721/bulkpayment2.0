package py.com.global.spm.GUISERVICE.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.faces.context.FacesContext;

//import javax.servlet.ServletContext;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.GUISERVICE.dao.ISystemParameterDao;
import py.com.global.spm.GUISERVICE.services.SuperCompanyService;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.services.UtilService;


@Component("generalHelper")
//@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class GeneralHelper implements Serializable {

	@Autowired
	SuperCompanyService superCompanyService;

	@Autowired
	ISystemParameterDao systemParameterDao;

	@Autowired
	SystemParameterService systemParameterService;

	@Autowired
	private UtilService utilService;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LoggerFactory.getLogger(GeneralHelper.class);
	String datePattern = "dd-MM-yyyy HH:mm:ss,SSS";

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();

	}
	public Sort ordenamiento(String direction, String properties, String directDefault, String propeDefault) {

		String dire = direction;
		String prope = properties;

		/*Ordenamiento por defecto por fechaDesde, de manera ascendente*/
		if (direction == null || "".equals(direction)) {
			dire = directDefault;
		}
		if (properties == null || "".equals(properties)) {
			prope = propeDefault;
		}
		return new Sort(Sort.Direction.fromString(dire), prope);
	}

	public static String getString(String param) {
		return param == null || param.trim().isEmpty() ? null : param.trim();
	}


	public static Integer parseInt(Object o) {
		return o == null || o.toString().trim().isEmpty() ? null : Integer
				.parseInt(o.toString());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> toArray(Set<?> set) {
		return new ArrayList(set);
	}


	public static boolean isNaN(String value) {
		try {
			Double d = Double.parseDouble(value);
			return d.isNaN();
		} catch (Exception e) {
			logger.trace("No es un numero--->" + value);
		}
		return true;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}


	/***
	 * @param d
	 *            Fecha a modificar
	 * @param time
	 *            Valor de hora a modificar. Formato por defecto HH:mm:ss
	 * @param hourFormat
	 *            (Optional)Formato de hora. Por defecto HH:mm:ss
	 * @return la hora formateada del valor pasado o null si ocurrio alguna
	 *         exepcion
	 *
	 * */

	public static Date setTimeOfDate(Date d, String time, String hourFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if (hourFormat != null) {
			sdf = new SimpleDateFormat(hourFormat);
		}
        if(d==null || time==null) return null;
		try {
			Date t = sdf.parse(time);

			Calendar c1 = Calendar.getInstance();
			c1.setTime(d);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(t);
			c1.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
			c1.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
			c1.set(Calendar.SECOND, c2.get(Calendar.SECOND));

			return c1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}


	// agregado por kolino
	private static String byteArrayToHexString(byte[] b) {
		StringBuilder result = new StringBuilder();
		for (byte value : b)
			result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));

		return result.toString();
	}

	public static String toSHA1(byte[] convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			logger.error(String.valueOf(e));
		}
		return byteArrayToHexString(md.digest(convertme));
	}


	public static void main(String[] args) {
		GeneralHelper gh = new GeneralHelper();
		boolean alphanumeric = false;
		int length = 6;
		String password = "a aaaa";
		System.out.println("isValid --> " + password + ", "
				+ gh.isValidPassword(password, length, alphanumeric));
		String password1 = "aaaaa";
		System.out.println("isValid --> " + password1 + ", "
				+ gh.isValidPassword(password1, length, alphanumeric));
		String password2 = "aaaaaa";
		System.out.println("isValid --> " + password2 + ", "
				+ gh.isValidPassword(password2, length, alphanumeric));
		String password3 = "111111";
		System.out.println("isValid --> " + password3 + ", "
				+ gh.isValidPassword(password3, length, alphanumeric));
		String password4 = "aaaaa23";
		System.out.println("isValid --> " + password4 + ", "
				+ gh.isValidPassword(password4, length, alphanumeric));
	}


    public boolean isValidAccountValue(String account) {
		String accountChr = utilService.getAccountRegularExpression();
		String regExp = (accountChr == null) ? SPM_GUI_Constants.ACCOUNT_DEF_REG_EXPR: accountChr;
		Pattern pattern = Pattern.compile(regExp);
		Matcher mat = pattern.matcher( account );
		return mat.matches();

	}
	public boolean isValidPhoneNumber(String phoneNumber){
		String phoneNumberRegex = utilService.getPhoneNumberRegularExpression();
		String regExp = (phoneNumberRegex == null) ? SPM_GUI_Constants.PHONE_DEF_REG_EXPR: phoneNumberRegex;
		Pattern pattern = Pattern.compile(regExp);
		Matcher mat = pattern.matcher( phoneNumber );
		return mat.matches();

	}


	public boolean isValidPassword(String password, int length,
			boolean alphanumeric) {
		if (password == null || password.trim().length() == 0) {
			return false;
		}
		// white space
		String regexSpace = ".*\\s+.*";
		if (password.matches(regexSpace)) {
			logger.debug("invalid password, space found");
			return false;
		}
		// length validate
		if (length > 0 && password.trim().length() < length) {
			return false;
		}
		// alphanumeric validate
		if (alphanumeric) {
			String regexNum = ".*\\d+.*";
			if (!password.matches(regexNum)) {
				logger.debug("invalid password, no num found");
				return false;
			}
			String regexLetter = ".*[a-zA-Z]+.*";
			if (!password.matches(regexLetter)) {
				logger.debug("invalid password, no letters found");
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifica si el usuario logueado es de una super compania o
	 * si esta editando una entidad que es de su compania. Retorna true en alguno de
	 * los casos, caso contrario retorna false
	 * @param idCompanySelected
	 * @return
	 */
	public boolean isValidUserCompany(Long idCompanySelected) {
		if (!superCompanyService.isLoggedUserFromSuperCompany()) {
			long userLoggedIdCompany = superCompanyService
					.getLoggedUserCompany().getIdcompanyPk();
			return userLoggedIdCompany == idCompanySelected;
		}
		return true;
	}

	public boolean isMimeTypeAllowed(MultipartFile file) {

		String mimeTypeFile = identifyFileTypeUsingMimetypesFileTypeMap(file);

		String mimeTypesAllowed = systemParameterService
				.getSystemParameterValue(
						SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
						SPM_GUI_Constants.MIME_TYPES_ALLOWED,null);
		logger.debug("mimeType 	---> " + mimeTypeFile);
		logger.info("mimeTypesAllowed 	---> " + mimeTypesAllowed);
		// log.info("idEmpresaActual --> " + idcompanyPk
		// + "  idEmpresasParaAgentes --> " + idCompanyPaymentForAgent);
		boolean res = false;

		// String idcompany = Long.toString(idcompanyPk);
		// String idcompany = null;
		// idcompany.valueOf(idcompanyPk);
		String[] mimeTypes = mimeTypesAllowed.split(",");
		for (String mimeType : mimeTypes) {
			if (mimeType.trim().equals(mimeTypeFile)) {
				res = true;
				break;
			}
		}

		return res;
	}


	public String identifyFileTypeUsingMimetypesFileTypeMap(MultipartFile file) {
		Tika tika = new Tika();
		String filetype = "";
		try {
			filetype = tika.detect(file.getInputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filetype;

	}

}

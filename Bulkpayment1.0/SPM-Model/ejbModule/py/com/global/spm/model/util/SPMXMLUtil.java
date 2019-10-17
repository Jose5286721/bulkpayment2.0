package py.com.global.spm.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import py.com.global.spm.entities.Paymentorder;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SPMXMLUtil {

	private static final Logger log = LogManager.getLogger(SPMXMLUtil.class);

	private static final String EL_PAYMENT_ORDER_DEF = "payment-order";
	// cabecera
	private static final String EL_PAYMENT_ORDER = "payment-order-header";
	private static final String AT_PAYMENT_ORDER_ID = "id-payment-order";
	private static final String AT_DATE = "date";
	private static final String AT_ID_COMPANY = "id-company";
	private static final String AT_TOTAL_AMOUNT = "total-amount";
	// detalle
	private static final String EL_PAYMENT_ORDER_DETAILS = "payment-order-details";
	private static final String EL_PAYMENT_ORDER_DETAIL = "payment-order-detail";
	private static final String AT_BENEFICIARY_NUMBER = "beneficiary-number";
	private static final String AT_BENEFICIARY_AMOUNT = "amount";

	public static String getFileName(String filePrefix, long idpaymentorderPk) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat(
				SpmConstants.FILE_DATE_HOUR_PATTERN);
		return filePrefix + df.format(calendar.getTime()) + "_"
				+ idpaymentorderPk + ".xml";
	}

	public static byte[] getXMLStream(Paymentorder paymentorder,
			List<PaymentorderdetailWrapper> podetailsWrapper, String filePath,
			String fileName) {
		log.debug("Creating file --> [" + filePath + File.separatorChar
				+ fileName + "]");
		byte[] fileStream = null;
		if (createXML(paymentorder, podetailsWrapper, filePath, fileName)) {
			File file = new File(filePath + File.separatorChar + fileName);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				fileStream = new byte[(int) file.length()];
				int bytesRead = fis.read(fileStream);
				log.trace("bytesRead=" + bytesRead);
			} catch (FileNotFoundException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						log.error(e);
					}
				}
			}
		} else {
			log.error("File not created --> [" + filePath + File.separatorChar
					+ fileName + "] " + paymentorder);
		}
		return fileStream;
	}

	private static boolean createXML(Paymentorder paymentorder,
			List<PaymentorderdetailWrapper> podetailsWrapper, String filePath,
			String fileName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(EL_PAYMENT_ORDER_DEF);
			doc.appendChild(rootElement);
			// payment order header
			Element paymentorderElement = doc.createElement(EL_PAYMENT_ORDER);
			paymentorderElement.setAttribute(AT_PAYMENT_ORDER_ID,
					String.valueOf(paymentorder.getIdpaymentorderPk()));
			paymentorderElement.setAttribute(AT_DATE,
					SpmUtil.timeStampToStr(paymentorder.getCreationdateTim()));
			paymentorderElement.setAttribute(AT_ID_COMPANY,
					String.valueOf(paymentorder.getIdcompanyPk()));
			paymentorderElement.setAttribute(AT_TOTAL_AMOUNT,
					String.valueOf(paymentorder.getAmountNum()));
			rootElement.appendChild(paymentorderElement);
			// payment order detail
			Element paymentdetailsElement = doc
					.createElement(EL_PAYMENT_ORDER_DETAILS);
			rootElement.appendChild(paymentdetailsElement);
			// agregar detalle de orden de pago
			if (podetailsWrapper != null && podetailsWrapper.size() > 0) {
				Element paymentdetail;
				for (PaymentorderdetailWrapper pod : podetailsWrapper) {
					paymentdetail = doc.createElement(EL_PAYMENT_ORDER_DETAIL);
					paymentdetail.setAttribute(AT_BENEFICIARY_NUMBER,
							pod.getBeneficiaryNumberStr());
					paymentdetail.setAttribute(AT_BENEFICIARY_AMOUNT,
							pod.getAmountStr());
					paymentdetailsElement.appendChild(paymentdetail);
				}
			} else {
				log.error("Orden de pago sin detalles --> " + paymentorder);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath
					+ File.separatorChar + fileName);
			transformer.transform(source, result);
			log.debug("File created! --> [" + filePath + File.separatorChar
					+ fileName + "]");
			return true;
		} catch (ParserConfigurationException pce) {
			log.error("Creating file --> " + paymentorder, pce);
		} catch (TransformerException tfe) {
			log.error("Creating file --> " + paymentorder, tfe);
		}
		return false;
	}
}

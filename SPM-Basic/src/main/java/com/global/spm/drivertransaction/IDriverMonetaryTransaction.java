
package com.global.spm.drivertransaction;

import java.util.Map;

public interface IDriverMonetaryTransaction {

	void initialize(Map<String, String> parameters);

	public ResponseMonetaryTransaction executeTransfer(RequestForTransferTransaction request);

	public ResponseMonetaryTransaction executeReverse(RequestForReverseTransaction request);

}

package py.com.spm.csvimporter;

import com.opencsv.bean.CsvBindByPosition;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class CSVRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7176328548477024047L;
	@CsvBindByPosition(position = 0)
	private String phonenumber;
	@CsvBindByPosition(position = 1)
	private double amount;
	@CsvBindByPosition(position = 2)
	private String currency;
	@CsvBindByPosition(position = 3)
	private String wallet;
	@CsvBindByPosition(position = 4)
	private String role;
	@CsvBindByPosition(position = 5)
	private String email;
	@CsvBindByPosition(position = 6)
	private String name;
	@CsvBindByPosition(position = 7)
	private String lastname;
	@CsvBindByPosition(position = 8)
	private String field1;//c?dula
	@CsvBindByPosition(position = 9)
	private String field2;

	private String generico1;
	private String generico2;
	private String generico3;
	
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}
	

	public String getGenerico1() {
		return generico1;
	}

	public void setGenerico1(String generico1) {
		this.generico1 = generico1;
	}
	
	public String getGenerico2() {
		return generico2;
	}

	public void setGenerico2(String generico2) {
		this.generico2 = generico2;
	}
	public void setGenerico3(String generico3) {
		this.generico3 = generico3;
	}


	public boolean parse(String[] record) {
		int index = 0;
		if (record.length == 10) {
			phonenumber = record[index++];
			String amuntStr = record[index++];
			try {
				amount = Double.parseDouble(amuntStr);
			} catch (Exception e) {
				System.out.println("Invalid amount --> " + amuntStr);
				return false;
			}
			currency = record[index++];
			wallet = record[index++];
			role = record[index++];
			email = record[index++];
			name = record[index++];
			lastname = record[index++];
			field1 = record[index++];
			field2 = record[index++];
			return true;
		} else if (record.length == 5) {
			phonenumber = record[index++];
			String amuntStr = record[index++];
			try {
				amount = Double.parseDouble(amuntStr);
			} catch (Exception e) {
				System.out.println("Invalid amount --> " + amuntStr);
				return false;
			}
			currency = record[index++];
			wallet = record[index++];
			role = record[index++];
			return true;
		}
		/**
		 * TODO:Esto estaba comentado, supongo que no deberia admitir registros con dos columnas
		 * TODO: CAPI
		 */
		else if (record.length == 2) {
		phonenumber = record[index++];
		String amuntStr = record[index++];
		try {
			amount = Double.parseDouble(amuntStr);
		} catch (Exception e) {
			System.out.println("Invalid amount --> " + amuntStr);
			return false;
		}
		return true;
			
		} else if (record.length == 3) {
			phonenumber = record[index++];
			field1= record[index++];
			String amuntStr = record[index++];
			try {
				amount = Double.parseDouble(amuntStr);
			} catch (Exception e) {
				System.out.println("Invalid amount --> " + amuntStr);
				return false;
			}
			return true;
		}
		
		else {
			System.out.println("Invalid record --> " + Arrays.toString(record));
		}
		return false;
	}

	public boolean parseGuardaSaldo(String[] record) {
		int index = 0;
		if (record.length == 5) {

			phonenumber = record[index++];
			field1= record[index++];
			String amuntStr = record[index++];
			try {
				amount = Double.parseDouble(amuntStr);
			} catch (Exception e) {
				System.out.println("Invalid amount --> " + amuntStr);
				return false;
			}
			generico1= record[index++];
			generico2= record[index++];
			return true;
		}
		else {
			System.out.println("Invalid record --> " + Arrays.toString(record));
		}
		return false;
	}

	@Override
	public String toString() {
		return "CSVRecord [phonenumber=" + phonenumber + ", amount=" + amount
				+ ", currency=" + currency + ", wallet=" + wallet + ", role="
				+ role + ", email=" + email + ", name=" + name + ", lastname="
				+ lastname + ", field1=" + field1 + ", field2=" + field2 + "]";
	}

}

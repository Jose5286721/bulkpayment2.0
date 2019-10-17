package py.com.global.spm.model.util;


/**
 * 
 * @author Lino Chamorro
 * 
 */
public class ProcessWindow {

	private int from;
	private int to;

	public ProcessWindow() {
		super();
	}

	public ProcessWindow(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public String getFromStr() {
		if (from < 10) {
			return "0" + from + ":00:00";
		}
		return from + ":00:00";
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public String getToStr() {
		if (to < 10) {
			return "0" + from + ":59:59";
		}
		return to + ":59:59";
	}

	public void setTo(int to) {
		this.to = to;
	}

	public boolean isValid() {
		if (from < 0 || to < 0) {
			return false;
		}
		return !(from > to);
	}

	@Override
	public String toString() {
		return "ProcessWindow [from=" + getFromStr() + ":00:00" + ", to=" + getToStr()
				+ ":59:59]";
	}

}

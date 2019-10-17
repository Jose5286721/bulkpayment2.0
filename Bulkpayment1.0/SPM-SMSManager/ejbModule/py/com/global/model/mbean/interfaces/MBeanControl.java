package py.com.global.model.mbean.interfaces;

/**
 * @author Lino Chamorro
 *
 */
public interface MBeanControl {
	
	// Life cycle methods
	public void create() throws Exception;

	public void destroy() throws Exception;
	
	public void start();
	
	public void stop();
	
	public int isRunning();
	
	// other methods
	public String getLastUpdate();
}

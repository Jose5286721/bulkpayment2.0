package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Eventcode;

@Local
public interface EventCodeManagerLocal {
	public Eventcode getEventcodeById(long idEventcode);

}

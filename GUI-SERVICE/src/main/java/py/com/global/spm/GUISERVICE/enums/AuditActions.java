package py.com.global.spm.GUISERVICE.enums;

public enum AuditActions {
	CREATE("audit_persist","Creacion"),
	UPDATE("audit_update","Modificacion"),
	REMOVE("audit_remove","Eliminacion"),
	IMPORT_FILE("audit_import_file","Importacion de Plantilla");
	
	String codigo0;
	String descripcion;
	private AuditActions(String codigo0, String descripcion) {
		this.codigo0 = codigo0;
		this.descripcion = descripcion;
	}
	
	public String getCodigo0() {
		return codigo0;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return getDescripcion();
	}
	
}

package py.com.global.spm.domain.audit;

public enum AuditActions {
    INSERTED("audit_persist"),
    UPDATED("audit_update"),
    DELETED("audit_delete");

    private final String name;

    AuditActions(String value) {
        this.name = value;
    }
    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}

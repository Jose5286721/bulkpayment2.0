package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {
    String status;
    String descripcion;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status='" + status + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

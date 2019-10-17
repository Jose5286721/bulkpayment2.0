package py.com.global.spm.GUISERVICE.message;

import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;
import py.com.global.spm.domain.entity.Draft;

import java.io.Serializable;
import java.util.List;

public class BeneficiaryImporterCsvMessage implements Serializable {
    private static final long serialVersionUID = -458642158486463132L;

    private List<BeneficiaryDraftDetailDto> newBeneficiaries;
    private Draft draft;

    public List<BeneficiaryDraftDetailDto> getNewBeneficiaries() {
        return newBeneficiaries;
    }

    public void setNewBeneficiaries(List<BeneficiaryDraftDetailDto> newBeneficiaries) {
        this.newBeneficiaries = newBeneficiaries;
    }

    public Draft getDraft() {
        return draft;
    }

    public void setDraft(Draft draft) {
        this.draft = draft;
    }
}

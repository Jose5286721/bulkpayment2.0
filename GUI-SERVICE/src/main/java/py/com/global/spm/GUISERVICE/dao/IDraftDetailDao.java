package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Draftdetail;
import py.com.global.spm.domain.entity.DraftdetailId;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author christiandelgado on 06/07/18
 * @project GOP
 */
@Repository
public interface IDraftDetailDao extends JpaRepository<Draftdetail, DraftdetailId>{

    /**
     * Retorna lista de detalles de un borrador por id  del borrador
     * @param id
     * @return
     */
     Page<Draftdetail> findByDraftIddraftPk(Long id, Pageable pageRequest);

     List<Draftdetail> findByDraftIddraftPk(Long id);

     @Query("SELECT SUM (d.amountChr) from Draftdetail d where d.id.iddraftPk = :idDraft")
     BigDecimal getTotalAmount(Long idDraft);


}

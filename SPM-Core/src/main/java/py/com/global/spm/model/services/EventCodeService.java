package py.com.global.spm.model.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Eventcode;
import py.com.global.spm.model.repository.IEventCodeDao;


@Service
@Transactional
public class EventCodeService {

    private final Logger log = LoggerFactory.getLogger(EventCodeService.class);


    private final IEventCodeDao dao;

    @Autowired
    public EventCodeService(IEventCodeDao dao) {
        this.dao = dao;
    }

    public Eventcode getEventByIdAndProcess(Long idEventCodeNum, Long idProcessPk) {
        log.trace("Finding EventCode by id --> idEventCodeNum=" + idEventCodeNum + "y idProcessPk" + idProcessPk);
        try {
            return dao.findByIdIdeventcodeNumAndIdIdprocessPk(idEventCodeNum, idProcessPk);
        } catch (Exception e) {
            log.error("Finding EventCode by id --> idEventCodeNum=" + idEventCodeNum + "y idProcessPk" + idProcessPk, e);
            throw e;
        }
    }
}

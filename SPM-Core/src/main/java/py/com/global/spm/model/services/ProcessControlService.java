package py.com.global.spm.model.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Processcontrol;
import py.com.global.spm.model.repository.IProcessControlDao;

@Service
@Transactional
public class ProcessControlService {

    private final Logger log = LoggerFactory.getLogger(ProcessControlService.class);

    private final IProcessControlDao dao;

    @Autowired
    public ProcessControlService(IProcessControlDao dao) {
        this.dao = dao;
    }


    public boolean createOrUpdate(Processcontrol processControl) {
        try {
            dao.save(processControl);
            return true;
        } catch (Exception e) {
            log.error("Creating processControl --> " + processControl, e);
        }
        return false;
    }

}

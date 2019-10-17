package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Workflow;
import py.com.global.spm.model.repository.IWorkflowDao;

import java.util.Optional;

@Service
@Transactional
public class WorkflowService {
    private final Logger log = LoggerFactory.getLogger(WorkflowService.class);

    @Autowired
    private IWorkflowDao dao;

    public Workflow getWorkflowById(long idworkflowPk) {
        Workflow workflow = null;
        try {
            Optional<Workflow> d = dao.findById( idworkflowPk);
            if(d.isPresent())workflow=d.get();
        } catch (Exception e) {
            log.error("Error al obtener WorkFlow --> id = " + idworkflowPk, e);
        }
        return workflow;
    }
}

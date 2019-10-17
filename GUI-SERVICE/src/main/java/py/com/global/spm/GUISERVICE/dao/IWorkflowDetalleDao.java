package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.domain.entity.Workflow;
import py.com.global.spm.domain.entity.Workflowdet;

import java.util.List;
@Repository
public interface IWorkflowDetalleDao extends JpaRepository<Workflowdet, Long>, JpaSpecificationExecutor<Workflowdet> {
        List<Workflowdet> findByWorkflow(Workflow wokflow);

        void deleteByUser(User user);

}


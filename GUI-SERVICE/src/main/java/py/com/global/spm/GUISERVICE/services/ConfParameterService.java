package py.com.global.spm.GUISERVICE.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IConfParameterDao;
import py.com.global.spm.domain.entity.ConfParameters;

@Service
public class ConfParameterService {
    private static final Logger log = LogManager.getLogger(ConfParameterService.class);

    private final IConfParameterDao dao;

    public ConfParameterService(IConfParameterDao dao){
        this.dao = dao;
    }

    public ConfParameters saveOrUpdate(ConfParameters confParameters){
        return dao.save(confParameters);
    }

    public ConfParameters byKey(String key){
        return dao.findByKey(key);
    }

    ConfParameters saveFile(String key, byte[] file, String fileName,
                            String fileType) {
        ConfParameters param = null;
        try {
            param = byKey(key);
            if(param == null) {
                param = new ConfParameters();
                param.setKey(key);
            }
            log.debug("Guardando archivo --> key = {}  filename = {} ",key, fileName);
            param.setValue(fileName);
            param.setTemplate(file);
            param.setFileType(fileType);
            saveOrUpdate(param);
        } catch (Exception e) {
            log.error("Guardando archivo --> key = {}  filename = {} ",key, fileName,e);

        }
        return param;
    }

}

package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import py.com.global.spm.GUISERVICE.services.UtilService;

@RestController
@Api(tags = "Util")
@RequestMapping(value = "api/util")
public class UtilController extends BaseController{

    @Autowired
    private UtilService utilService;

    private static final Logger logger = LoggerFactory
            .getLogger(UtilController.class);

    @GetMapping(value = "/phoneRegExpr",  produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPhoneNumberRegExpr(){
        try {
            return utilService.getNumberPhoneRegExp();
        } catch (Exception e) {
            logger.error("Al obtener expresion regular del numero telefonico: {} ",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/csvMaxSize", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCSVMaxSizeValue(){
        try{
            return utilService.getCSVSizeValue();
        }catch (Exception e){
            logger.error("al obtener tamano maximo del csv");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}

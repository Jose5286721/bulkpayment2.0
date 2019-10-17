package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBSmsLogMessage;
import py.com.global.spm.domain.utils.ScopeViews;
import py.com.global.spm.domain.entity.Logmessage;
import py.com.global.spm.GUISERVICE.services.SmsLogMessageService;

@Api(tags = "LogMts")
@RestController
@RequestMapping(value = "/api/smsLogMessage")

public class SmsLogMessageController extends BaseController<Logmessage, Long>{
    private static final Logger logger = LoggerFactory.getLogger(SmsLogMessageController.class);
    @Autowired
    private SmsLogMessageService smsLogMessageService;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_REGISTRO_DE_MSJ_RECEIVED')")
    @JsonView({ScopeViews.Basics.class})
    @GetMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogMtsListByFilter(@RequestParam(value ="sourcenumberChr", required = false) String sourcenumberChr,
                                        @RequestParam(value ="idCompany", required = false) Long idCompany,
                                        @RequestParam(value = "stateChr", required = false) String stateChr,
                                        @RequestParam(value = "sinceDate", required = false) String sinceDate,
                                        @RequestParam(value = "toDate", required = false) String toDate,
                                        @RequestParam(value="direction",required = false) String direction,
                                        @RequestParam(value="properties",required = false) String properties,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {
        try {
            return smsLogMessageService.filterSmsLogMessagePag(new CBSmsLogMessage(sourcenumberChr,idCompany,stateChr,sinceDate,toDate), direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de log del Mts", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}

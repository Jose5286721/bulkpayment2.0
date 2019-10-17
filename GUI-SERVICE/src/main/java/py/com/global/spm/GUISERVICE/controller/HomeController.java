package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.GUISERVICE.dto.Home.ReqSaldoActual;
import py.com.global.spm.GUISERVICE.services.HomeService;

import javax.validation.Valid;

@Api(tags = "Home")
@RestController
@RequestMapping(value = "/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;


    @PreAuthorize("hasRole('ROLE_VER_SALDO_CUENTA')")
    @PostMapping(value = "/getSaldo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getSaldoActual(@RequestBody @Valid ReqSaldoActual req) {
        return homeService.getSaldoActual(req.getCode(), req.getIdCompany());
    }

}



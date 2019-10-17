package py.com.global.spm.GUISERVICE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;

@RestController
@RequestMapping("/auth/cache/")
public class TestController {

    @Autowired
    private SystemParameterService systemParameterService;
    @GetMapping(value = "getSystem")
    public Object getValue(@RequestParam(value = "processId") Long procesId,
                           @RequestParam(value = "parameterPk") String parameterPk,
                           @RequestParam(value = "defaultValue") String defaultValue){
        return systemParameterService.getSystemParameterValue(procesId,parameterPk,defaultValue);
    }
}

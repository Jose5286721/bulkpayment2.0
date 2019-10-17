package py.com.global.spm.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.com.global.spm.model.services.SystemParameterService;

@RestController
@RequestMapping("/api/test")
public class SystemTest {
    @Autowired
    private SystemParameterService systemParameterService;
    @GetMapping(value = "/systemTest")
    public Object systemParameterValue(@RequestParam(name = "idProcess") Long idProcess,
                                       @RequestParam(name = "parameterPk") String parameterPk){

        return systemParameterService.getParameterValue(idProcess,parameterPk,"asdsad");
    }
}

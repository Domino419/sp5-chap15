package controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
    private static final Log log = LogFactory.getLog(RegisterController.class);

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        log.info(":::::::::::::::::::::::::::::: logout.session :" + session );
        return "redirect:/main";
    }

}

package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

import javax.servlet.http.HttpSession;


/**
 * class         : LoginController
 * date          : 25-01-02
 * description   : 로그인 요청을 처리하는 컨트롤러 클래스.
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;

    private static final Log log = LogFactory.getLog(RegisterController.class); // 로그 추가

    /**
     * method        : setAuthService
     * date          : 25-01-02
     * param         : AuthService authService - 설정할 인증 서비스 객체
     * return        : void
     * description   : 인증 서비스를 주입받기 위한 Setter 메서드.
     */
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * method        : form
     * date          : 25-01-02
     * param         : LoginCommand loginCommand - 초기화된 로그인 데이터 객체
     * return        : String - 로그인 폼 뷰 이름
     * description   : GET 요청 시 로그인 form 반환하며, "REMEMBER" 쿠키가 존재하면 이메일을 form에 설정.
     * history       : 쿠키 사용으로 시그니처 변경   -- 25.01.06
     */
    @GetMapping
    public String form(LoginCommand loginCommand,@CookieValue(value = "REMEMBER",required = false) Cookie rCookie ) {
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: LoginController#form() ::::" + (rCookie == null ? "null" : "NotNull"));
        if(rCookie != null) {
            log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::LoginController#form() " );
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }
    /**
     * method        : submit
     * date          : 25-01-02
     * param         : LoginCommand loginCommand - 사용자가 입력한 로그인 데이터, Errors errors - 검증 오류 저장 객체
     * return        : String - 로그인 결과에 따른 뷰 이름
     * description   : POST 요청 시 로그인 데이터를 검증하고, 인증 성공 시 성공 뷰를, 실패 시 폼 뷰를 반환.
     */
    @PostMapping
    public String submit(LoginCommand loginCommand, Errors errors, HttpSession session,HttpServletResponse response) {
        new LoginCommandValidator().validate(loginCommand, errors);
        if(errors.hasErrors()) {
            return "login/loginForm";    // 로그인 폼을 보여주기 위한 뷰
        }try{
            AuthInfo authInfo = authService.authenticate(
                     loginCommand.getEmail()
                    ,loginCommand.getPassword() ) ;
            session.setAttribute("authInfo", authInfo); // 로그인 성공 정보를 세션에 저장
            log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::LoginController.submit.session 저장 성공 : " + authInfo);

            //Cookie str

            // 1."REMEMBER" 쿠키를 생성하여 이메일 정보를 저장
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
            rememberCookie.setPath("/");   //path 설정을 "/"를 설정하면 해당 웹 애플리케이션의 모든 경로에서 쿠키를 사용할 수 있음.
            log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::쿠키 사용 여부 확인 : " + loginCommand.isRememberEmail());
            // 2. 이메일 기억 옵션 o
            if(loginCommand.isRememberEmail()){
                rememberCookie.setMaxAge(60*60*24*30);   // 30일(2,592,000초) 동안 쿠키 유지
               // rememberCookie.setMaxAge(60);   // test를 위해서 60초동안 쿠키 유지

            // 2. 이메일 기억 옵션 X
            }else {
                rememberCookie.setMaxAge(0);             // 쿠키 유효 기간을 0초로 설정하면 쿠키는 바로 삭제됨.
               // rememberCookie.setMaxAge(0);           // 쿠키 유효 기간을 -1 (음수)로 설정하면 세션쿠키로 적용됨.
            }
            //3. 응답 객체에 쿠키를 추가하여 클라이언트에 전달
            log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::쿠키값 확인( rememberCookie.getValue)  : " + rememberCookie.getValue());
            response.addCookie(rememberCookie);
            //Cookie end

            return "login/loginSuccess";  // 로그인 성공 결과를 보여주기 위한 뷰
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            return "login/loginForm";     // 로그인 폼을 보여주기 위한 뷰
        }
    }


}

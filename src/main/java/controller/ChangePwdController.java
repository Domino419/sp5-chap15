package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.AuthInfo;
import spring.ChangePasswordService;
import spring.WrongIdPasswordException;


/**
 * class         : ChangePwdController
 * date          : 25-01-02
 * description   : 비밀번호 변경 요청을 처리하는 컨트롤러.
 *                 비밀번호 변경 폼 요청(GET)과 비밀번호 변경 처리(POST)를 처리.
 */
@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

    private ChangePasswordService changePasswordService;

    /**
     * method        : setChangePasswordService
     * date          : 25-01-02
     * param         : ChangePasswordService changePasswordService - 비밀번호 변경 서비스 객체
     * return        : void
     * description   : ChangePasswordService 객체를 주입받아 설정.
     */
    public void setChangePasswordService(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    /**
     * method        : form
     * date          : 25-01-02
     * param         : ChangePwdCommand pwdCmd - 모델 객체로 비밀번호 변경 데이터를 담음
     * return        : String - 비밀번호 변경 폼 뷰 이름
     * description   : 비밀번호 변경 폼을 보여주는 요청을 처리.
     */
    @GetMapping
    public String form(@ModelAttribute("command") ChangePwdCommand pwdCmd){
        return "edit/changePwdForm";
    }

    /**
     * method        : submit
     * date          : 25-01-02
     * param         : ChangePwdCommand pwdCmd - 사용자 입력 데이터,
     *                 Errors errors - 입력 검증 오류 객체,
     *                 HttpSession session - 사용자 세션 정보
     * return        : String - 처리 결과에 따른 뷰 이름
     * description   : 비밀번호 변경 요청 데이터를 검증하고, 검증 통과 시 비밀번호 변경을 처리.
     *                 실패 시 오류를 표시하며 비밀번호 변경 폼으로 이동.
     */
    @PostMapping
    public String submit(@ModelAttribute("command") ChangePwdCommand pwdCmd, Errors errors,HttpSession session){
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if(errors.hasErrors()){
            return "edit/changePwdForm";
        }

        // 세션에서 인증 정보를 가져와 비밀번호 변경에 사용
        AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");

        try{
            changePasswordService.changePassword(
                    authInfo.getEmail(),
                    pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword());
            // return "edit/changePwd";
            return "edit/changedPwd";   // 주소 오타로 인한 404 에러
        } catch (WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching");
            return "edit/changePwdForm";
        }
    }
}

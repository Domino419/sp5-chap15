package controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler; //250107 add.
import org.springframework.beans.TypeMismatchException; //250107 add.

import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;


/**
 * class         : MemberDetailController
 * date          : 25-01-07
 * description   : 회원 상세 정보를 처리하는 컨트롤러 클래스.
 */
@Controller
public class MemberDetailController {

    private static final Log log = LogFactory.getLog(RegisterController.class);

    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }


    /**
     * method        : Detail
     * date          : 25-01-07
     * param         : memId - 조회할 회원의 ID
     */
    @GetMapping("/members/{id}")
    public String Detail(@PathVariable("id") Long memId, Model model){
        Member member = memberDao.selectById(memId);
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: MemberDetailController#Detail() ::::");

        if(member == null){
            throw new MemberNotFoundException() ;
        }
        model.addAttribute("member", member);
        return "member/memberDetail";
    }

    /**
     * method        : handleTypeMismatchException
     * date          : 25-01-07
     * description   : TypeMismatchException 예외가
     */
    @ExceptionHandler(TypeMismatchException.class)
    public String hadleTypeMismatchException(){
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: MemberDetailController#hadleTypeMismatchException()");
        return "member/invalidld";
    }

    /**
     * method        : handleNotFoundException
     * date          : 25-01-07
     * description   : MemberNotFoundException 예외
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public String handleNotFoundException(){
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: MemberDetailController#handleNotFoundException()");
        return "member/noMember";
    }


}

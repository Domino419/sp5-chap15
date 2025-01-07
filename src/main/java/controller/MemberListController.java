package controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import spring.Member;
import spring.MemberDao;
import org.springframework.validation.Errors;


/**
 * class         : MemberListController
 * date          : 2025-01-07
 * description   : 회원 목록 조회를 처리하는 컨트롤러
 */
@Controller
public class MemberListController {

    private static final Log log = LogFactory.getLog(RegisterController.class);

    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * method        : list
     * date          : 2025-01-07
     * param         : listCommand - 날짜 범위를 포함한 명령 객체, model - 모델 객체
     * return        : String - 뷰 이름
     * description   : 날짜 범위에 따라 회원 목록을 조회하여 모델에 추가하고 회원 목록 페이지를 반환한다.
     */
    @RequestMapping("/members")
    public String list( @ModelAttribute("cmd") ListCommand listCommand, Errors errors, Model model) {
        log.info(":::::::::::::::::::::::::::::::::::::::: listCommand.from & to " + listCommand.getFrom() + "::::::::::" +  listCommand.getTo() );

        if(errors.hasErrors()) {
            return "member/memberList";
        }

        if(listCommand.getFrom() !=null && listCommand.getTo() != null) {
            List<Member> members = memberDao.selectByRegdate(
                    listCommand.getFrom(), listCommand.getTo()) ;
            model.addAttribute("members", members);
        }
        return "member/memberList";    // 리턴할 때 members/memberList 로 하는 바람에 404 에러
    }
}

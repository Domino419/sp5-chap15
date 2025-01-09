package controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;



/**
 * class         : RestMemberController
 * date          : 25-01-08
 * description   : 회원 정보를 REST API로 제공하는 컨트롤러 클래스
 */

@RestController
public class RestMemberController {

    private MemberDao memberDao;
    private MemberRegisterService registerService;

    private static final Log log = LogFactory.getLog(RegisterController.class);

    /**
     * method        : Members
     * date          : 25-01-08
     * return        : List<Member> - 모든 회원 정보 리스트
     * description   : 데이터베이스의 모든 회원 정보를 반환합니다.
     */
    @GetMapping("/api/members")
    public List<Member> Members() {
        return memberDao.selectAll();
    }

    /**
     * method        : newMember
     * date          : 25-01-08
     * description   : Json형식으로 전송된 요청 데이터를 커맨드 객체로 전달받기 예제
     */
    @PostMapping("/api/members")
    public void newMember(@RequestBody @Valid RegisterRequest regReq,HttpServletResponse response) throws IOException {
        try{
            Long newMemberId=registerService.regist(regReq);                          // 회원 등록 요청 처리 및 새 회원 ID 반환
            response.setHeader("Location","/api/members/"+newMemberId);   // 응답 헤더에 Location 설정
            response.setStatus(HttpServletResponse.SC_CREATED);                       // HTTP 상태코드 201(Created) 반환
        } catch (DuplicateMemberException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT);                      // 중복 회원 예외 발생 시 HTTP 상태코드 409(Conflict) 반환
        }
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::: New member created");

    }
//
//    /**
//     * method        : Member (as is)
//     * date          : 25-01-08
//     * return        : Member - 조회된 회원 객체
//     * description   : 회원 정보를 직접 반환하거나 없을 경우에 null 값을 리턴함.  HTTP 응답코드를 직접 설정함
//     */
//    @GetMapping("api/members/{id}")   // http://localhost:8080/api/members/11
//    public Member MemberAsis(@PathVariable Long id, HttpServletResponse response) throws IOException {
//        Member member = memberDao.selectById(id);
//        if(member == null) {
//            log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: RestMemberController#Member() is null" );
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return null;
//        }
//        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: RestMemberController#Member() is NotNull" + member  );
//        return member;
//    }


    /**
     * method        : Member (to be)
     * date          : 25-01-08
     * return        : Member - 조회된 회원 객체 /
     * description   : 상태 코드와 응답 본문(회원 정보 또는 에러 메시지)을 한 번에 포함하여 반환함.
     */
    @GetMapping("api/members/{id}")   // http://localhost:8080/api/members/11
    public ResponseEntity<Object> member (@PathVariable Long id) throws IOException {
        Member member = memberDao.selectById(id);
        if(member == null) {
            log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: RestMemberController#Member() is null" );
            // response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("no member"));
        }
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::: RestMemberController#Member() is NotNull" + member  );
        return ResponseEntity.status(HttpStatus.OK)
                .body(member);
    }


    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setRegisterService(MemberRegisterService registerService) {
        this.registerService = registerService;
    }

}

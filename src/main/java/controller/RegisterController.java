package controller;

import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder; // 추가
import org.springframework.web.bind.annotation.*;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

/**
 * class         : RegisterController
 * date          : 24-12-17
 * description   : 회원 등록 기능을 처리하는 컨트롤러 클래스.
 *                 각 요청에 대해 회원 등록 단계별 처리 및 예외 처리를 담당.
 */
@Controller
public class RegisterController {

	private static final Log log = LogFactory.getLog(RegisterController.class);

	private MemberRegisterService memberRegisterService;

	/**
	 * method        : setMemberRegisterService
	 * date          : 24-12-17
	 * param         : MemberRegisterService memberRegisterService - 회원 등록 서비스를 주입받는 객체
	 * return        : void
	 * description   : MemberRegisterService를 주입받아 해당 서비스의 기능을 사용하도록 설정.
	 */

	public void setMemberRegisterService(
			MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}

	/**
	 * method        : handleStep1
	 * date          : 24-12-17
	 * param         : none
	 * return        : String - "register/step1" JSP 페이지 반환
	 * description   : 회원가입 첫 번째 단계 페이지로 이동.
	 *  chap13용 url :  http://localhost:8080/register/step1
	 */
	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}

	/**
	 * method        : handleStep2
	 * date          : 24-12-17
	 * param         : Boolean agree - 약관 동의 여부
	 *               : Model model - 뷰에 전달할 데이터를 저장하는 객체
	 * return        : String - 다음 단계 페이지 또는 현재 단계 페이지 반환
	 * description   : 약관 동의 여부를 확인하고, 동의 시 회원가입 두 번째 단계로 이동.
	 *                 동의하지 않을 경우 첫 번째 단계로 돌아감.
	 */
	@PostMapping("/register/step2")
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}


	/**
	 * method        : handleStep2Get
	 * date          : 24-12-17
	 * param         : none
	 * return        : String - 리다이렉트 URL 반환
	 * description   : GET 방식으로 두 번째 단계에 접근했을 때 첫 번째 단계로 리다이렉트.
	 */
	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}

	/**
	 * method        : handleStep3
	 * date          : 2024-12-26
	 * param         : RegisterRequest regReq - 회원 가입 요청 데이터를 담은 객체
	 * param         : Errors errors - 요청 데이터의 유효성 검증 결과를 담은 객체
	 * return        : String - 처리 결과에 따라 반환할 뷰의 이름
	 * description   : 회원 가입 3단계 처리를 담당. 입력 데이터를 검증하고, 중복 이메일 여부를 확인한 뒤,
	 *                 성공 시 회원 등록을 완료하고 완료 페이지로 이동.
	 *                 - 유효성 검사 실패: step2 페이지 반환
	 *                 - 중복 이메일 예외 발생: step2 페이지 반환, "email" 필드에 "duplicate" 오류 추가
	 *                 - 성공 시: step3 페이지 반환
	 */
	@PostMapping("/register/step3")
	public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {
		log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::handleStep3" );
		if (errors.hasErrors())        // 유효성 검사 결과에 오류가 있는 경우
			return "register/step2";   // 유효성 검증 실패 시 다시 step2 페이지 반환
		try {
			memberRegisterService.regist(regReq);  // 회원 등록 서비스 호출: 요청 데이터를 이용해 회원 정보 저장
			return "register/step3";               // 회원 등록 성공 시 step3 페이지 반환
		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");  // 중복 회원 예외 처리: "email" 필드에 "duplicate" 오류 추가
			// errors.reject("SampleCode_NotMatchPassword");  // 중복 회원 예외 처리: 글로벌 오류를 등록처리
			return "register/step2";							     // 예외 발생 시 step2 페이지 반환
		}
	}


	/**
	 * method        : handleStep3
	 * 	(실습용) handleStep3에서  Error 타입 파라미터를 제거한 메소드
	 */

	@PostMapping("/register/step3errors")
	public String step3errors(@Valid RegisterRequest regReq) {
		log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::step3errors" );
		try {
			memberRegisterService.regist(regReq);  // 회원 등록 서비스 호출: 요청 데이터를 이용해 회원 정보 저장
			return "register/step3";               // 회원 등록 성공 시 step3 페이지 반환
		} catch (DuplicateMemberException ex) {
			return "register/step2";							     // 예외 발생 시 step2 페이지 반환
		}
	}


	/**
	 * method        : initBinder (컨트롤러단의 검증, 로컬 검증 )
	 * date          : 2024-12-26
	 * param         : WebDataBinder binder - 요청 데이터를 바인딩하고 검증하는 WebDataBinder 객체
	 * return        : void
	 * description   : WebDataBinder에 RegisterRequestValidator를 설정하여 요청 데이터의 유효성 검증을 처리
	 * 유효성 검증 로직을 수정하고 싶은 경우에는 setValidator 부분만 수정해서 검증클래스를 변경하거나, 추가하거나 할 수 있음!!
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());   // RegisterRequestValidator를 WebDataBinder에 설정하여 유효성 검증 수행
	}




}

package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * class         : LoginCommandValidator
 * date          : 25-01-02
 * description   : 로그인 요청 데이터를 검증하는 클래스
 */
public class LoginCommandValidator implements Validator {

    /**
     * method        : supports
     * date          : 25-01-02
     * param         : Class<?> clazz - 검증 대상 클래스
     * return        : boolean - 검증할 수 있는 클래스 여부
     * description   : 검증 대상 클래스가 LoginCommand 클래스인지 확인한다.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginCommand.class.isAssignableFrom(clazz);
    }

    /**
     * method        : validate
     * date          : 25-01-02
     * param         : Object target - 검증 대상 객체, Errors errors - 검증 결과 저장 객체
     * return        : void
     * description   : 주어진 객체의 이메일과 비밀번호가 비어있거나 공백인지 검증하고, 오류가 있으면 Errors 객체에 등록한다.
     */
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
    }
}

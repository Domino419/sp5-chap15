package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * class         : ChangePwdCommandValidator
 * date          : 25-01-02
 * description   : 비밀번호 변경 요청 데이터를 검증하는 Validator 구현 클래스.
 *                 현재 비밀번호와 새 비밀번호 필드의 필수 입력 여부를 확인.
 */
public class ChangePwdCommandValidator implements Validator {

    /**
     * method        : supports
     * date          : 25-01-02
     * param         : Class<?> clazz - 검증 대상 클래스
     * return        : boolean - 지원 여부 (ChangePwdCommand에 대한 Validator인지 확인)
     * description   : 전달받은 클래스가 ChangePwdCommand와 호환되는지 검사.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePwdCommand.class.isAssignableFrom(clazz);
    }

    /**
     * method        : validate
     * date          : 25-01-02
     * param         : Object target - 검증 대상 객체, Errors errors - 검증 오류 저장 객체
     * return        : void
     * description   : ChangePwdCommand 객체의 필수 입력 필드(currentPassword, newPassword)를 검증.
     *                 currentPassword는 공백 또는 비어 있으면 오류 처리.
     *                 newPassword는 비어 있으면 오류 처리.
     */
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "currentPassword", "required");                      // 현재 비밀번호가 비었거나 공백인 경우 오류
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "required");    // 새 비밀번호가 비었을 경우 오류
    }
}

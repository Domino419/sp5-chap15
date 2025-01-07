package controller;

/**
 * class         : LoginCommand
 * date          : 25-01-02
 * description   : 로그인 요청 시 사용자의 이메일, 비밀번호 및 이메일 기억 여부를 저장하는 클래스
 */
public class LoginCommand {

    private String email ;
    private String password;
    private boolean rememberEmail ;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberEmail() {
        return rememberEmail;
    }

    public void setRememberEmail(boolean rememberEmail) {
        this.rememberEmail = rememberEmail;
    }




}

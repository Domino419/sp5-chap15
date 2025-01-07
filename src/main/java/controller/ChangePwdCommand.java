package controller;


/**
 * class         : ChangePwdCommand
 * date          : 25-01-02
 * description   : 비밀번호 변경 요청 데이터를 담는 커맨드 객체.
 *                 사용자가 입력한 현재 비밀번호와 새 비밀번호를 저장.
 */
public class ChangePwdCommand {

    private String currentPassword;
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

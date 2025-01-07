package spring;

/**
 * class         : AuthService
 * date          : 25-01-01
 * description   : 이메일과 비밀번호가 일치하는지 확인하고 Authinfo 객체를 생성한다.
 */
public class AuthService {

    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * method        : authenticate
     * date          : 25-01-02
     * param         : String email, String password
     * return        : Authinfo - 인증에 성공한 회원의 정보를 담은 객체
     * description   : 주어진 이메일과 비밀번호를 사용하여 회원을 인증. 인증 실패 시 예외를 발생시키고,
     *                 인증 성공 시 Authinfo 객체를 반환.
     */
    public AuthInfo authenticate(String email, String password) {
        Member member = memberDao.selectByEmail(email);
        if(member == null) {
            throw new WrongIdPasswordException() ;
        }
        if(!member.matchPassword(password)){
            throw new WrongIdPasswordException() ;
        }
        return new AuthInfo(member.getId(), member.getEmail(), member.getName()) ;
    }

}

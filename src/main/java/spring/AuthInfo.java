package spring;


/**
 * class         : AuthInfo
 * date          : 25-01-01
 * description   : 인증 상테 정보를 세션에 보관할 때 사용하는 클래스
 */
public class AuthInfo {

    private Long id ;
    private String email ;
    private String name ;

    public AuthInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}

package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * class         : AuthCheckInterceptor
 * date          : 25-01-06
 * description   : 로그인 인증 여부를 확인하는 인터셉터 클래스
 */
public class AuthCheckInterceptor implements HandlerInterceptor {


    /**
     * method        : preHandle
     * date          : 25-01-06
     * return        : boolean - 로그인 인증 여부에 따라 true 또는 false 반환
     * description   : 요청이 처리되기 전에 세션에서 인증정보를 확인
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        // 세션이 null 이 아니고, 인증 정보가 null 이 아니면 true
        if (session != null) {
            Object authinfo = session.getAttribute("authinfo");
            if(authinfo != null) {
                return true;
            }
        }
        // 그게 아니면 false
        response.sendRedirect(request.getContextPath() + "/login"); //  현재 애플리케이션의 루트 경로 + "/login" 경로로 리다이렉트
        return false;
    }

}

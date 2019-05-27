package guru.bonacci.ninetags2.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserInterceptor extends HandlerInterceptorAdapter {

	private static final String USER_DETAILS_HEADER = "Dear-User";
	
	private final FakeSecurityContext securityContext;

		
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
    	// leave swagger alone...
    	if (request.getServletPath().contains("swagger")) return true;
    	

    	val ourDearUser = request.getHeader(USER_DETAILS_HEADER);
    	if (ourDearUser == null) {
	        response.getWriter().write("You forgot the http-header '" + USER_DETAILS_HEADER + "'");
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        return false;
    	}    

    	securityContext.setAuthentication(ourDearUser);
        log.info("user(" + securityContext.getAuthentication() + ") authenticated");
        return true;
    }
}

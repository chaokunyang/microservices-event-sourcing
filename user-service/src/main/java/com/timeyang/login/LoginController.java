package com.timeyang.login;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangck
 */
@SessionAttributes("authorizationRequest")
@Controller
public class LoginController {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
        httpSessionSecurityContextRepository.loadContext(holder);

        try {
            // 使用提供的证书认证用户
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
            Authentication auth = new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"), authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));

            // 认证用户
            if(!auth.isAuthenticated())
                throw new CredentialException("用户不能够被认证");
        } catch (Exception ex) {
            // 用户不能够被认证，重定向回登录页
            logger.info(ex);
            return "login";
        }

        // 从会话得到默认保存的请求
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        // 为令牌请求生成认证参数Map
        Map<String, String> authParams = getAuthParameters(defaultSavedRequest);
        AuthorizationRequest authRequest = new DefaultOAuth2RequestFactory(clientDetailsService).createAuthorizationRequest(authParams);
        authRequest.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
        model.addAttribute("authorizationRequest", authRequest);

        httpSessionSecurityContextRepository.saveContext(SecurityContextHolder.getContext(), holder.getRequest(), holder.getResponse());
        return "authorize";
    }

    /**
     * 为会话的令牌请求生成认证参数Map
     * @param defaultSavedRequest 会话中默认保存的SPRING_SECURITY_SAVED_REQUEST请求
     * @return 包含OAuth2请求明细的参数Map
     */
    private Map<String,String> getAuthParameters(DefaultSavedRequest defaultSavedRequest) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put(OAuth2Utils.CLIENT_ID, defaultSavedRequest.getParameterMap().get(OAuth2Utils.CLIENT_ID)[0]);
        authParams.put(OAuth2Utils.REDIRECT_URI, defaultSavedRequest.getParameterMap().get(OAuth2Utils.REDIRECT_URI)[0]);
        if(defaultSavedRequest.getParameterMap().get(OAuth2Utils.STATE) != null) {
            authParams.put(OAuth2Utils.STATE, defaultSavedRequest.getParameterMap().get(OAuth2Utils.STATE)[0]);
        }

        authParams.put(OAuth2Utils.RESPONSE_TYPE, "code");
        authParams.put(OAuth2Utils.USER_OAUTH_APPROVAL, "true");
        authParams.put(OAuth2Utils.GRANT_TYPE, "authorization_code");

        return authParams;
    }

}

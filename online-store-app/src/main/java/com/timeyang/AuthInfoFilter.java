package com.timeyang;

/**
 * 添加是否已认证信息响应头，用于在前端判断是否获取受保护数据，因为获取受保护的数据会重定向到Oauth服务器，从而在js里面发生跨域，而且即使能够跨域，返回的也是登录页面的html代码，而不是受保护的资源
 * @author yangck
 */
//public class AuthInfoFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.isAuthenticated()) {
//            ((HttpServletResponse)response).setHeader("authenticated", "true");
//        }
//        chain.doFilter(request, response);
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}

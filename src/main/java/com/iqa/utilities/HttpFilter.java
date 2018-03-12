/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iqa.utilities;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author louis
 */
public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Context.ip.set(request.getRemoteAddr());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}

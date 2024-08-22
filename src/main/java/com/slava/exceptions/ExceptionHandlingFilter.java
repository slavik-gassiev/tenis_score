package com.slava.exceptions;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (PlayerNotFoundException e) {
            handleException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e, HttpServletResponse.SC_NOT_FOUND);
        }catch (MatchNotFoundException e) {
            handleException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e, HttpServletResponse.SC_NOT_FOUND);
        }catch (MatchesNotFoundException e) {
            handleException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e, HttpServletResponse.SC_NOT_FOUND);
        }catch (MatchValidationException e) {
            handleException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e, HttpServletResponse.SC_BAD_REQUEST);
        }catch (Exception e) {
            handleException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                 Exception e, int statusCode)
            throws IOException{
        servletResponse.setStatus(statusCode);
        servletRequest.setAttribute("errorMessage", e.getMessage());
        servletRequest.getRequestDispatcher("/error.jsp");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

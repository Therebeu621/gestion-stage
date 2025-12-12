package fr.univartois.stage.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to force session creation.
 * This ensures the session cookie is set before the response is committed,
 * avoiding "Cannot create a session after the response has been committed"
 * errors
 * caused by late running JAX-RS filters (like Krazo CSRF).
 */
public class ForceSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            ((HttpServletRequest) request).getSession(true);
        }

        chain.doFilter(request, response);
    }
}

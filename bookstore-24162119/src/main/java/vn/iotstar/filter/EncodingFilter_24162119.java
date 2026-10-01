package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.*;

public class
        EncodingFilter_24162119
        implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException,
                   ServletException {

        request.setCharacterEncoding(
                "UTF-8"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        chain.doFilter(
                request,
                response
        );
    }
}
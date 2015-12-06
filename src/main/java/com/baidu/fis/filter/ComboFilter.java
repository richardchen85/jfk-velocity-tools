package com.baidu.fis.filter;

import org.apache.velocity.runtime.directive.Foreach;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cdrd-clq on 2015/12/5.
 */
public class ComboFilter implements Filter {
    private String encode;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encode = filterConfig.getInitParameter("encode");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        if(req.getQueryString() != null && req.getQueryString().indexOf("?") == 0) {
            String[] files = req.getQueryString().substring(1).split(",");
            StringBuilder sb = new StringBuilder();
            for(String file:files) {
                String realPath = req.getSession().getServletContext().getRealPath("/" + file);
                try {
                    sb.append(readFile(realPath, file));
                } catch (Exception e) {
                    System.out.println("读取文件内容出错");
                    e.printStackTrace();
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(sb.toString());
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private String readFile(String filepath, String url) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(filepath);
        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), this.encode
            );
            BufferedReader bufferedReader = new BufferedReader(read);
            sb
                .append("/* ")
                .append(url)
                .append((new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss").format(new Date())))
                .append(" */\n");
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

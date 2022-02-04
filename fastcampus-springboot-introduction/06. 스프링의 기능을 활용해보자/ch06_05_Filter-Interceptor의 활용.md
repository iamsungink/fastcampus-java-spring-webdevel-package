# Filter-Interceptor의 활용

 * ### Filter
 ~~~
 @Slf4j
 @Component
 public class GlobalFilter implements Filter{

     @Override
     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

         //전처리
         //HttpServletRequest httpServetRequest = (HttpServletRequest) request;
         //HttpServletResponse httpServletResponse = (HttpServletResponse) response;

         ContentCachingRequestWrapper httpServetRequest = new ContentCachingRequestWrapper((HttpServetRequest) request);
         ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

         /*
         BufferedReader br = httpServletRequest.getReader();

         br.lines().forEach(line --> {
             log.info("url : {}, line : {}", url, line);
         });
         */

         chain.doFilter(httpServetRequest, httpServletResponse);

         String url = httpServletRequest.getRequestURI();

         //후처리
         String reqContent = new String(httpServletRequest.getContentAsByteArray());

         log.info("request url : {}, request body : {}", url, reqContent);

         String resContent = new String(httpServletResponse.getContentAsByteArray());
         int httpStatus = httpServletResponse.getStatus();

         httpServletResponse.copyBodyToResponse();

         log.info("response status : {}, responseBody : {}", httpStatus, resContent);

     }
 }
 ~~~

 * ### interceptor
 ~~~

 @Documented
 @Retention(RetentionPolicy.RUNTIME)
 @Target({ElementType.Type, ElementType.METHOD})
 public @interface Auth{

 }

 ~~~

 ~~~

@Slf4J
@Component
public class AuthInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServeltResponse response, Object handler) throws Exception{
        String url = request.getRequestURI();

        URI uri = UriComponentBuilder.fromUriString(request.getRequestURI()).query(request.getQueryString()).build().toUri();

        log.info("request url : {}, url);
        boolean hasAnnotaion = checkAnnotaion(handler, Auth.class);
        log.info("has annotaion : {}, hasAnnotation);

        // 나의 서버는 모두 public 으로 동작을 하는데,
        // 단! Auth 권한을 가진 요청에 대해서는 세션, 쿠키
        if( hasAnnotaion ){
            // 권한 체크
            String query = uri.getQuery():
            if(query.equlas("name=steve")){
                return truew;
            }

            throws AuthException("steve가 아님");

        }

        return true;
    }

    pulic boolean checkAnnotation(Object object, Class clazz){

        // resource javascript, html
        if( handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // annotaion check
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if( null != handlerMethod.getMethodAnnotaion(clazz) || null != handlerMethod.getBeanType().getAnnotaion(clazz)){

            // Auth annotaion이 있을떄는 true
            return ture;
        }
    }
}
~~~

~~~
@Configuration
@RequiredArgsContructor
public class MvcConfig implements WebMvcConfigurer{

    private final AuthInterceptor authInterceptor;

    @Override 
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPattern("/api/private/*");
    }
}
~~~

#### http://localhost:8080/api/private/hello?name=test
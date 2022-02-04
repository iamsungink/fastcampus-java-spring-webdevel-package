# Rest Template 사용하기

* ### client - GET
~~~
public class RestTemplateService{

    // http://localhost:9090/api/server/hello
    // response
    public UserReponse hello(){

        URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/hello")
                    .queryParam("name","aaaa")
                    .queryParam("age',99)
                    .encode()
                    .build()
                    .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri, String.class);
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }
}
~~~

* ### client - POST

 ~~~
public class RestTemplateService{

    // http://localhost:9090/api/server/hello
    // response
    public UserReponse hello(){

        URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/hello")
                    .queryParam("name","aaaa")
                    .queryParam("age',99)
                    .encode()
                    .build()
                    .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri, String.class);
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }

    public UserResponse post(){
        // http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/user/{userId}/name{userName}")
                    .encode()
                    .build()
                    .expand()
                    .expand(100, "stee")
                    .toUri();

        System.out.println(uri);

        // http body -> object -> objecdt mapper - json -> rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponseclass);

        System.out.println(result.getStatusCode());
        System.out.println(result.getHeaders());
        System.out.println(result.getBody());

        return response.getBody();


    }
    
}
~~~

* ### exchage

~~~
public class Req<T> {
    private Header header;
    private T body;

    public static class Header {
        
        private String responseCode;

        public String getResponseCode(){
            return resonseCode;
        }
        
        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }
    }

    public Header getHeader(){
        return header;
    }

    public void setHeader(Header header){
        this.header = header;
    }

    publc T getBody(){
        return body;
    }

    public void setBody(T body){
        this.body = body;
    }
}
~~~

~~~
public class RestTemplateService{

    // http://localhost:9090/api/server/hello
    // response
    public UserReponse hello(){

        URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/hello")
                    .queryParam("name","aaaa")
                    .queryParam("age',99)
                    .encode()
                    .build()
                    .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri, String.class);
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }

    public UserResponse post(){
        // http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/user/{userId}/name{userName}")
                    .encode()
                    .build()
                    .expand()
                    .expand(100, "stee")
                    .toUri();

        System.out.println(uri);

        // http body -> object -> objecdt mapper - json -> rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponseclass);

        System.out.println(result.getStatusCode());
        System.out.println(result.getHeaders());
        System.out.println(result.getBody());

        return response.getBody();


    }

    public UserResponse exchange(){

        URI uri = UriComponentBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/user/{userId}/name{userName}")
                    .encode()
                    .build()
                    .expand()
                    .expand(100, "stee")
                    .toUri();

        System.out.println(uri);

        // http body -> object -> objecdt mapper - json -> rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RequestEntity<UserRequest> requestEntity = RequestEntity.post(uri)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .header("x-authorization", "abce")
                                                                .header(custom-header","ffff")
                                                                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchage(requestEntity, UserResonse.class);

        return resonse.getBody();

    }

    public UserResponse genericExchagne(){

        URI uri = UriComponentBuilder
                    .fromUriString("http://localhost:9090")
                    .path("/api/server/user/{userId}/name{userName}")
                    .encode()
                    .build()
                    .expand()
                    .expand(100, "stee")
                    .toUri();

        System.out.println(uri);

        // http body -> object -> objecdt mapper - json -> rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        Req<userRequest> req = new Req<UserRequest>();
        req.setHeader(
            new Req.Heaeder()
        );

        req.setBody(
            userRequest
        );

        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity.post(uri)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .header("x-authorization", "abce")
                                                                .header(custom-header","ffff")
                                                                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Req<UserResponse>> response = restTemplate.exchage(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>(){});

        return resonse.getBody().getBody() ;



    }
}
~~~


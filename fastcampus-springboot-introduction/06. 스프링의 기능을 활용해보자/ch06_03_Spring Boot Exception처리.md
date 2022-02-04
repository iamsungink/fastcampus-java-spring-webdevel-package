# Spring Boot Exception 처리

* ### 에러페이지

* ### 4XX Error or 5XX Error

* ### Client가 200 외에 처리를 하지 못 할 때는 200을 내려주고 별도의 에러 Message 전달


~~~
 
 @RestControllerAdvice
 public class GlobalControllerAdvice{

     @ExceptionHandler(value = Exception.class)
     public ResponseEntity exception(Exception e){
         System.out.println(e.getClass().getName());
         System.out.println(e.getLocalizedMessage());

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
     }

     @ExceptionHandler(value = MethodArgumentNotValidException.class)
     public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
 }
~~~
#### http://localhost:8080/api/user
~~~
{
    "name" : "홍길동",
    "age" : 0
}
~~~
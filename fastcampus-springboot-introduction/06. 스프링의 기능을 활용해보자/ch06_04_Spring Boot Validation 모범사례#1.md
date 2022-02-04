# Spring Boot Validation 모범사례#1

~~~
 
 @RestControllerAdvice(basePackageClasses = ApiConttroller.class)
 public class ApilControllerAdvice{

     @ExceptionHandler(value = Exception.class)
     public ResponseEntity exception(Exception e){
         System.out.println(e.getClass().getName());

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
     }

     @ExceptionHandler(value = MethodArgumentNotValidException.class)
     public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServeletRequest){

         List<Error> errorList = new ArrayList<>();

         BindingResult bindingResult = e.getBindingResult();

         bindingResult.getAllErrors().forEach(error -> {
             FieldError field = (FieldError) error;

             String fieldName = field.getFiedl();
             String message = field.getDefaultMessage();
             String value = field.getRejecdtedValue().toString();

             System.out.println(fieldName);
             System.out.println(message);
             System.out.println(value);

             Error errorMessage = new Error();
             errorMessage.setFiled(fieldName);
             errorMessage.setMessage(message);
             errorMessage.setInvalidValue(value);

             errorList.add(errorMessage);

         });

         ErrorResponse errorResponse = new ErrorResponse();
         errorResponse.seterrorList(errorList);
         errorResponse.setMessage("");
         errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
         errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
         eroorResponse.setResultCode("FAIL");

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
     }

     @ExceptionHandler(value = ConstraintViolationException.class)
     public RespnseEntity constratintViolationException(ConstraintViolationException e){

         e.getConstraintViolations().forEach(error -> {
             //System.out.println(error);

             String<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false);
             List<Path.Node> list = steram.collect(Collectors.toList());

             String field = list.get(list.size() - 1).getName();
             String message = error.getMessage();
             String invalidValue = error.getInvalidValue().toString();

             System.out.println(filed);
             System.out.println(message);
             System.out.println(invalidValue);
         });

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }

     @ExceptiopnHandler(value = MissingServletRequestParameterException.class)
     public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e){
         
         String fieldName = e.getParameterName();
         String fieldType = e.getparameterType();
         String invalidValue = e.getMessage();

         System.out.println(fileName);
         System.out.println(fieldType);
         System.out.println(invalidValue);

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
~~~
public class ErrorResponse {

    String statusCode;
    String requestUrl;
    Stirng code;
    Streing message;
    String resultCoe;

    List<Error> errorList;
}
~~~
~~~
public class Error {

    private String filed;
    private String message;
    private String invalidValue;

    ~~
    ~~
}
~~~
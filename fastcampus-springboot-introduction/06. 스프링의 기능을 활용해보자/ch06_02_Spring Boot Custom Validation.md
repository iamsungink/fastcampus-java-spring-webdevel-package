# Spring Boot Custom Validation


### AsserTure / False 와 같은 method 지정을 통해서 Custom Logic 적용가능
* (메소드는 is~ 로 시작되어야함.)

 ~~~
 private String reqYearMonth;

 public String getReqYearMonth(){
     return reqYearMonth;
 }

 public void setReqYearMonth(String reqYearMonth){
     this.reqYearMonth = reqYearMonth;
 }

@AssertTrue(message = "yyyyMM 의 형식에 맞지 않습니다.)
public boolean isReqYearMonthValidation(){
    try{
        LocalDate localDate = LocalDate.parse(getReqYearMonth() + "01", DateTimeFormat.ofPattern("yyyyMMdd"));
    }catch (Exception e){
        return false;
    }
    return true;
}
 ~~~
#### http://localhost:8080/api/user
~~~
{
    "name" : "홍길동",
    "age" : 90,
    "email" : "test@test.com",
    "phoneNumber" : "010-1111-2222",
    "reqYearMonth" : "202104"
}
~~~

### ConstrantValidator를 적용하여 재사용이 가능한 Custom Logic  적용가능
#### customom annotation
~~~
@Constraint(validatedBy = {YearMonthValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface YearMonth {

    String message() default "yyyyMM 형식에 맞지 않습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String pattern() default "yyyyMMdd";
}

~~~
#### validtor
~~~
public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {

    private String pattern;

    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // yyyyMM
        try{
            LocalDate localDate = LocalDate.parse(value+"01" , DateTimeFormatter.ofPattern(this.pattern));
        }catch (Exception e){
            return false;
        }


        return true;
    }
}
~~~

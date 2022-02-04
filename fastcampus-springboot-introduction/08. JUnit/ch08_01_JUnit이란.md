# JUnit이란

### Mockito 라이브러리 추가

* [maven repository](https://mvnrepository.com/)
* [Mockito Core](https://mvnrepository.com/artifact/org.mockito/mockito-core)
``` 
// https://mvnrepository.com/artifact/org.mockito/mockito-core
testImplementation group: 'org.mockito', name: 'mockito-core', version: '3.12.4'
```
* [Mockito JUnit Jupiter](https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter)
```
// https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '3.12.4'

```
* build.gradle
~~~
dependencies {
    //testCompile group: 'junit', name: 'junit', version: '4.12'
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter', version: '5.7.1'
    testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '3.9.0'
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '3.9.0'
}
~~~

### mocking 처리
~~
@ExtendWith(MockitoExtention.class)
public class DollarCalculatorTest{

    @Test
    public void testHello(){
        System.out.println("hello");
    }

    @Test
    public void dollarTest(){
        MarketApi marketApi = new MarketApi();
        DollarCalculator dollarCalculator = new DollarCalculator(marketApi);
        dollarCalculator.init();

        Calculator calcualtor = new Calculator(dollarCalculator);
        Assertions.assertEquals(22000, calculator.sum(10, 10));
        Assertions.assertEquals(0, calculator.minus(10, 10));



    }
}
~~
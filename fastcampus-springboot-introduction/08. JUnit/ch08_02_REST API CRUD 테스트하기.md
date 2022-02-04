# REST API CRUD 테스트 하기

### get
~~~
@WebMvcTest({CalculatorApiController.class})
public class CalculatorApiContollerTest{

    @MockBean
    private MarketApi marketApi;

    @Autowired
    private Mockmvc mockMvc;

    @BeforeEach
    public void init(){
        Mockito.when(marketApi.connect()).thenReturn(3000);
    }

    @Test
    public void sumTest() throws Exception{
        //http://localhost:8080/api/sum

        mockMvc.perform(
            MockMvcRequestBuilders.get("http://localhost/api/sum")
                .queryParam("x", "10")
                .queryParam("y", "10")
        ).andExcpect(
            MockMvcResultMatchers.status().isOk()
        ).andExcpent(
            MockMvcResultMatchers.contents().string("60000")
        ).andDo(MockMvcResultHandlers.print);
    }


}
~~~

### post
~~~
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res{
    private int result;
    private Body response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body{
        private String resultCode = "OK";
    }
}
~~~

~~~
@WebMvcTest({CalculatorApiController.class})
public class CalculatorApiContollerTest{

    @MockBean
    private MarketApi marketApi;

    @Autowired
    private Mockmvc mockMvc;

    @BeforeEach
    public void init(){
        Mockito.when(marketApi.connect()).thenReturn(3000);
    }

    @Test
    public void sumTest() throws Exception{
        //http://localhost:8080/api/sum

        mockMvc.perform(
            MockMvcRequestBuilders.get("http://localhost/api/sum")
                .queryParam("x", "10")
                .queryParam("y", "10")
        ).andExcpect(
            MockMvcResultMatchers.status().isOk()
        ).andExcpent(
            MockMvcResultMatchers.contents().string("60000")
        ).andDo(MockMvcResultHandlers.print);
    }

    @Test
    public void minusTest() throws Exception{

        //http://localhost:8080/api/minus

        Req req = new Req();
        req.setX(10);
        req.setY(10);

        String json = new ObjectMapper().writeValueAsString(req);

        mockMvc.perform(
            MockMvcRequestBuilders.post("//http://localhost:8080/api/minus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.result").value("0")
        ).andExcpet(
            MockMvcResultMatchers.jsonPath("$.response.resultCode").value("OK")
        ).andDo(MockMvcResultHandlers.print());
    }


}
~~~


# REST API 개발하기

[NAVER OPEN API 검색](https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8
)

* application.yaml
~~~
naver:
  url:
    search:
      local: https://openapi.naver.com/v1/search/local.json
      image: https://openapi.naver.com/v1/search/image
  client:
    id: Zi3o1uQftp59zuIqEAz4
    secret: iy6YKSWpLM
~~~

* NaverClient
~~~
@Component
public class NaverClient {

    @Value(${naver.client.id})
    private String naverClientId;

    @Value(${naver.client.secret})
    private String naverClientSecret;

    @Value(${naver.url.search.local})
    private String naverLocalSearchUrl;

    @Value(${naver.url.search.image})
    privae String naverImageSearchUrl;

    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq){
        var uri = UriComponentBuilder.fromUriString(naverLocalSearchUrl)
                    .queryParams(searchLocalReq.toMultivalueMap())
                    .build()
                    .encode()
                    .toUri();

        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        var responseEntity = new RestTemplate().exchage(
            uri,
            HttpMethod.GET,
            httpEntity,
            responseType
        );

        return responseEntity.getBody();
    }


}
~~~

* SearchLocalReq
~~~
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchLocalReq{
    private String query = "";
    private int display = 1;
    private int start = 1;
    private String sort = "random"; 

    public MultiValueMap<String, String> toMultiValueMap(){
        var map = new LinkedMultiValueMap<String, String>;

        map.add("query", queryt);
        map.add("display, String.valueOf(display));
        map.add("start", String,valueOf(start));
        map.add("sort", sort);
        
        return map;
    }
}
~~~

* SearchLocalRes
~~~
@NoArgsConstructor
@AllArgsConsturctor
@Data
public class SearchLocalRes{
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private String category;
    private List<SearchLocalItem> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    publi static class SearchLocalItem{
        private String title;
        private String link;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private int mapx;
        private int mapy;

    }
}
~~~

* NaverClientTest
~~~
@SpringBootTest
public class NaverClientTest{

    @Autowired
    private NaverClient naverClient;

    @Test
    public void searchLocalTest(){

        var search = new SearchLocalReq();
        search.setQuery("갈비집");

        var result = naverClient.searchLocal(search);



    }
}
~~~

* WishListService
~~~
@Service
@RequiredArgsConstructor
public class WishListService{

    private final NaverClient naverClient;
    
    public WishListDto search(String query){

        //지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() > 0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>,"");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            //이미지검색
            var searchImageRes = naverClient.searchImage(searchImageReq);
            
            if(searchImageRes.getTotal()>0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                //결과리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }

        return new WishListDto();
    }
}
~~~

* WishListServiceTest
~~~
@SpringBootTest
public class WishListServiceTest{

    @Autowired
    private WishListService wishListService;

    @Test
    public void searchTest(){
        var result = wishListService.search("갈비");
    }
}

~~~

* ApiController
~~~
@RestConroller
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class ApiContorller{

    private final WishListService wishListService;

    @GetMapping("/search")
    public WishListDto search(@RequestParam String query){
        return wishListService.search(query);
    }

    @PostMapping("/")
    public WishListDto add(@RequestBody WishListDto wishListDto){
        log.info("{}", wishListDto);

        return wishListService.add(wishListDto);
    }
}

~~~

### swagger
* build.gradle 
~~~
dependencies {
    implementation group : 'io.springfox', name : 'springfox-boot-starter', version : '3.0.0.'
}
~~~

* localhost:8080/swagger-ui/
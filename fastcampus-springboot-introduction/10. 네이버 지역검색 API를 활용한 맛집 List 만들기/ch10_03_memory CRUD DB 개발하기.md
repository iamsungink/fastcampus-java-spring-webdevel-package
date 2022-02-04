# 네이버 지역검색 API를 활용한 맛집 LIST 만들기

### Memory CRUD DB 개발하기

* build.gradle
~~~
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	// https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter
	implementation group: 'io.springfox', name: 'springfox-boot-starter', version: '3.0.0'
}
~~~

* MemoryDbRepositoryIfs
~~~
public class MemoryDbRepositoryIfs<T> {

    Optionnal<T> findById(int index);
    T save(T entitty);
    void deleteById(int index);
    List<T> listAll();
}
~~~

* MemoryDbRepositoryAbstract
~~~
abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T> {

    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index){
        return db.stream().filter(it -> it.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity){
        var optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpoty()){
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;
        }else{
            var preIndex = optionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
            db.add(entity);
            return entity;
        }
    }

    @Override
    public void deleteById(int index){
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if(optionalEntity.isPresent()){
            db.remove(optionalEntity.get());
        }

    }

    @Override
    public List<T> listAll(){
        return db;
    }
}
~~~

* MemoryDbEntity
~~~
@NoArgsConstrunctor
@AllArgsConstructor
@Data
public class MemoryDbEnteity{
    private int index;
}
~~~

* WishListEntity
~~~
@NoArgsConstructor
@AllArgsConstructor
@Daata
public class WishListEntity extends MemoryDbEntity {
    private String titile;
    private String category;
    private String address;
    private String readAddress;
    private String homePageLink;
    private String imageLink;
    private boolean isVisit;
    private int visitCount;
    private LocalDateTime lastVisitDate;

}
~~~

* WishListRepository
~~~
@Repository
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {

}
~~~

* WishListRepositoryTest
~~~
@SpringBootTest
public class WishListRepositoryTest {

    private WishListEntity create(){
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setReadAddress("readAddress");
        wishList.setHomePageLink("");
        wishList.setImageLink("");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        wishList.setLastVisitDate(null);

        return wishList;
    }

    @Autowired
    private WishListRepository wishListRepository;

    public void saveTest(){
        var wishListEntity = create();
        var expected = wishListRepository.save(wishListEntity);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(1, expented.getIndex());

    }

    public void findByIdTest(){
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        var expected = wishListRepository.findById(1);

        Assertions.assertEquals(true, expected.isPresent());
        Assertions.assertEquals(1, expectd.get().getIndex());

    }

    public void deleteByIdTest(){
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        wishListRepository.deleteById(1);

        int count = wishListRepository.listAll().size();

        Assertions.assertEquals(0, count);

    }

    public void listAllTest(){

        var wishListEntity1 = create();
        wishListRepository.save(wishListEntity1);

        var wishListEntity2 = create();
        wishListRepository.save(wishListEntity2);

        int count = wishListRepository.listAll().size();

        Assertions.assertEquals(2, count);

    }
}
~~~
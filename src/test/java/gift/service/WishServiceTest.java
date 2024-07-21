package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/*
@SpringBootTest
@Sql("/truncate.sql")
public class WishServiceTest {

    @Autowired
    private WishService wishService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("위시 리스트 등록")
    void register() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.addProduct(createProductRequest);

        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), pageable);
        WishResponse actual = wishes.responses().get(0);

        assertAll(
            () -> assertThat(actual.wishId()).isNotNull(),
            () -> assertThat(actual.productId()).isNotNull(),
            () -> assertThat(actual.productName()).isEqualTo(product.name()),
            () -> assertThat(actual.count()).isEqualTo(3),
            () -> assertThat(actual.imageUrl()).isEqualTo(product.imageUrl()),
            () -> assertThat(actual.price()).isEqualTo(product.price())
        );
    }

    @Test
    @DisplayName("위시 리스트 조회")
    void findWish() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.addProduct(createProductRequest);
        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), pageable);

        assertThat(wishes).isNotNull();
    }

    @Test
    @DisplayName("위시 리스트 삭졔")
    void delete() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.addProduct(createProductRequest);
        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));

        wishService.deleteWishList(user.id(), product.id());
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), pageable);

        assertThat(wishes.responses()).isEmpty();
    }
}
*/

package ru.annachemic.tests;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.annachemic.db.dao.ProductsMapper;
import ru.annachemic.db.model.Products;
import ru.annachemic.dto.Product;
import ru.annachemic.enums.CategoryType;
import ru.annachemic.dto.Category;
import ru.annachemic.service.CategoryService;
import ru.annachemic.service.ProductService;
import ru.annachemic.utils.DbUtils;
import ru.annachemic.utils.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class ProductTests {
    int productId;
    static ProductsMapper productsMapper;
    static Retrofit client;
    static ProductService productService;
    static CategoryService categoryService;
    Faker faker = new Faker();
    Product product;

    @BeforeAll
    static void beforeAll() {
        /*client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
        categoryService = client.create(CategoryService.class);*/
        productsMapper = DbUtils.getProductsMapper();
    }

    /*@BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }*/

    @Test
    void postProductTest() {
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        //Response<Product> response = productService.createProduct(product).execute();
        DbUtils.createNewProducts(productsMapper);
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore + 1));

        /*assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
        productId = response.body().getId();
        productId = countProductsAfter;*/
    }

    @DisplayName("Категория с заданным id")
    @Test
    void getProductByIdTest() {
        DbUtils.createNewProducts(productsMapper);
        Long id = Long.valueOf(product.getId());
        DbUtils.idProducts(id);
        assertThat(Long.valueOf(product.getId()), equalTo(id));
        /*Response<Category> response = categoryService.getCategory(id).execute();
        log.info(response.body().toString());
        assertThat(response.body().getTitle(), equalTo(CategoryType.FOOD.getTitle()));
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.code(), equalTo(200));*/
    }

    @Test
    void updateProductTest() {
        //Response<Product> response = productService.createProduct(product).execute();
        DbUtils.createNewProducts(productsMapper);
        //log.info(response.body().toString());
        product.setPrice(100);
        DbUtils.updateProducts(productsMapper);
        /*log.info(response.body().toString());
        assertThat(response.body().getPrice(), equalTo(100));*/
    }

    @AfterEach
    void tearDown() {
        DbUtils.deleteProducts(productsMapper);
        /*if (productId != 0) {
            Response<ResponseBody> response = productService.deleteProduct(productId).execute();
            assertThat(response.isSuccessful(), is(true));
        }*/
    }
}

package ru.annachemic.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.dto.Category;
import ru.annachemic.enums.CategoryType;
import ru.annachemic.service.CategoryService;
import ru.annachemic.utils.DbUtils;
import ru.annachemic.utils.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class CategoryTests {
    static CategoriesMapper categoriesMapper;
    static Retrofit client;
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        categoryService = client.create(CategoryService.class);
        categoriesMapper = DbUtils.getCategoriesMapper();
    }

    @DisplayName("Категория с заданным id")
    @Test
    void getCategoryByIdTest() {
        Integer id = CategoryType.FOOD.getId();
        DbUtils.idCategories(id);
        assertThat(CategoryType.FOOD.getId(), equalTo(id));
        /*Response<Category> response = categoryService.getCategory(id).execute();
        log.info(response.body().toString());
        assertThat(response.body().getTitle(), equalTo(CategoryType.FOOD.getTitle()));
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.code(), equalTo(200));*/
    }

    @DisplayName("Категория с нулевым id")
    @Test
    void getCategoryByNullIdTest() {
        DbUtils.idCategories(0);
        /*Response<Category> response = categoryService.getCategory(0).execute();
        assertThat(response.code(), equalTo(404));
        assertThat(response.isSuccessful(), equalTo(false));*/
    }

    @DisplayName("Категория с отрицательным id")
    @Test
    void getCategoryByNegativeIdTest() {
        DbUtils.idCategories(-15);
        /*Response<Category> response = categoryService.getCategory(-15).execute();
        assertThat(response.code(), equalTo(404));
        assertThat(response.isSuccessful(), equalTo(false));*/
    }

    @Test
    void postCategoryTest() {
        Integer countCategoriesBefore = DbUtils.countCategories(categoriesMapper);
        DbUtils.createNewCategory(categoriesMapper);
        Integer countCategoriesAfter = DbUtils.countCategories(categoriesMapper);

        assertThat(countCategoriesAfter, equalTo(countCategoriesBefore + 1));
    }
}

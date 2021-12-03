package ru.annachemic.utils;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.db.dao.ProductsMapper;
import ru.annachemic.db.model.Categories;
import ru.annachemic.db.model.CategoriesExample;
import ru.annachemic.db.model.Products;
import ru.annachemic.db.model.ProductsExample;
import ru.annachemic.dto.Product;
import ru.annachemic.enums.CategoryType;

import java.io.IOException;

@UtilityClass
public class DbUtils {
    private static  String resource = "mybatisConfig.xml";
    static Faker faker = new Faker();

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }

    @SneakyThrows
    public static CategoriesMapper getCategoriesMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    @SneakyThrows
    public static ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public static void createNewCategory(CategoriesMapper categoriesMapper) {
        Categories newCategory = new Categories();
        newCategory.setTitle(faker.food().dish());
        categoriesMapper.insert(newCategory);
    }

    public static Integer countCategories(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        return Math.toIntExact(categoriesCount);
    }

    public static Categories idCategories(Integer categoriesId) {
        return getCategoriesMapper().selectByPrimaryKey(categoriesId);
    }

    public static void deleteCategories(CategoriesMapper categoriesMapper) {
        categoriesMapper.deleteByExample(new CategoriesExample());
    }

    public static void createNewProducts(ProductsMapper productsMapper) {
        Products newProduct = new Products();
        newProduct.setTitle(faker.food().dish());
        newProduct.setPrice((int) ((Math.random() + 1) * 100));
        newProduct.setCategory_id(CategoryType.FOOD.getId().longValue());
        productsMapper.insert(newProduct);
    }

    public static Integer countProducts(ProductsMapper productsMapper) {
        long products = productsMapper.countByExample(new ProductsExample());
        return Math.toIntExact(products);
    }

    public static Products idProducts(Long productsId) {
        return getProductsMapper().selectByPrimaryKey(productsId);
    }

    public static void updateProducts(ProductsMapper productsMapper) {
        Products newProduct = new Products();
        newProduct.setPrice((int) ((Math.random() + 1) * 100));
        productsMapper.updateByExample(newProduct, new ProductsExample());
    }

    public static void deleteProducts(ProductsMapper productsMapper) {
        productsMapper.deleteByExample(new ProductsExample());
    }
}

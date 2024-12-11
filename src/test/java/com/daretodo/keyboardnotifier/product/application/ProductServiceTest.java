package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.Period;
import com.daretodo.keyboardnotifier.product.domain.PriceUnit;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void 유사한_상품을_조회한다() {
        // given
        Long id = 1L;
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        Product product1 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 1L)
                .set(javaGetter(Product::getName), "Product A")
                .set(javaGetter(Product::getProductType), ProductType.KEYBOARD)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)))
                .sample();
        Product product2 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 2L)
                .set(javaGetter(Product::getName), "Product B")
                .set(javaGetter(Product::getProductType), ProductType.PARTS)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2)))
                .sample();
        Product product3 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 3L)
                .set(javaGetter(Product::getName), "Product C")
                .set(javaGetter(Product::getProductType), ProductType.KEYCAP)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3)))
                .sample();
        Product product4 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 4L)
                .set(javaGetter(Product::getName), "Product D")
                .set(javaGetter(Product::getProductType), ProductType.KEYBOARD)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(4), LocalDateTime.now().plusDays(4)))
                .sample();
        Product product5 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 5L)
                .set(javaGetter(Product::getName), "Product E")
                .set(javaGetter(Product::getProductType), ProductType.KEYBOARD)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5)))
                .sample();


        List<ProductEntity> productEntities = List.of(ProductEntity.fromDomain(product4), ProductEntity.fromDomain(product5));
        when(productRepository.findSimilarProducts(id)).thenReturn(productEntities);

        // when
        var result = productService.findSimilarProducts(id);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("Product D");
        assertThat(result.get(1).name()).isEqualTo("Product E");
    }
}

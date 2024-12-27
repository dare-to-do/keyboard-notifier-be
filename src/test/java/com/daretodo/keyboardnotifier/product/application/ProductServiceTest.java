package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.application.dto.ProductResponse;
import com.daretodo.keyboardnotifier.product.domain.*;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void 유사한_상품을_조회한다() {
        // given
        Long id = 1L;
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        Product product4 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getName), "Product D")
                .set(javaGetter(Product::getType), ProductType.KEYBOARD)
                .set(javaGetter(Product::getPriceUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(4), LocalDateTime.now().plusDays(4)))
                .sample();
        Product product5 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getName), "Product E")
                .set(javaGetter(Product::getType), ProductType.KEYBOARD)
                .set(javaGetter(Product::getPriceUnit), PriceUnit.KRW)
                .set(javaGetter(Product::getPeriod), Period.of(LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5)))
                .sample();

        List<Product> products = List.of(product4, product5);
        when(productRepository.findSimilarProducts(id)).thenReturn(products);
        when(productMapper.toProductResponseList(products))
                .thenReturn(List.of(ProductResponse.fromDomain(product4), ProductResponse.fromDomain(product5)));

        // when
        var result = productService.findSimilarProducts(id);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("Product D");
        assertThat(result.get(1).name()).isEqualTo("Product E");
    }
}

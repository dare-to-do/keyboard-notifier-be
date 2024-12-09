package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.PriceUnit;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class ProductServiceMultiThreadTest {

    @Autowired
    private ProductService sut;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품_조회_실패시_재시도한다() throws InterruptedException {
        // given
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        Product product = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 1L)
                .set(javaGetter(Product::getName), "Product A")
                .set(javaGetter(Product::getPrice), 1000L)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();
        List<Product> products = List.of(product);
        productRepository.saveAll(products);

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        int MAX_RETRY_COUNT = 3;
        AtomicInteger tryCount = new AtomicInteger();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                int retryCount = 0;
                boolean success = false;
                while (!success && retryCount <= MAX_RETRY_COUNT) {
                    try {
                        tryCount.getAndIncrement();
                        sut.findProduct(1L);
                        success = true;
                    } catch (OptimisticLockingFailureException e) {
                        log.info("Thread: {}, Retry: {}", Thread.currentThread().getId(), retryCount++);
                    } finally {
                        if (success || retryCount > MAX_RETRY_COUNT) {
                            latch.countDown();
                        }
                    }
                }
            });
        }

        latch.await();

        // then
        assertThat(tryCount.get()).isGreaterThan(threadCount);
    }
}

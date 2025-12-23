package com.midlevel.orderfulfillment.adapter.out.persistence;

import com.midlevel.orderfulfillment.domain.model.*;
import com.midlevel.orderfulfillment.domain.port.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for OrderRepository using Testcontainers.
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * 
 * 1. TESTCONTAINERS:
 *    - Spins up real PostgreSQL database in Docker
 *    - Each test run gets fresh database
 *    - Tests run against actual database, not mocks
 *    - Ensures JPA mappings work correctly
 * 
 * 2. @SpringBootTest:
 *    - Loads full Spring application context
 *    - All beans are wired up (repository, data source, etc.)
 *    - Tests the complete integration stack
 * 
 * 3. @Testcontainers:
 *    - Manages container lifecycle automatically
 *    - Starts container before tests
 *    - Stops container after tests
 * 
 * 4. @DynamicPropertySource:
 *    - Configures Spring to use Testcontainers database
 *    - Overrides application.yml database settings
 *    - Sets URL, username, password from running container
 */
@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryIntegrationTest {
    
    /**
     * PostgreSQL Testcontainer
     * 
     * @Container tells Testcontainers to manage this container
     * static ensures one container for all tests (faster)
     * 
     * The container is started automatically before first test
     * and stopped after last test.
     */
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    
    /**
     * Dynamically configure Spring to use the Testcontainers database.
     * 
     * This method is called before Spring context initialization.
     * It overrides the datasource properties from application.yml.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    /**
     * The repository we're testing.
     * Spring injects the actual implementation (OrderRepositoryAdapter).
     */
    @Autowired
    private OrderRepository orderRepository;
    
    // Test data - reused across tests
    private Order testOrder;
    private String customerId = "CUST001";
    private List<OrderItem> items;
    private Address shippingAddress;
    
    /**
     * Setup method run before each test.
     * Creates fresh test data for isolation between tests.
     */
    @BeforeEach
    void setUp() {
        // Create test items
        items = List.of(
                OrderItem.of("PROD001", "Laptop", Money.usd(BigDecimal.valueOf(999.99)), 1),
                OrderItem.of("PROD002", "Mouse", Money.usd(BigDecimal.valueOf(29.99)), 2)
        );
        
        // Create test address
        shippingAddress = Address.of(
                "123 Main St",
                "Springfield",
                "IL",
                "62701",
                "US"
        );
        
        // Create test order
        testOrder = Order.create(customerId, items, shippingAddress);
    }
    
    /**
     * Test: Verify Testcontainers is working
     */
    @Test
    @Order(1)
    @DisplayName("Should have PostgreSQL container running")
    void testContainerIsRunning() {
        assertThat(postgres.isRunning()).isTrue();
        assertThat(postgres.getDatabaseName()).isEqualTo("testdb");
    }
    
    /**
     * Test: Save a new order
     * Verifies: JPA save operation and entity mapping
     */
    @Test
    @Order(2)
    @DisplayName("Should save a new order to database")
    void testSaveOrder() {
        // When
        Order savedOrder = orderRepository.save(testOrder);
        
        // Then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderId()).isEqualTo(testOrder.getOrderId());
        assertThat(savedOrder.getCustomerId()).isEqualTo(customerId);
        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(savedOrder.getItems()).hasSize(2);
        assertThat(savedOrder.getShippingAddress()).isNotNull();
    }
    
    /**
     * Test: Find order by ID
     * Verifies: JPA query and entity-to-domain conversion
     */
    @Test
    @Order(3)
    @DisplayName("Should find order by ID")
    void testFindById() {
        // Given - save an order first
        Order savedOrder = orderRepository.save(testOrder);
        
        // When
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getOrderId());
        
        // Then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getOrderId()).isEqualTo(savedOrder.getOrderId());
        assertThat(foundOrder.get().getCustomerId()).isEqualTo(customerId);
        assertThat(foundOrder.get().getItems()).hasSize(2);
    }
    
    /**
     * Test: Find order by non-existent ID
     * Verifies: Proper handling of not found case
     */
    @Test
    @Order(4)
    @DisplayName("Should return empty Optional when order not found")
    void testFindByIdNotFound() {
        // When
        Optional<Order> foundOrder = orderRepository.findById("NON_EXISTENT_ID");
        
        // Then
        assertThat(foundOrder).isEmpty();
    }
    
    /**
     * Test: Find orders by customer ID
     * Verifies: Custom query methods work correctly
     */
    @Test
    @Order(5)
    @DisplayName("Should find all orders for a customer")
    void testFindByCustomerId() {
        // Given - save multiple orders for same customer
        orderRepository.save(testOrder);
        
        Order anotherOrder = Order.create(customerId, items, shippingAddress);
        orderRepository.save(anotherOrder);
        
        // When
        List<Order> customerOrders = orderRepository.findByCustomerId(customerId);
        
        // Then
        assertThat(customerOrders).hasSize(2);
        assertThat(customerOrders)
                .extracting(Order::getCustomerId)
                .containsOnly(customerId);
    }
    
    /**
     * Test: Find orders by status
     * Verifies: Enum-based queries work correctly
     */
    @Test
    @Order(6)
    @DisplayName("Should find orders by status")
    void testFindByStatus() {
        // Given - save orders with different statuses
        Order createdOrder = orderRepository.save(testOrder);
        
        Order paidOrder = Order.create("CUST002", items, shippingAddress);
        paidOrder.pay();
        orderRepository.save(paidOrder);
        
        // When
        List<Order> createdOrders = orderRepository.findByStatus(OrderStatus.CREATED);
        List<Order> paidOrders = orderRepository.findByStatus(OrderStatus.PAID);
        
        // Then
        assertThat(createdOrders).hasSizeGreaterThanOrEqualTo(1);
        assertThat(createdOrders)
                .extracting(Order::getStatus)
                .containsOnly(OrderStatus.CREATED);
        
        assertThat(paidOrders).hasSizeGreaterThanOrEqualTo(1);
        assertThat(paidOrders)
                .extracting(Order::getStatus)
                .containsOnly(OrderStatus.PAID);
    }
    
    /**
     * Test: Update existing order
     * Verifies: JPA update operations and state changes persist
     */
    @Test
    @Order(7)
    @DisplayName("Should update order status and persist changes")
    void testUpdateOrder() {
        // Given - save an order
        Order savedOrder = orderRepository.save(testOrder);
        String orderId = savedOrder.getOrderId();
        
        // When - pay the order and save
        savedOrder.pay();
        orderRepository.save(savedOrder);
        
        // Then - reload and verify changes persisted
        Order reloadedOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(reloadedOrder.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(reloadedOrder.getPaidAt()).isNotNull();
    }
    
    /**
     * Test: Delete order
     * Verifies: Cascade delete works for order items
     */
    @Test
    @Order(8)
    @DisplayName("Should delete order and cascade to items")
    void testDeleteOrder() {
        // Given - save an order
        Order savedOrder = orderRepository.save(testOrder);
        String orderId = savedOrder.getOrderId();
        
        assertThat(orderRepository.existsById(orderId)).isTrue();
        
        // When
        orderRepository.deleteById(orderId);
        
        // Then
        assertThat(orderRepository.existsById(orderId)).isFalse();
        assertThat(orderRepository.findById(orderId)).isEmpty();
    }
    
    /**
     * Test: Find all orders
     * Verifies: Bulk read operations
     */
    @Test
    @Order(9)
    @DisplayName("Should find all orders in database")
    void testFindAll() {
        // Given - save multiple orders
        orderRepository.save(testOrder);
        
        Order order2 = Order.create("CUST003", items, shippingAddress);
        orderRepository.save(order2);
        
        // When
        List<Order> allOrders = orderRepository.findAll();
        
        // Then
        assertThat(allOrders).hasSizeGreaterThanOrEqualTo(2);
    }
    
    /**
     * Test: Order items are persisted correctly
     * Verifies: One-to-many relationship and embedded objects
     */
    @Test
    @Order(10)
    @DisplayName("Should persist order items with correct values")
    void testOrderItemsPersistence() {
        // Given
        Order savedOrder = orderRepository.save(testOrder);
        
        // When
        Order reloadedOrder = orderRepository.findById(savedOrder.getOrderId()).orElseThrow();
        
        // Then
        assertThat(reloadedOrder.getItems()).hasSize(2);
        
        OrderItem firstItem = reloadedOrder.getItems().get(0);
        assertThat(firstItem.getProductId()).isEqualTo("PROD001");
        assertThat(firstItem.getProductName()).isEqualTo("Laptop");
        assertThat(firstItem.getUnitPrice().getAmount()).isEqualByComparingTo("999.99");
        assertThat(firstItem.getQuantity()).isEqualTo(1);
    }
    
    /**
     * Test: Address is embedded correctly
     * Verifies: @Embeddable mapping works
     */
    @Test
    @Order(11)
    @DisplayName("Should persist embedded address correctly")
    void testAddressEmbedding() {
        // Given
        Order savedOrder = orderRepository.save(testOrder);
        
        // When
        Order reloadedOrder = orderRepository.findById(savedOrder.getOrderId()).orElseThrow();
        
        // Then
        Address address = reloadedOrder.getShippingAddress();
        assertThat(address.getStreet()).isEqualTo("123 Main St");
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getState()).isEqualTo("IL");
        assertThat(address.getZipCode()).isEqualTo("62701");
        assertThat(address.getCountry()).isEqualTo("US");
    }
    
    /**
     * Test: Verify complete order lifecycle can be persisted
     * Verifies: All state transitions persist correctly
     */
    @Test
    @Order(12)
    @DisplayName("Should persist complete order lifecycle")
    void testCompleteOrderLifecycle() {
        // Create order
        Order order = orderRepository.save(testOrder);
        String orderId = order.getOrderId();
        
        // Pay order
        order.pay();
        orderRepository.save(order);
        
        Order paidOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(paidOrder.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(paidOrder.getPaidAt()).isNotNull();
        
        // Ship order
        paidOrder.ship();
        orderRepository.save(paidOrder);
        
        Order shippedOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(shippedOrder.getStatus()).isEqualTo(OrderStatus.SHIPPED);
        assertThat(shippedOrder.getShippedAt()).isNotNull();
    }
}

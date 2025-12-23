package com.midlevel.orderfulfillment.adapter.out.persistence;

import com.midlevel.orderfulfillment.adapter.out.persistence.entity.OrderEntity;
import com.midlevel.orderfulfillment.domain.model.Order;
import com.midlevel.orderfulfillment.domain.model.OrderStatus;
import com.midlevel.orderfulfillment.domain.port.OrderRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation that bridges domain OrderRepository port
 * to Spring Data JPA repository.
 * 
 * HEXAGONAL ARCHITECTURE:
 * This is the "Adapter" that implements the domain "Port"
 * 
 * Responsibilities:
 * - Translate between domain objects and JPA entities
 * - Delegate persistence operations to Spring Data repository
 * - Handle entity/domain conversions
 * 
 * @Component makes this a Spring bean that can be injected
 */
@Component
public class OrderRepositoryAdapter implements OrderRepository {
    
    private final JpaOrderRepository jpaOrderRepository;
    
    /**
     * Constructor injection (preferred over field injection)
     * Spring automatically injects JpaOrderRepository implementation
     */
    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }
    
    @Override
    public Order save(Order order) {
        // Convert domain Order to JPA entity
        OrderEntity entity = OrderEntity.fromDomain(order);
        
        // Save using Spring Data JPA
        OrderEntity savedEntity = jpaOrderRepository.save(entity);
        
        // Convert back to domain and return
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<Order> findById(String orderId) {
        // Find entity using Spring Data JPA
        return jpaOrderRepository.findById(orderId)
                // Convert entity to domain if found
                .map(OrderEntity::toDomain);
    }
    
    @Override
    public List<Order> findByCustomerId(String customerId) {
        // Find entities and convert to domain list
        return jpaOrderRepository.findByCustomerId(customerId).stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return jpaOrderRepository.findByStatus(status).stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(String orderId) {
        jpaOrderRepository.deleteById(orderId);
    }
    
    @Override
    public boolean existsById(String orderId) {
        return jpaOrderRepository.existsById(orderId);
    }
}

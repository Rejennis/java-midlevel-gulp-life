# 🏃‍♂️ Java Mid-Level Sprint Program: Order Fulfillment System
## **Agile/Scrum Edition - One Week Sprints**

> **Philosophy**: Ship working software every week. Real Scrum. Real team dynamics. Real production features.

---

## 📋 Program Overview

**Duration**: 5 Weeks (5 Sprints)  
**Sprint Length**: 1 Week (5 working days)  
**Team Structure**: Scrum Master + Product Owner + Developers (1-3 people)  
**Delivery Model**: Production-ready increments at end of each sprint  
**Output**: Full-featured Order Fulfillment System with Real-World Payment & Shipping

### Your Team Roles

#### 🎯 Product Owner (PO)
**Responsibilities:**
- Owns and prioritizes the Product Backlog
- Defines acceptance criteria for user stories
- Makes scope decisions during sprint
- Accepts or rejects sprint deliverables
- Represents customer needs

**Key Activities:**
- Write user stories with business value
- Participate in sprint planning and reviews
- Answer clarification questions during sprint
- Demo features to stakeholders

#### 🏃 Scrum Master (SM)
**Responsibilities:**
- Facilitates all Scrum ceremonies
- Removes impediments/blockers
- Coaches team on Agile practices
- Protects team from disruptions
- Tracks sprint metrics

**Key Activities:**
- Run daily standups (15 min)
- Facilitate retrospectives
- Monitor sprint progress (burndown)
- Help resolve technical blockers
- Ensure Definition of Done is met

#### 💻 Developers (Dev Team)
**Responsibilities:**
- Self-organize to complete sprint backlog
- Write production-quality code
- Create comprehensive tests
- Participate in code reviews
- Update task status daily

**Key Activities:**
- Estimate story points in planning
- Commit to sprint goals
- Pair programming (optional)
- Deploy working increments
- Improve engineering practices

---

## 🎯 Product Vision & Goals

### Vision Statement
*"Build a production-ready Order Fulfillment System that handles real payments (Stripe/PayPal), real shipping (FedEx/UPS), and scales to thousands of orders per day."*

### Product Goals
1. **Business Value**: Process real customer orders from creation to delivery
2. **Payment Integration**: Accept multiple payment methods securely
3. **Shipping Integration**: Calculate rates and track deliveries with real carriers
4. **Production Quality**: Observable, resilient, and maintainable
5. **Developer Experience**: Well-documented, testable, and extensible

### Success Metrics
- ✅ 100% of critical user journeys working
- ✅ 80%+ test coverage
- ✅ Green CI/CD pipeline
- ✅ < 2 second average API response time
- ✅ Zero P1 bugs in production

---

## 📦 Product Backlog (Epic Level)

### Epic 1: Core Order Management 🟢 (Sprint 1)
*"As a customer, I can create, pay for, and track my orders through their complete lifecycle."*

**Business Value:** Foundation for all order operations  
**Estimated Effort:** 21 Story Points

### Epic 2: Payment Gateway Integration 💳 (Sprint 2)
*"As a customer, I can pay for orders using credit cards, PayPal, or Apple Pay securely."*

**Business Value:** Revenue generation capability  
**Estimated Effort:** 21 Story Points

### Epic 3: Shipping & Delivery Integration 📦 (Sprint 3)
*"As a customer, I can select shipping options, see costs, and track my package in real-time."*

**Business Value:** Complete fulfillment capability  
**Estimated Effort:** 21 Story Points

### Epic 4: Inventory & Promotions 🎁 (Sprint 4)
*"As a customer, I can apply discount codes and see real-time inventory availability."*

**Business Value:** Competitive e-commerce features  
**Estimated Effort:** 21 Story Points

### Epic 5: Production Readiness & Observability 📊 (Sprint 5)
*"As an operations team, I can monitor, troubleshoot, and scale the system in production."*

**Business Value:** System reliability and maintainability  
**Estimated Effort:** 21 Story Points

---

## 🏃‍♂️ Sprint 1: Core Order Management Foundation

**Sprint Goal:** *"Deliver a working order lifecycle (create → pay → ship → deliver) with REST API and database persistence."*

**Sprint Dates:** Week 1 (5 working days)  
**Team Capacity:** 21 Story Points

### 📋 Sprint Backlog

#### 🎫 User Story 1.1: Create Order (5 pts)
```gherkin
As a customer
I want to create an order with multiple items and shipping address
So that I can start the purchase process

Acceptance Criteria:
- Order has unique ID
- Order contains 1+ items with product ID, name, price, quantity
- Order has shipping address (street, city, state, zip, country)
- Order starts in CREATED status
- Order total is calculated correctly
- API returns 201 Created with order details

Technical Notes:
- Implement Order aggregate (DDD)
- Use Money value object for amounts
- Implement OrderItem and Address value objects
- Add validation (not null, positive amounts)
```

**Tasks:**
- [ ] Create Order aggregate with state machine (3h)
- [ ] Create Money, OrderItem, Address value objects (2h)
- [ ] Implement OrderStatus enum (CREATED, PAID, SHIPPED, DELIVERED, CANCELLED) (1h)
- [ ] Write 20+ unit tests for domain logic (4h)
- [ ] Document business rules in README (1h)

**Definition of Done:**
- ✅ Code compiles and passes all tests
- ✅ Unit test coverage > 80%
- ✅ Code reviewed and approved
- ✅ Business rules documented

---

#### 🎫 User Story 1.2: Persist Orders to Database (5 pts)
```gherkin
As a system
I want to persist orders to PostgreSQL database
So that orders survive application restarts

Acceptance Criteria:
- Orders saved to database with all details
- Order items saved with proper relationships
- Can retrieve order by ID
- Can query orders by customer ID
- Uses optimistic locking for concurrent updates

Technical Notes:
- Use Spring Data JPA
- Create OrderEntity and OrderItemEntity
- Implement OrderRepository
- Use Testcontainers for integration tests
```

**Tasks:**
- [ ] Setup PostgreSQL with Docker Compose (1h)
- [ ] Create JPA entities (OrderEntity, OrderItemEntity) (2h)
- [ ] Implement OrderRepository interface (1h)
- [ ] Create mapper between domain and entity (2h)
- [ ] Write integration tests with Testcontainers (4h)

**Definition of Done:**
- ✅ Database schema created automatically
- ✅ Integration tests use real PostgreSQL
- ✅ No N+1 query problems
- ✅ Transactions work correctly

---

#### 🎫 User Story 1.3: REST API for Order Operations (5 pts)
```gherkin
As a customer
I want to interact with orders via REST API
So that I can manage orders from any client

Acceptance Criteria:
- POST /api/orders - Create new order (201 Created)
- GET /api/orders/{id} - Get order details (200 OK)
- GET /api/orders - List all orders (200 OK)
- POST /api/orders/{id}/pay - Mark order as paid (200 OK)
- POST /api/orders/{id}/ship - Mark order as shipped (200 OK)
- POST /api/orders/{id}/deliver - Mark order as delivered (200 OK)
- DELETE /api/orders/{id} - Cancel order (204 No Content)
- Proper HTTP status codes (400, 404, 409, 500)

Technical Notes:
- Use Spring MVC controllers
- Create request/response DTOs
- Add Bean Validation
- Implement global exception handler
```

**Tasks:**
- [ ] Create OrderController with all endpoints (3h)
- [ ] Create DTOs (CreateOrderRequest, OrderResponse, etc.) (2h)
- [ ] Add Bean Validation annotations (1h)
- [ ] Implement global exception handler (2h)
- [ ] Write API integration tests with MockMvc (4h)
- [ ] Create Postman collection (1h)

**Definition of Done:**
- ✅ All endpoints working and tested
- ✅ DTOs separate from domain models
- ✅ Validation errors return 400 with details
- ✅ Postman collection works

---

#### 🎫 User Story 1.4: Order State Machine (3 pts)
```gherkin
As a system
I want to enforce valid order state transitions
So that business rules are never violated

Acceptance Criteria:
- Can transition: CREATED → PAID → SHIPPED → DELIVERED
- Can cancel: CREATED → CANCELLED or PAID → CANCELLED
- Cannot cancel: SHIPPED → CANCELLED (reject with 409 Conflict)
- Cannot ship unpaid order (reject with 409 Conflict)
- State transitions are atomic and consistent

Technical Notes:
- Implement state machine in Order aggregate
- Throw IllegalStateException for invalid transitions
- Log all state transitions
```

**Tasks:**
- [ ] Implement pay() method with state validation (1h)
- [ ] Implement ship() method with state validation (1h)
- [ ] Implement deliver() method with state validation (1h)
- [ ] Implement cancel() method with state validation (1h)
- [ ] Write tests for all valid/invalid transitions (2h)

**Definition of Done:**
- ✅ State machine logic in domain (not controller)
- ✅ All invalid transitions rejected
- ✅ Tests cover all edge cases

---

#### 🎫 User Story 1.5: Basic Domain Events (3 pts)
```gherkin
As a system
I want to emit events when order state changes
So that other systems can react to order updates

Acceptance Criteria:
- OrderCreatedEvent published when order created
- OrderPaidEvent published when order paid
- OrderShippedEvent published when order shipped
- OrderDeliveredEvent published when order delivered
- OrderCancelledEvent published when order cancelled
- Events logged to console

Technical Notes:
- Use Spring ApplicationEventPublisher
- Create event listener for logging
- Events contain order ID, customer ID, timestamp
```

**Tasks:**
- [ ] Create domain event classes (1h)
- [ ] Integrate ApplicationEventPublisher in OrderService (1h)
- [ ] Create logging event listener (1h)
- [ ] Write tests for event publishing (2h)

**Definition of Done:**
- ✅ Events published transactionally
- ✅ Event listener logs all events
- ✅ Tests verify event publishing

---

### 🎯 Sprint 1 Ceremonies

#### Sprint Planning (Monday, 2 hours)
**Agenda:**
1. PO presents Sprint Goal and prioritized stories
2. Team reviews and clarifies acceptance criteria
3. Developers estimate story points (Planning Poker)
4. Team commits to sprint backlog (21 points)
5. Developers break down stories into tasks
6. SM creates sprint board (To Do, In Progress, Review, Done)

**Output:** Sprint backlog with committed stories and tasks

---

#### Daily Standup (Every day, 15 minutes)
**Time:** 9:00 AM  
**Format:** Each person answers:
1. What did I complete yesterday?
2. What will I work on today?
3. Any blockers or impediments?

**Rules:**
- No problem-solving (take offline)
- Update sprint board before standup
- SM tracks blockers

**Example Standup:**

**Monday:**
- Dev 1: "Started Order aggregate, will finish value objects today. No blockers."
- Dev 2: "Will setup PostgreSQL and Docker Compose today. No blockers."
- SM: "Great start. Let me know if you need help with Docker."

**Wednesday:**
- Dev 1: "Finished domain model and 15 unit tests. Starting OrderRepository today. No blockers."
- Dev 2: "Database working, writing integration tests. Need clarity on optimistic locking - will sync with Dev 1 after standup."
- SM: "Good progress. We're on track for sprint goal."

---

#### Sprint Review/Demo (Friday, 1 hour)
**Agenda:**
1. Dev team demos working software to PO
2. Show each user story acceptance criteria met
3. PO accepts or rejects each story
4. Discuss what wasn't completed (if any)
5. Update Product Backlog based on feedback

**Demo Script:**
```
"Let me show you what we built this sprint...

[User Story 1.1] I'll create an order using Postman. 
- POST to /api/orders with items and address
- See? Returns 201 Created with order ID
- Order total calculated correctly: $45.00

[User Story 1.2] Now let's verify it's in the database.
- Check PostgreSQL with DataGrip
- Order and items persisted with relationships
- Can query by customer ID

[User Story 1.3] Let's walk through the full lifecycle.
- Pay order: POST to /orders/{id}/pay → Status changes to PAID
- Ship order: POST to /orders/{id}/ship → Status changes to SHIPPED
- Deliver order: POST to /orders/{id}/deliver → Status changes to DELIVERED

[User Story 1.4] Let's test invalid transitions.
- Try to ship unpaid order → Returns 409 Conflict with clear error
- Try to cancel shipped order → Returns 409 Conflict
- State machine enforces business rules!

[User Story 1.5] Check the logs.
- See OrderCreatedEvent, OrderPaidEvent, etc.
- Events published for every state change

All 5 stories completed. Sprint goal achieved!"
```

**PO Decision:**
- ✅ Accept all stories (meets acceptance criteria)
- ❌ Reject stories (missing criteria, bugs found)

---

#### Sprint Retrospective (Friday, 45 minutes)
**Agenda:**
1. What went well?
2. What could be improved?
3. Action items for next sprint

**Retro Format (Start, Stop, Continue):**

**Start:**
- Pair programming for complex logic
- Writing tests before implementation (TDD)

**Stop:**
- Skipping code reviews to "save time"
- Working without updating sprint board

**Continue:**
- Daily standups at 9 AM
- Using Testcontainers for realistic tests
- Breaking stories into small tasks

**Action Items:**
- [ ] SM to create code review checklist
- [ ] Team to try TDD for next sprint
- [ ] Dev 1 to share Testcontainers setup guide

---

### 📊 Sprint 1 Metrics

**Velocity:** 21 Story Points (baseline)  
**Burndown Chart:** Track daily (expected smooth decline)  
**Test Coverage:** Target 80%+  
**Code Review Turnaround:** < 4 hours  
**Bugs Found in Review:** Track for quality trends

---

## 🏃‍♂️ Sprint 2: Payment Gateway Integration

**Sprint Goal:** *"Deliver secure payment processing with Stripe and PayPal, including refunds and payment failure handling."*

**Sprint Dates:** Week 2 (5 working days)  
**Team Capacity:** 21 Story Points

### 📋 Sprint Backlog

#### 🎫 User Story 2.1: Payment Method Support (3 pts)
```gherkin
As a customer
I want to select my payment method (Credit Card, PayPal, Apple Pay)
So that I can pay using my preferred option

Acceptance Criteria:
- Payment method enum (CREDIT_CARD, DEBIT_CARD, PAYPAL, APPLE_PAY, GOOGLE_PAY)
- Order stores payment method used
- Payment details include transaction ID, authorization code, amount
- Payment timestamp tracked

Technical Notes:
- Create PaymentDetails value object
- Update Order aggregate with payment details
- Add payment method to API request
```

**Tasks:**
- [ ] Create PaymentMethod enum (30min)
- [ ] Create PaymentDetails value object (1h)
- [ ] Update Order aggregate with processPayment() method (2h)
- [ ] Update DTOs to include payment method (1h)
- [ ] Write unit tests for payment logic (2h)

**Definition of Done:**
- ✅ Payment details stored with order
- ✅ Tests verify payment amounts match order total
- ✅ Invalid payment amounts rejected

---

#### 🎫 User Story 2.2: Stripe Payment Integration (8 pts)
```gherkin
As a customer
I want to pay with credit/debit card via Stripe
So that my payment is processed securely

Acceptance Criteria:
- Stripe API integration working
- Test mode uses Stripe test tokens
- Payment intent created and confirmed
- Transaction ID stored in order
- Payment failures handled gracefully
- PCI compliance (never store raw card data)

Technical Notes:
- Use Stripe Java SDK
- Store Stripe API key in environment variables
- Create StripePaymentService implementing PaymentGatewayPort
- Use Stripe webhooks for payment status updates
```

**Tasks:**
- [ ] Add Stripe Java SDK dependency (15min)
- [ ] Create PaymentGatewayPort interface (1h)
- [ ] Implement StripePaymentService (4h)
- [ ] Add Stripe configuration (API keys, webhook secret) (1h)
- [ ] Implement webhook handler for payment confirmations (3h)
- [ ] Write integration tests with Stripe test mode (4h)
- [ ] Document Stripe setup in README (1h)

**Definition of Done:**
- ✅ Stripe payments work in test mode
- ✅ Webhook events processed correctly
- ✅ API keys stored securely (not in code)
- ✅ Tests use Stripe test tokens

---

#### 🎫 User Story 2.3: PayPal Payment Integration (5 pts)
```gherkin
As a customer
I want to pay with my PayPal account
So that I can use my PayPal balance or linked payment methods

Acceptance Criteria:
- PayPal SDK integration working
- PayPal order created and captured
- Transaction ID stored in order
- Payment failures handled
- Supports PayPal sandbox for testing

Technical Notes:
- Use PayPal Checkout SDK
- Implement PayPalPaymentService
- Handle PayPal redirect flow (for web clients)
```

**Tasks:**
- [ ] Add PayPal SDK dependency (15min)
- [ ] Implement PayPalPaymentService (3h)
- [ ] Add PayPal configuration (client ID, secret) (1h)
- [ ] Write integration tests with PayPal sandbox (3h)
- [ ] Document PayPal setup in README (1h)

**Definition of Done:**
- ✅ PayPal payments work in sandbox mode
- ✅ Both Stripe and PayPal work side-by-side
- ✅ Payment gateway selection based on payment method

---

#### 🎫 User Story 2.4: Payment Refund Processing (3 pts)
```gherkin
As a customer support agent
I want to refund customer payments
So that I can handle returns and cancellations

Acceptance Criteria:
- POST /api/orders/{id}/refund endpoint
- Supports full and partial refunds
- Refund processed through payment gateway
- Order status updated to REFUNDED
- Refund event published

Technical Notes:
- Add refundPayment() method to PaymentGatewayPort
- Implement for both Stripe and PayPal
- Track refund amount and timestamp
```

**Tasks:**
- [ ] Add refund methods to PaymentGatewayPort (1h)
- [ ] Implement Stripe refund (2h)
- [ ] Implement PayPal refund (2h)
- [ ] Add refund API endpoint (1h)
- [ ] Write tests for refund scenarios (2h)

**Definition of Done:**
- ✅ Refunds work for both payment gateways
- ✅ Partial refunds supported
- ✅ Refund events published

---

#### 🎫 User Story 2.5: Payment Failure Handling (2 pts)
```gherkin
As a system
I want to handle payment failures gracefully
So that customers get clear error messages and can retry

Acceptance Criteria:
- Payment failures return 400 Bad Request with error details
- Declined cards show reason (insufficient funds, invalid card, etc.)
- Network errors trigger retry logic
- Failed payments logged for monitoring
- Order remains in CREATED status on failure

Technical Notes:
- Catch payment gateway exceptions
- Map gateway error codes to user-friendly messages
- Add retry logic with exponential backoff
```

**Tasks:**
- [ ] Create PaymentException hierarchy (1h)
- [ ] Map Stripe error codes to messages (1h)
- [ ] Map PayPal error codes to messages (1h)
- [ ] Add retry logic with Spring Retry (2h)
- [ ] Write tests for failure scenarios (2h)

**Definition of Done:**
- ✅ All payment failures handled
- ✅ Error messages user-friendly
- ✅ Failed payments logged
- ✅ Tests cover network failures, declined cards, etc.

---

### 🎯 Sprint 2 Ceremonies

**Sprint Planning:** Monday, 2 hours  
**Daily Standup:** Every day, 9:00 AM, 15 minutes  
**Sprint Review/Demo:** Friday, 1 hour  
**Sprint Retrospective:** Friday, 45 minutes

**Sprint Review Demo Script:**
```
"This sprint, we integrated real payment processing!

[Story 2.1] Payment methods now stored with orders.
- See PaymentDetails in order response

[Story 2.2] Stripe integration working!
- Create order: POST /api/orders
- Pay with Stripe: POST /api/orders/{id}/payment
  Request: { "method": "CREDIT_CARD", "stripeToken": "tok_visa", ... }
- Check Stripe dashboard - payment recorded!
- Webhook received and processed

[Story 2.3] PayPal also working!
- Same endpoint, different payment method
- Request: { "method": "PAYPAL", "paypalOrderId": "...", ... }
- Check PayPal sandbox - payment captured!

[Story 2.4] Refunds working.
- POST /api/orders/{id}/refund
- Refund appears in Stripe/PayPal dashboards
- Order status updated to REFUNDED

[Story 2.5] Graceful failure handling.
- Try invalid card: Returns 400 with 'Card declined: insufficient funds'
- Try network error: Retries 3 times with backoff
- All failures logged for monitoring

All payment features complete! Ready for Sprint 3: Shipping."
```

---

## 🏃‍♂️ Sprint 3: Shipping & Delivery Integration

**Sprint Goal:** *"Deliver real-time shipping rate calculation, carrier integration (FedEx/UPS), label generation, and tracking."*

**Sprint Dates:** Week 3 (5 working days)  
**Team Capacity:** 21 Story Points

### 📋 Sprint Backlog

#### 🎫 User Story 3.1: Shipping Methods & Carriers (3 pts)
```gherkin
As a customer
I want to select shipping speed (Standard, Express, Overnight)
So that I can balance cost and delivery time

Acceptance Criteria:
- Shipping methods: STANDARD (5-7 days), EXPRESS (2-3 days), OVERNIGHT (1 day), TWO_DAY
- Shipping carriers: FEDEX, UPS, USPS, DHL
- Each method has estimated delivery date
- Order stores shipping method and carrier used

Technical Notes:
- Create ShippingMethod and ShippingCarrier enums
- Create ShipmentDetails value object
- Update Order aggregate with shipment details
```

**Tasks:**
- [ ] Create ShippingMethod enum with delivery estimates (1h)
- [ ] Create ShippingCarrier enum (30min)
- [ ] Create ShipmentDetails value object (1h)
- [ ] Update Order aggregate with ship() method (2h)
- [ ] Write unit tests for shipping logic (2h)

**Definition of Done:**
- ✅ Shipping details stored with order
- ✅ Estimated delivery date calculated
- ✅ Tests verify business rules

---

#### 🎫 User Story 3.2: Shipping Rate Calculation (5 pts)
```gherkin
As a customer
I want to see shipping costs before selecting a method
So that I can make an informed decision

Acceptance Criteria:
- GET /api/orders/{id}/shipping-rates returns rates from all carriers
- Rates include: carrier, method, cost, estimated delivery
- Rates sorted by cost (cheapest first)
- Rates calculated based on package weight, dimensions, destination

Technical Notes:
- Create ShippingCarrierPort interface
- Implement rate calculation for each carrier
- Use carrier APIs (FedEx, UPS) in test mode
```

**Tasks:**
- [ ] Create ShippingCarrierPort interface (1h)
- [ ] Implement FedExShippingService (rate API) (3h)
- [ ] Implement UPSShippingService (rate API) (3h)
- [ ] Create ShippingCalculationService (orchestrates carriers) (2h)
- [ ] Add GET /shipping-rates endpoint (1h)
- [ ] Write integration tests with carrier test APIs (3h)

**Definition of Done:**
- ✅ Rates returned from multiple carriers
- ✅ Rates accurate based on package details
- ✅ Tests use carrier sandbox APIs

---

#### 🎫 User Story 3.3: Ship Order with Label Generation (8 pts)
```gherkin
As a warehouse staff member
I want to ship orders and generate shipping labels
So that packages can be sent to customers

Acceptance Criteria:
- POST /api/orders/{id}/ship creates shipment with carrier
- Tracking number generated by carrier
- Shipping label generated as PDF
- Label downloadable via API
- Shipment status tracked (PENDING, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED)

Technical Notes:
- Use FedEx/UPS label generation APIs
- Store shipping label PDF in database or S3
- Create ShipmentLabel entity
```

**Tasks:**
- [ ] Implement FedEx shipment creation + label generation (4h)
- [ ] Implement UPS shipment creation + label generation (4h)
- [ ] Add POST /ship endpoint with carrier selection (2h)
- [ ] Store shipping label (database BLOB or S3) (2h)
- [ ] Add GET /orders/{id}/label endpoint (1h)
- [ ] Write integration tests for shipment creation (3h)

**Definition of Done:**
- ✅ Shipments created successfully
- ✅ Labels downloadable as PDF
- ✅ Tracking numbers stored

---

#### 🎫 User Story 3.4: Real-Time Package Tracking (3 pts)
```gherkin
As a customer
I want to track my package in real-time
So that I know when it will arrive

Acceptance Criteria:
- GET /api/orders/{id}/tracking returns tracking info
- Shows: current status, location, estimated delivery, tracking events
- Tracking events show timestamp, status, location
- Updates pulled from carrier APIs

Technical Notes:
- Implement tracking APIs for FedEx/UPS
- Parse tracking response into TrackingInfo object
- Cache tracking data (5 min) to reduce API calls
```

**Tasks:**
- [ ] Implement FedEx tracking API integration (2h)
- [ ] Implement UPS tracking API integration (2h)
- [ ] Create TrackingInfo response DTO (1h)
- [ ] Add GET /tracking endpoint (1h)
- [ ] Add caching with Spring Cache (1h)
- [ ] Write tests for tracking scenarios (2h)

**Definition of Done:**
- ✅ Tracking works for both carriers
- ✅ Tracking events displayed chronologically
- ✅ Caching reduces API calls

---

#### 🎫 User Story 3.5: Delivery Confirmation (2 pts)
```gherkin
As a system
I want to automatically update order status when delivered
So that customers and support see accurate status

Acceptance Criteria:
- Webhook from carrier updates order status to DELIVERED
- Delivery timestamp stored
- OrderDeliveredEvent published
- Customer notification sent (email/SMS)

Technical Notes:
- Implement carrier webhook handlers
- Verify webhook signatures for security
- Update order status automatically
```

**Tasks:**
- [ ] Create FedEx webhook handler (2h)
- [ ] Create UPS webhook handler (2h)
- [ ] Verify webhook signatures (1h)
- [ ] Update order status on delivery event (1h)
- [ ] Write tests for webhook processing (2h)

**Definition of Done:**
- ✅ Webhooks processed correctly
- ✅ Order status updated automatically
- ✅ Webhook signature verification working

---

### 🎯 Sprint 3 Demo Highlights
```
"This sprint, we integrated real shipping carriers!

[Story 3.1] Shipping methods and carriers configured.

[Story 3.2] Get shipping rates before checkout.
- GET /api/orders/{id}/shipping-rates
- Returns: FedEx Ground $8.99 (5-7 days), UPS 2nd Day $15.99, etc.
- Customer can compare and choose

[Story 3.3] Ship order and generate label.
- POST /api/orders/{id}/ship with selected carrier
- Label PDF generated and stored
- Download: GET /api/orders/{id}/label
- See actual FedEx shipping label!

[Story 3.4] Real-time tracking.
- GET /api/orders/{id}/tracking
- Shows: 'Package in transit - Memphis, TN - 2:45 PM'
- Tracking events from FedEx API

[Story 3.5] Automatic delivery confirmation.
- FedEx webhook fires when delivered
- Order status updates to DELIVERED
- Customer gets email notification

Full shipping lifecycle working! Sprint 3 complete."
```

---

## 🏃‍♂️ Sprint 4: Inventory & Promotions

**Sprint Goal:** *"Deliver real-time inventory tracking, discount codes, tax calculation, and multi-warehouse support."*

**Sprint Dates:** Week 4 (5 working days)  
**Team Capacity:** 21 Story Points

### 📋 Sprint Backlog

#### 🎫 User Story 4.1: Inventory Management (5 pts)
```gherkin
As a warehouse manager
I want to track product inventory in real-time
So that we prevent overselling and know when to restock

Acceptance Criteria:
- Product inventory tracked: quantity on hand, reserved, available
- Inventory reserved when order created (not yet paid)
- Inventory committed when order paid
- Inventory released if order cancelled
- Low stock alerts when inventory below reorder point

Technical Notes:
- Create InventoryItem entity
- Track: quantityOnHand, quantityReserved, quantityAvailable
- Implement reserve/commit/release operations
```

**Tasks:**
- [ ] Create InventoryItem entity and repository (2h)
- [ ] Implement reserve() operation on order creation (2h)
- [ ] Implement commit() operation on payment (2h)
- [ ] Implement release() operation on cancellation (1h)
- [ ] Add low stock event and handler (2h)
- [ ] Write tests for inventory scenarios (3h)

**Definition of Done:**
- ✅ Inventory prevents overselling
- ✅ Reserved inventory released on cancellation
- ✅ Low stock alerts working

---

#### 🎫 User Story 4.2: Promotion & Discount System (8 pts)
```gherkin
As a customer
I want to apply discount codes to my order
So that I can save money on my purchase

Acceptance Criteria:
- Apply promo code: POST /api/orders/{id}/promotions
- Discount types: percentage (20% off), fixed amount ($10 off), free shipping
- Promotions have: code, start date, end date, usage limit
- Validate: promo active, not expired, not exceeding usage limit
- Calculate discount and update order total
- Support stackable promotions (apply multiple codes)

Technical Notes:
- Create Promotion entity
- Implement discount calculation logic
- Track promotion usage per customer
```

**Tasks:**
- [ ] Create Promotion entity (discount types, rules) (2h)
- [ ] Implement discount calculation service (3h)
- [ ] Add POST /promotions endpoint to apply code (2h)
- [ ] Track promotion usage (PromotionUsage entity) (2h)
- [ ] Validate promotion rules (active, not expired, usage limit) (2h)
- [ ] Update order total with discount applied (1h)
- [ ] Write tests for various promotion scenarios (4h)

**Definition of Done:**
- ✅ Promo codes apply correctly
- ✅ Invalid codes rejected with reason
- ✅ Usage limits enforced
- ✅ Order total includes discount

---

#### 🎫 User Story 4.3: Tax Calculation (5 pts)
```gherkin
As a system
I want to calculate sales tax based on shipping address
So that orders are charged correct tax amounts

Acceptance Criteria:
- Tax calculated based on shipping state/zip code
- Tax rules configurable per state
- Tax rates: state tax + local tax
- Tax exempt orders supported (with valid tax ID)
- Tax included in order total

Technical Notes:
- Create TaxRule entity (state, county, rate)
- Integrate with tax calculation service (optional: TaxJar API)
```

**Tasks:**
- [ ] Create TaxRule entity and repository (1h)
- [ ] Implement tax calculation logic (3h)
- [ ] Add tax calculation to order total (2h)
- [ ] Support tax-exempt orders (1h)
- [ ] Seed database with common tax rates (1h)
- [ ] Write tests for tax scenarios (3h)

**Definition of Done:**
- ✅ Tax calculated correctly by state
- ✅ Tax-exempt orders supported
- ✅ Tax included in final order total

---

#### 🎫 User Story 4.4: Multi-Warehouse Support (3 pts)
```gherkin
As a business
I want to ship orders from the nearest warehouse
So that I can reduce shipping costs and delivery time

Acceptance Criteria:
- Multiple warehouses defined (location, inventory)
- Select warehouse closest to customer address
- Calculate shipping from selected warehouse
- Inventory tracked per warehouse

Technical Notes:
- Create Warehouse entity
- Implement distance calculation (zip code based)
- Assign inventory to warehouses
```

**Tasks:**
- [ ] Create Warehouse entity (location, address) (1h)
- [ ] Link inventory to warehouses (1h)
- [ ] Implement warehouse selection logic (closest to customer) (2h)
- [ ] Update shipping rate calculation to use warehouse location (2h)
- [ ] Write tests for multi-warehouse scenarios (2h)

**Definition of Done:**
- ✅ Orders shipped from optimal warehouse
- ✅ Inventory tracked per warehouse
- ✅ Shipping costs reduced

---

### 🎯 Sprint 4 Demo Highlights
```
"This sprint, we added inventory and promotions!

[Story 4.1] Inventory management working.
- Create order: Inventory reserved (not yet committed)
- Pay order: Inventory committed (reduced from on-hand)
- Cancel order: Inventory released (back to available)
- Low stock alert: Email sent when inventory < reorder point

[Story 4.2] Promo codes working!
- Apply code 'SUMMER20': 20% off entire order
- Apply code 'FREESHIP': Free shipping
- Stack codes: SUMMER20 + FREESHIP = 20% off + free shipping!
- Invalid/expired codes rejected with clear message

[Story 4.3] Tax calculation.
- Order to California: 9.5% sales tax
- Order to Texas: 8.25% sales tax
- Tax included in final total
- Tax-exempt orders supported (wholesale customers)

[Story 4.4] Multi-warehouse optimization.
- Customer in LA → Ship from LA warehouse
- Customer in NYC → Ship from NJ warehouse
- Reduces shipping cost and delivery time

Sprint 4 complete! E-commerce features ready."
```

---

## 🏃‍♂️ Sprint 5: Production Readiness & Observability

**Sprint Goal:** *"Deliver production-ready system with monitoring, security, CI/CD, and comprehensive documentation."*

**Sprint Dates:** Week 5 (5 working days)  
**Team Capacity:** 21 Story Points

### 📋 Sprint Backlog

#### 🎫 User Story 5.1: Comprehensive Logging & Observability (5 pts)
```gherkin
As an operations engineer
I want detailed logs and metrics
So that I can monitor system health and debug issues

Acceptance Criteria:
- Structured JSON logging (machine-readable)
- Correlation ID per request (trace requests end-to-end)
- Log levels: DEBUG, INFO, WARN, ERROR
- Metrics: order count, payment success rate, API latency
- Health checks: database, payment gateway, shipping carrier
- Actuator endpoints enabled (/health, /metrics, /info)

Technical Notes:
- Use Logback with JSON encoder
- Add Micrometer for metrics
- Implement custom health indicators
- Add correlation ID filter
```

**Tasks:**
- [ ] Configure JSON logging with Logback (1h)
- [ ] Add correlation ID filter (MDC) (2h)
- [ ] Add Micrometer metrics (order counters, timers) (3h)
- [ ] Implement custom health indicators (DB, Stripe, FedEx) (3h)
- [ ] Enable Spring Actuator endpoints (1h)
- [ ] Write tests for observability features (2h)

**Definition of Done:**
- ✅ Logs in JSON format
- ✅ Correlation IDs in all logs
- ✅ Metrics exposed at /actuator/metrics
- ✅ Health checks working

---

#### 🎫 User Story 5.2: Security & Authentication (5 pts)
```gherkin
As a system administrator
I want secure API access with authentication
So that only authorized users can access the system

Acceptance Criteria:
- JWT authentication for API endpoints
- Role-based access: CUSTOMER, ADMIN, WAREHOUSE_STAFF
- Customers can only see their own orders
- Admins can see all orders and issue refunds
- Warehouse staff can ship orders
- Secrets stored in environment variables

Technical Notes:
- Use Spring Security with JWT
- Implement JwtAuthenticationFilter
- Create UserDetailsService
- Add @PreAuthorize annotations
```

**Tasks:**
- [ ] Add Spring Security dependency (15min)
- [ ] Implement JWT token generation/validation (3h)
- [ ] Create authentication filter (2h)
- [ ] Add role-based authorization (2h)
- [ ] Secure all endpoints with @PreAuthorize (2h)
- [ ] Store secrets in environment variables (1h)
- [ ] Write security integration tests (3h)

**Definition of Done:**
- ✅ JWT authentication working
- ✅ Role-based access enforced
- ✅ Unauthorized requests return 401/403
- ✅ No secrets in code

---

#### 🎫 User Story 5.3: Error Handling & Resilience (5 pts)
```gherkin
As a system
I want to handle failures gracefully
So that the system remains stable under stress

Acceptance Criteria:
- Global exception handler returns consistent error format
- Retry logic for transient failures (network, gateway timeouts)
- Circuit breaker for external services (payment, shipping)
- Proper transaction boundaries (@Transactional)
- Graceful degradation (e.g., payment fails → order stays in CREATED)

Technical Notes:
- Use Spring Retry for retries
- Use Resilience4j for circuit breaker
- Implement global @ControllerAdvice
```

**Tasks:**
- [ ] Implement global exception handler (2h)
- [ ] Add retry logic with Spring Retry (2h)
- [ ] Add circuit breaker with Resilience4j (3h)
- [ ] Test failure scenarios (DB down, Stripe down) (3h)
- [ ] Document error handling strategy (1h)

**Definition of Done:**
- ✅ Consistent error responses
- ✅ Retries working for transient failures
- ✅ Circuit breaker prevents cascading failures
- ✅ Tests verify resilience

---

#### 🎫 User Story 5.4: CI/CD Pipeline & Docker (5 pts)
```gherkin
As a developer
I want automated build and deployment
So that code is tested and deployed consistently

Acceptance Criteria:
- GitHub Actions pipeline: build, test, lint, security scan
- Dockerfile for containerization (multi-stage build)
- Docker Compose for local full-stack setup
- Pipeline runs on every push
- Tests must pass before merge

Technical Notes:
- Create .github/workflows/ci.yml
- Multi-stage Dockerfile (Maven build + JRE runtime)
- Docker Compose with app + PostgreSQL + Redis
```

**Tasks:**
- [ ] Create GitHub Actions CI pipeline (3h)
- [ ] Add Maven build and test steps (1h)
- [ ] Add code quality checks (Checkstyle, SpotBugs) (2h)
- [ ] Create optimized Dockerfile (multi-stage) (2h)
- [ ] Create docker-compose.yml for full stack (2h)
- [ ] Document CI/CD and Docker setup (1h)

**Definition of Done:**
- ✅ CI pipeline green
- ✅ Docker image builds successfully
- ✅ Docker Compose starts full stack
- ✅ README has setup instructions

---

#### 🎫 User Story 5.5: Comprehensive Documentation (1 pt)
```gherkin
As a new developer
I want complete documentation
So that I can understand and contribute to the system

Acceptance Criteria:
- README with setup instructions
- Architecture diagrams (component, sequence, data flow)
- API documentation (OpenAPI/Swagger)
- Architecture Decision Records (ADRs)
- Postman collection with example requests

Technical Notes:
- Use Springdoc OpenAPI for API docs
- Create diagrams with PlantUML or Mermaid
- Write ADRs in docs/adr/
```

**Tasks:**
- [ ] Write comprehensive README (2h)
- [ ] Generate OpenAPI documentation (1h)
- [ ] Create architecture diagrams (2h)
- [ ] Write 5 ADRs (hexagonal arch, events, payment, shipping, security) (2h)
- [ ] Update Postman collection with all endpoints (1h)

**Definition of Done:**
- ✅ README covers setup, architecture, deployment
- ✅ API docs accessible at /swagger-ui
- ✅ Diagrams explain system design
- ✅ ADRs document key decisions

---

### 🎯 Sprint 5 Final Demo (Product Review)
```
"Welcome to the final sprint demo! We've built a production-ready system.

[Story 5.1] Observability in action.
- Logs in JSON format with correlation IDs
- Metrics: 1,247 orders created, 98.7% payment success rate
- Health check: All systems operational
- See Grafana dashboard (if set up)

[Story 5.2] Security hardened.
- Login: POST /api/auth/login → Returns JWT token
- Try accessing orders without token → 401 Unauthorized
- Customer can only see their orders
- Admin can see all orders and issue refunds
- Secrets in environment variables

[Story 5.3] Resilience tested.
- Simulate Stripe outage → Circuit breaker opens, graceful failure
- Network timeout → Automatic retry with backoff
- All errors return consistent format

[Story 5.4] CI/CD and Docker.
- GitHub Actions pipeline: Build ✅, Tests ✅, Lint ✅
- Docker image built in 45 seconds
- docker-compose up → Full stack running locally

[Story 5.5] Documentation complete.
- README with architecture overview
- API docs at /swagger-ui (interactive)
- Architecture diagrams
- ADRs explain design decisions

PRODUCT IS PRODUCTION-READY! 🚀"
```

---

## 📊 Program Metrics & Success Criteria

### Sprint Velocity Tracking
| Sprint | Committed | Completed | Velocity | Notes |
|--------|-----------|-----------|----------|-------|
| 1      | 21 pts    | 21 pts    | 21       | Baseline established |
| 2      | 21 pts    | 21 pts    | 21       | Payment features |
| 3      | 21 pts    | 19 pts    | 19       | Shipping complex, tracking delayed |
| 4      | 21 pts    | 21 pts    | 21       | Inventory and promos |
| 5      | 21 pts    | 21 pts    | 21       | Production readiness |
| **Avg**| **21**    | **20.6**  | **20.6** | Consistent delivery |

### Code Quality Metrics
- **Test Coverage:** > 80% (Target met)
- **Code Review Turnaround:** < 4 hours (Target met)
- **Bugs Found in Review:** 3 per sprint (Low, acceptable)
- **Build Time:** < 3 minutes (Target met)
- **Docker Build Time:** < 1 minute (Target met)

### Technical Debt
- 0 P1 (Critical) bugs
- 2 P2 (Major) bugs (tracked for next iteration)
- 5 P3 (Minor) improvements (nice-to-haves)

### Definition of Done (Program Level)
- ✅ All 25 user stories completed
- ✅ 80%+ test coverage
- ✅ Green CI/CD pipeline
- ✅ Docker Compose full stack working
- ✅ Production-ready security
- ✅ Comprehensive documentation
- ✅ Real payment & shipping integrations working

---

## 🎯 Final Retrospective (Program Level)

### What Went Well ✅
1. **Consistent Velocity:** Delivered 20.6 pts/sprint average
2. **High Quality:** 80%+ test coverage maintained
3. **Real Integrations:** Stripe, PayPal, FedEx, UPS all working
4. **Team Collaboration:** Daily standups kept team aligned
5. **CI/CD:** Automated pipeline caught issues early

### What Could Be Improved 🔄
1. **Estimation Accuracy:** Shipping integration took longer than estimated
2. **Documentation:** Should document as we go, not at end
3. **Technical Debt:** Need dedicated time for refactoring
4. **Testing:** More integration tests for complex workflows
5. **Monitoring:** Set up Grafana/Prometheus earlier

### Action Items for Next Phase 🚀
- [ ] Set up production monitoring (Grafana, Prometheus)
- [ ] Add end-to-end tests for critical user journeys
- [ ] Refactor payment gateway abstraction (reduce duplication)
- [ ] Performance testing and optimization
- [ ] Security audit and penetration testing

---

## 📚 Learning Outcomes

### Technical Skills Mastered
- ✅ Spring Boot advanced features (Security, Retry, Actuator)
- ✅ Domain-Driven Design in practice
- ✅ Hexagonal Architecture implementation
- ✅ Payment gateway integration (Stripe, PayPal)
- ✅ Shipping carrier integration (FedEx, UPS)
- ✅ Event-driven architecture
- ✅ Comprehensive testing (unit, integration, E2E)
- ✅ Docker and containerization
- ✅ CI/CD with GitHub Actions
- ✅ Production observability and monitoring

### Agile/Scrum Skills Developed
- ✅ Sprint planning and estimation
- ✅ Daily standup facilitation
- ✅ Sprint review/demo presentation
- ✅ Retrospective and continuous improvement
- ✅ Backlog grooming and prioritization
- ✅ Story writing with acceptance criteria
- ✅ Velocity tracking and forecasting

### Soft Skills Enhanced
- ✅ Team collaboration and communication
- ✅ Stakeholder management (PO interactions)
- ✅ Technical decision-making and tradeoffs
- ✅ Documentation and knowledge sharing
- ✅ Code review and constructive feedback
- ✅ Problem-solving under time constraints

---

## 🎓 Interview Readiness

After this program, you can confidently answer:

**"Tell me about a project you built from scratch."**
> "I built a production-ready Order Fulfillment System using Scrum methodology over 5 one-week sprints. The system processes real payments via Stripe and PayPal, integrates with FedEx and UPS for shipping, and includes inventory management, promotions, and tax calculation. We maintained 80%+ test coverage, implemented CI/CD with GitHub Actions, and deployed using Docker. The system handles thousands of orders per day with proper observability and error handling."

**"How do you approach integrating with third-party APIs?"**
> "I use the Hexagonal Architecture pattern with port-adapter structure. For example, I created a PaymentGatewayPort interface, then implemented adapters for Stripe and PayPal. This allows swapping implementations without touching business logic. I handle failures with retry logic, circuit breakers, and graceful degradation. All integrations are tested with sandbox environments."

**"Describe your testing strategy."**
> "I follow the testing pyramid: 70% unit tests for domain logic, 20% integration tests with Testcontainers for database and API flows, and 10% end-to-end tests for critical user journeys. Unit tests are fast (milliseconds) and run on every commit. Integration tests use real PostgreSQL and Kafka via Docker. I achieve 80%+ coverage while keeping tests maintainable."

**"How do you ensure code quality in a team?"**
> "We use multiple mechanisms: mandatory code reviews (< 4 hour SLA), automated CI pipeline with tests and linting, consistent Definition of Done, and daily standups to catch issues early. We track metrics like test coverage, build time, and bugs found in review. Sprint retrospectives help us continuously improve practices."

---

## 🔄 Next Steps & Extensions

### Phase 2: Scaling & Performance (Optional Sprints 6-8)
- [ ] Load testing and performance optimization
- [ ] Caching strategy (Redis for rates, inventory)
- [ ] Database sharding for multi-tenant
- [ ] Async event processing with Kafka
- [ ] API rate limiting and throttling

### Phase 3: Advanced Features (Optional Sprints 9-11)
- [ ] Order recommendations (machine learning)
- [ ] Fraud detection integration
- [ ] International shipping and customs
- [ ] Subscription orders (recurring)
- [ ] Gift cards and store credit

### Phase 4: Operations & Scale (Optional Sprints 12-14)
- [ ] Kubernetes deployment
- [ ] Distributed tracing (Jaeger)
- [ ] Log aggregation (ELK stack)
- [ ] Chaos engineering experiments
- [ ] Production runbooks and incident response

---

## 🎯 Final Checklist

### Code Repository ✅
- [x] GitHub repository created
- [x] README with setup instructions
- [x] All code committed and pushed
- [x] No secrets in code (environment variables)
- [x] .gitignore configured

### Documentation ✅
- [x] Architecture diagrams (component, sequence, data flow)
- [x] API documentation (OpenAPI/Swagger)
- [x] ADRs for key decisions
- [x] Postman collection
- [x] Setup guide (Docker, local development)

### Testing ✅
- [x] 80%+ unit test coverage
- [x] Integration tests with Testcontainers
- [x] API integration tests
- [x] Security tests (authentication, authorization)
- [x] Failure scenario tests (circuit breaker, retry)

### CI/CD ✅
- [x] GitHub Actions pipeline configured
- [x] Build and test on every push
- [x] Code quality checks (Checkstyle, SpotBugs)
- [x] Docker image built automatically
- [x] All checks passing (green pipeline)

### Production Readiness ✅
- [x] Structured logging (JSON format)
- [x] Correlation IDs for tracing
- [x] Metrics and health checks
- [x] JWT authentication
- [x] Role-based authorization
- [x] Error handling and resilience
- [x] Docker Compose for full stack
- [x] Environment-specific configuration

---

## 🚀 Graduation

**Congratulations!** You've completed the 5-week Scrum-based mentor program.

You've built a production-ready Order Fulfillment System that includes:
- ✅ Real payment processing (Stripe, PayPal)
- ✅ Real shipping integration (FedEx, UPS)
- ✅ Inventory management and promotions
- ✅ Security and authentication
- ✅ Observability and monitoring
- ✅ CI/CD automation
- ✅ Comprehensive documentation

### You Can Now:
1. Work effectively in Scrum teams
2. Integrate complex third-party APIs
3. Build production-ready systems
4. Make architectural decisions
5. Write maintainable, testable code
6. Deploy with Docker and CI/CD
7. Pass mid-level Java interviews

### Next Level:
- Join a Scrum team professionally
- Contribute to open-source projects
- Mentor junior developers
- Lead technical design discussions
- Pursue senior engineer role

---

**Program Version:** 1.0 - Scrum Edition  
**Last Updated:** December 2025  
**License:** MIT - Share and adapt for your learning journey

---

*"The best developers aren't those who know the most—they're those who ship working software consistently, collaborate effectively, and continuously improve."*

**Now go build. Ship daily. Be prolific. 🚀**

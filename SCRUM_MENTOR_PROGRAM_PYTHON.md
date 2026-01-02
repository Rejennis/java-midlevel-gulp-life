# 🐍 Python Mid-Level Sprint Program: Order Fulfillment System
## Agile/Scrum Edition — One-Week Sprints

> Philosophy: Ship working software weekly with production-minded discipline.

---

## Program Overview

- Duration: 5 Weeks (5 sprints)
- Sprint Length: 1 week (5 working days)
- Team: Product Owner (PO) + Scrum Master (SM) + Developers (1–3)
- Output: Production-capable Order Fulfillment System (payments + shipping)

### Technology Stack (Python)
- Web/API: FastAPI + Uvicorn (async), optional Django REST for alt-path
- Data: PostgreSQL, SQLAlchemy 2.x ORM, Alembic migrations
- Models: Pydantic v2 (request/response, domain DTOs)
- Testing: pytest, pytest-asyncio, testcontainers-python
- Messaging: Kafka via aiokafka or confluent-kafka-python
- Payments: stripe (Stripe SDK), PayPal Checkout (paypal-checkout-serversdk or HTTP)
- Shipping: FedEx/UPS REST APIs (requests/httpx)
- Security: PyJWT, passlib, OAuth2 Password Flow (FastAPI)
- Observability: OpenTelemetry (traces), Prometheus client (metrics), structlog/loguru
- Config: pydantic-settings, dotenv, 12-factor .env
- Packaging: Docker + docker-compose, GitHub Actions CI/CD

### Success Metrics
- 100% critical user journeys pass (create → pay → ship → deliver)
- 80%+ test coverage on core modules
- CI green on PRs (tests, lint, type checks)
- p95 API latency < 2s under realistic load
- Zero P1 issues in production-like environment

---

## Product Backlog (Epics)

- Epic 1: Core Order Management (Sprint 1)
- Epic 2: Payment Gateway Integration (Sprint 2)
- Epic 3: Shipping & Delivery Integration (Sprint 3)
- Epic 4: Inventory & Promotions (Sprint 4)
- Epic 5: Production Readiness & Observability (Sprint 5)

---

## Sprint 1: Core Order Management Foundation

Sprint Goal: "Deliver working order lifecycle (create → pay → ship → deliver) with REST API and persistence."
Capacity: 21 pts

### User Story 1.1: Create Order (5 pts)
As a customer, I create an order with items and shipping address.

Acceptance Criteria:
- Unique order ID
- ≥1 items with productId, name, unitPrice, quantity
- Shipping address (street, city, state, zip, country)
- Initial status CREATED
- Correct total calculation
- 201 Created returns order snapshot

Technical Notes:
- Pydantic models: Money, OrderItem, Address, Order
- Domain services (pure functions) for totals and validation

Tasks:
- Define Pydantic schemas and validators (2h)
- Implement domain service for `calculate_total()` (1h)
- Add validation (non-null, positive amounts, quantity ≥1) (1h)
- Write 20+ pytest unit tests (2h)

Definition of Done:
- Tests green, coverage >80% for domain
- Models reviewed, documented

### User Story 1.2: Persist Orders (5 pts)
As a system, I persist orders in PostgreSQL with relationships.

Acceptance Criteria:
- Orders and items saved and retrieved by ID
- Query by customerId
- Optimistic locking for concurrent updates

Technical Notes:
- SQLAlchemy 2.x models: `OrderModel`, `OrderItemModel`
- Repository pattern (interface + impl)
- Alembic migrations for schema evolution
- Testcontainers with PostgreSQL for integration tests

Tasks:
- Configure SQLAlchemy engine + session (1h)
- Create models + relationships (2h)
- Implement repository methods (1h)
- Alembic setup + initial migration (1h)
- Integration tests with testcontainers (4h)

Definition of Done:
- Real DB integration tests pass
- No N+1 issues (use `selectinload`)
- Transactional correctness verified

### User Story 1.3: REST API for Orders (5 pts)
As a customer, I manage orders via REST API.

Endpoints:
- POST `/api/orders` → 201 Created
- GET `/api/orders/{id}` → 200 OK
- GET `/api/orders` → 200 OK (filter by customerId)
- POST `/api/orders/{id}/pay` → 200 OK
- POST `/api/orders/{id}/ship` → 200 OK
- POST `/api/orders/{id}/deliver` → 200 OK
- DELETE `/api/orders/{id}` → 204 No Content

Acceptance Criteria:
- Correct HTTP semantics and error codes (400/404/409)
- Request/response schemas separate from domain

Technical Notes:
- FastAPI routers + dependency-injected services
- Exception handlers (HTTPException mapping)
- Pydantic DTOs

Tasks:
- Create routers and DTOs (3h)
- Add validation (Pydantic + FastAPI) (1h)
- Global exception handler (2h)
- API tests via `httpx` + `pytest` (3h)
- Postman collection (1h)

Definition of Done:
- All endpoints tested and documented
- DTOs decoupled from persistence models

### User Story 1.4: Order State Machine (3 pts)
As a system, enforce valid transitions and reject invalid ones.

Acceptance Criteria:
- Allowed: CREATED → PAID → SHIPPED → DELIVERED
- Cancel: CREATED → CANCELLED or PAID → CANCELLED
- Reject: SHIPPED → CANCELLED, SHIP unpaid order (409)

Technical Notes:
- Enum `OrderStatus`, transition validator
- Log transitions (structlog)

Tasks:
- Implement `pay()`, `ship()`, `deliver()`, `cancel()` (2h)
- Negative tests for invalid transitions (2h)

Definition of Done:
- All edge cases covered
- Logic isolated in domain layer

### User Story 1.5: Basic Domain Events (3 pts)
As a system, emit events on state changes for future integrations.

Acceptance Criteria:
- Emit `OrderCreated`, `OrderPaid`, `OrderShipped`, `OrderDelivered`, `OrderCancelled`
- Events include orderId, customerId, timestamp
- Log listener prints events

Technical Notes:
- Lightweight in-process pub/sub (callbacks)
- Future-ready adapter for Kafka

Tasks:
- Define event classes (1h)
- Publisher + listener wiring (1h)
- Unit tests for event emission (1h)

Definition of Done:
- Transactionally consistent publication
- Tests verify emission on transitions

### Sprint 1 Ceremonies & Metrics
- Planning: 2h, commit to 21 pts
- Daily: 15m standup, board updated
- Review: 1h demo covering all stories
- Retro: 45m (Start/Stop/Continue)
- Velocity baseline: 21 pts, target coverage ≥80%

---

## Sprint 2: Payment Gateway Integration

Sprint Goal: "Secure payments via Stripe and PayPal, including refunds and failure handling."
Capacity: 21 pts

### User Story 2.1: Payment Method Support (3 pts)
Acceptance Criteria:
- Enum: CREDIT_CARD, DEBIT_CARD, PAYPAL, APPLE_PAY, GOOGLE_PAY
- Store payment method, amount, transactionId, authorizedAt

Technical Notes:
- Pydantic `PaymentDetails`
- Extend `Order` with `process_payment()`

Tasks:
- Enum + details schema (1h)
- Domain updates + tests (2h)

### User Story 2.2: Stripe Integration (8 pts)
Acceptance Criteria:
- Stripe PaymentIntent created/confirmed
- Transaction ID stored
- Test mode with Stripe tokens
- PCI-safe (no raw card storage)

Technical Notes:
- `stripe` Python SDK
- Env config: `STRIPE_API_KEY`, `STRIPE_WEBHOOK_SECRET`
- `StripePaymentService` implementing `PaymentGatewayPort`
- Webhook endpoint `/webhooks/stripe`

Tasks:
- Add dependency + config (1h)
- Implement service (4h)
- Webhook handler (3h)
- Integration tests (4h)
- README setup (1h)

Definition of Done:
- Test tokens succeed
- Webhooks update payment status

### User Story 2.3: PayPal Integration (5 pts)
Acceptance Criteria:
- Create + capture PayPal order
- Store transactionId
- Sandbox support

Technical Notes:
- PayPal server SDK or HTTP client
- Config: `PAYPAL_CLIENT_ID`, `PAYPAL_CLIENT_SECRET`
- Redirect flow support

Tasks:
- Dependency + config (1h)
- Service implementation (3h)
- Sandbox tests (3h)
- Docs (1h)

Definition of Done:
- Stripe + PayPal coexist
- Payment method selects gateway

### User Story 2.4: Refunds (3 pts)
Acceptance Criteria:
- Refund endpoint (full/partial)
- Idempotent retries, audit trail

Technical Notes:
- Gateway-specific refund APIs
- Persist refund records

Tasks:
- Endpoint + service (2h)
- Tests for edge cases (2h)

### User Story 2.5: Failure Handling (2 pts)
Acceptance Criteria:
- Graceful errors, retry/backoff
- Idempotency keys to avoid double charges

Technical Notes:
- `tenacity` for retries, timeouts
- Correlation IDs per request

---

## Sprint 3: Shipping & Delivery Integration

Sprint Goal: "Rates, labels, tracking with FedEx/UPS."
Capacity: 21 pts

### User Story 3.1: Rate Calculation (5 pts)
Acceptance Criteria:
- Get carrier rates based on weight/dimensions/destination
- Present selectable shipping options

Technical Notes:
- HTTP integrations (httpx)
- Carrier credentials in env

Tasks:
- Rate service abstraction (2h)
- FedEx/UPS adapters (4h)
- Tests with recorded fixtures (2h)

### User Story 3.2: Label Generation (5 pts)
Acceptance Criteria:
- Generate shipping labels (PDF/PNG)
- Persist label metadata

Technical Notes:
- Carrier label APIs
- Store in object storage or DB blob

Tasks:
- Label endpoints (2h)
- Adapter + file storage (3h)
- Tests (2h)

### User Story 3.3: Tracking & Webhooks (5 pts)
Acceptance Criteria:
- Subscribe to tracking updates
- Webhooks update order status

Technical Notes:
- Webhook route `/webhooks/carrier`
- Signature verification

Tasks:
- Tracking service (2h)
- Webhook handler (2h)
- Tests (2h)

### User Story 3.4: Address Validation (3 pts)
Acceptance Criteria:
- Validate shipping addresses via carrier APIs
- Clear 400 errors for invalid fields

### User Story 3.5: Shipping Selection (3 pts)
Acceptance Criteria:
- Customer selects option at checkout
- Pricing reflects selection

---

## Sprint 4: Inventory & Promotions

Sprint Goal: "Accurate stock and flexible discounts."
Capacity: 21 pts

### User Story 4.1: Inventory Model (5 pts)
Acceptance Criteria:
- Product stock tracking
- Low-stock alerts

Technical Notes:
- SQLAlchemy models `Product`, `Inventory`
- Concurrency-safe updates

### User Story 4.2: Stock Reservation (5 pts)
Acceptance Criteria:
- Reserve stock on order create
- Release on cancel/refund

Technical Notes:
- Transactions + isolation
- Dead-letter handling on failure

### User Story 4.3: Promotions/Coupons (5 pts)
Acceptance Criteria:
- Percent/amount discounts
- Validity windows, min purchase rules

Technical Notes:
- Pydantic validation for business rules
- Apply at order total stage

### User Story 4.4: Tax Calculation (3 pts)
Acceptance Criteria:
- Basic tax rates per region
- Show tax breakdown

### User Story 4.5: Multi-Warehouse (3 pts)
Acceptance Criteria:
- Stock per location
- Ship-from optimal warehouse

---

## Sprint 5: Production Readiness & Observability

Sprint Goal: "Traceable, secure, resilient system with CI/CD."
Capacity: 21 pts

### User Story 5.1: Tracing & Logging (5 pts)
Acceptance Criteria:
- OpenTelemetry traces across API + services
- Structured logs with correlation IDs

Technical Notes:
- otel SDK + exporters
- structlog/loguru configuration

### User Story 5.2: Metrics (5 pts)
Acceptance Criteria:
- Prometheus metrics endpoint `/metrics`
- Custom counters/gauges/histograms

Technical Notes:
- `prometheus_client`
- Request/DB latency histograms

### User Story 5.3: Security & Auth (5 pts)
Acceptance Criteria:
- JWT auth (PyJWT)
- Roles/permissions for admin vs customer

Technical Notes:
- OAuth2 Password flow + scopes
- Secure secrets via env/KeyVault alt

### User Story 5.4: Resilience Patterns (3 pts)
Acceptance Criteria:
- Timeouts, retries, circuit breakers
- Bulkhead in async tasks

Technical Notes:
- `tenacity` retries + backoff
- `asyncio` task groups

### User Story 5.5: CI/CD & Docker (3 pts)
Acceptance Criteria:
- GitHub Actions running tests, lint (ruff/flake8), typecheck (mypy)
- Build + push Docker image

Technical Notes:
- Workflow matrix (Python 3.11/3.12)
- `docker-compose` for local stack (PostgreSQL, Kafka)

---

## Ceremonies & Working Agreements

- Planning (Mon, 2h): Sprint Goal, story clarifications, estimates, commitment
- Daily Standup (15m): Yesterday/Today/Blockers; update board before standup
- Review (Fri, 1h): Demo stories against acceptance criteria; PO accepts/rejects
- Retrospective (Fri, 45m): Start/Stop/Continue with 2–3 actionable items

Definition of Done (Global):
- Passing tests (unit/integration) with coverage thresholds
- Lint and type checks pass (ruff/mypy)
- API documented (OpenAPI/README) and Postman updated
- Secrets not in code; env-configured; reproducible Docker run

Metrics:
- Velocity tracking, test coverage, lead time, escaped defects
- Burndown chart maintained daily

---

## Quickstart (Optional)

Project bootstrap commands (illustrative):

```bash
# Create venv and install core deps
python -m venv .venv
source .venv/Scripts/activate  # Windows: .venv\Scripts\activate
pip install fastapi uvicorn[standard] sqlalchemy alembic pydantic pytest pytest-asyncio testcontainers opentelemetry-sdk prometheus-client pyjwt structlog tenacity python-dotenv httpx stripe

# Initialize Alembic
alembic init migrations

# Run API (placeholder)
uvicorn app.main:app --reload
```

---

## Demo Script (Sprint 1 Example)

- Create order via POST `/api/orders` (201 Created with ID)
- Verify DB persistence (integration test or admin endpoint)
- Pay → Ship → Deliver via respective endpoints (200 OK)
- Show logs of domain events emitted for each transition

---

## Notes

- Django REST Framework is a viable alternative if team prefers sync request handling and built-in admin UX.
- For Kafka/eventing, prefer outbox pattern or transactional publishers to ensure consistency.
- Adopt Twelve-Factor App principles: config via env, stateless processes, disposability.

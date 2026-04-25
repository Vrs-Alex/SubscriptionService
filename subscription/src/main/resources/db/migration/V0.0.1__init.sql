
CREATE TABLE subscription (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    plan VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    price NUMERIC(10, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE subscription_status_history (
    id UUID PRIMARY KEY,
    subscription_id UUID NOT NULL REFERENCES subscription(id) ON DELETE CASCADE,
    old_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    message TEXT
);


CREATE INDEX idx_subscription_user_id    ON subscription(user_id);
CREATE INDEX idx_subscription_service    ON subscription(service_type);
CREATE INDEX idx_subscription_status     ON subscription(status);
CREATE INDEX idx_subscription_end_date   ON subscription(end_date);
CREATE INDEX idx_history_subscription_id ON subscription_status_history(subscription_id);
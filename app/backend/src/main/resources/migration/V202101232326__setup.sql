DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT users_pkey PRIMARY KEY (username)
);

DROP TABLE IF EXISTS basket;
CREATE TABLE IF NOT EXISTS basket (
    id SERIAL NOT NULL,
    username VARCHAR(100),
    closed bool NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT now(),
    checkout_at TIMESTAMP,
    CONSTRAINT basket_pkey PRIMARY KEY (id),
    CONSTRAINT basket_users_fkey
          FOREIGN KEY(username)
    	  REFERENCES users(username)
);
CREATE INDEX IF NOT EXISTS basket_status_idx ON basket USING btree (username, closed);

DROP TABLE IF EXISTS basket_product;
CREATE TABLE IF NOT EXISTS basket_product (
	id serial NOT NULL,
	basket_id INTEGER NULL,
	product varchar(255) NULL,
	quantity int4 NULL,
	CONSTRAINT basket_product_pkey PRIMARY KEY (id),
    CONSTRAINT basket_product_basket_fkey
          FOREIGN KEY(basket_id)
    	  REFERENCES basket(id)
);
CREATE UNIQUE INDEX IF NOT EXISTS basket_product_idx ON basket_product USING btree (basket_id, product);
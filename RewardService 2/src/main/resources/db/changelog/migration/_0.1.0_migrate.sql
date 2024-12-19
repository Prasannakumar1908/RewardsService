--Create the rewards table
CREATE TABLE rewards
(
    reward_Id   VARCHAR(255) NOT NULL PRIMARY KEY,
    reward_Name VARCHAR(255),
    points      INT
);

CREATE TABLE token_entry
(
    processor_name VARCHAR(255) NOT NULL,
    segment        INT          NOT NULL,
    owner          VARCHAR(255),
    token          oid,
    timestamp      VARCHAR(255) NOT NULL,
    token_type     VARCHAR(255),
    CONSTRAINT token_entry_pkey PRIMARY KEY (processor_name, segment)
);

CREATE TABLE association_value_entry
(
    id                bigint       NOT NULL PRIMARY KEY,
    association_key   VARCHAR(255) NOT NULL,
    association_value VARCHAR(255),
    saga_id           VARCHAR(255) NOT NULL,
    saga_type         VARCHAR(255)
);

CREATE TABLE dead_letter_entry
(
    dead_letter_id       VARCHAR(255)                NOT NULL PRIMARY KEY,
    cause_message        VARCHAR(1023),
    cause_type           VARCHAR(255),
    diagnostics          OID,
    enqueued_at          TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_touched         TIMESTAMP(6) WITH TIME ZONE,
    aggregate_identifier VARCHAR(255),
    event_identifier     VARCHAR(255)                NOT NULL,
    message_type         VARCHAR(255)                NOT NULL,
    meta_data            OID,
    payload              OID                         NOT NULL,
    payload_revision     VARCHAR(255),
    payload_type         VARCHAR(255)                NOT NULL,
    sequence_number      BIGINT,
    time_stamp           VARCHAR(255)                NOT NULL,
    token                OID,
    token_type           VARCHAR(255),
    type                 VARCHAR(255),
    processing_group     VARCHAR(255)                NOT NULL,
    processing_started   TIMESTAMP(6) WITH TIME ZONE,
    sequence_identifier  VARCHAR(255)                NOT NULL,
    sequence_index       BIGINT                      NOT NULL
);

CREATE TABLE saga_entry
(
    saga_id         VARCHAR(255) NOT NULL PRIMARY KEY,
    revision        VARCHAR(255),
    saga_type       VARCHAR(255),
    serialized_saga OID
);




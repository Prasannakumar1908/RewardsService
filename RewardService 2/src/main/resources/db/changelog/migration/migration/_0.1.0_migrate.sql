
-- Create the rewards table
CREATE TABLE rewards (
                         reward_id VARCHAR(255) NOT NULL PRIMARY KEY,
                         reward_name VARCHAR(255) NOT NULL,
                         points INT NOT NULL,
                         created_at TIMESTAMP
);

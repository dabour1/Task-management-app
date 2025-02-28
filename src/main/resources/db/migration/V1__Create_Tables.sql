-- Create Users Table
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    phone_number VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Workspaces Table
CREATE TABLE workspaces (
    workspace_id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(user_id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Partitioned Tasks Table
CREATE TABLE tasks (
    task_id UUID,
    workspace_id UUID,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(255) CHECK (status IN ('PENDING', 'DONE')) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (task_id, workspace_id)
) PARTITION BY LIST (workspace_id);





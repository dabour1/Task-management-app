
-- Stored Procedure to Create a New Partition for a Workspace
CREATE OR REPLACE FUNCTION create_task_partition(workspace_id UUID)
RETURNS VOID AS $$
DECLARE
    partition_name TEXT;
BEGIN
    -- Generate the partition name
    partition_name := 'tasks_workspace_' || REPLACE(workspace_id::TEXT, '-', '_');

    -- Create the partition
    EXECUTE format('
        CREATE TABLE %I PARTITION OF tasks
        FOR VALUES IN (%L);
    ', partition_name, workspace_id);
END;
$$ LANGUAGE plpgsql;

-- Trigger to Automatically Create a Partition When a New Workspace is Created
CREATE OR REPLACE FUNCTION create_workspace_and_partition()
RETURNS TRIGGER AS $$
BEGIN

    PERFORM create_task_partition(NEW.workspace_id);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach the Trigger to the Workspaces Table
CREATE TRIGGER trigger_create_workspace
AFTER INSERT ON workspaces
FOR EACH ROW
EXECUTE FUNCTION create_workspace_and_partition();
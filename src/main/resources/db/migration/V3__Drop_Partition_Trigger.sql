
CREATE OR REPLACE FUNCTION drop_task_partition()
RETURNS TRIGGER AS $$
DECLARE
    partition_name TEXT;
BEGIN
    -- Generate the partition name
    partition_name := 'tasks_workspace_' || REPLACE(OLD.workspace_id::TEXT, '-', '_');

    -- Drop the partition if it exists
    EXECUTE format('DROP TABLE IF EXISTS %I;', partition_name);

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_drop_task_partition
AFTER DELETE ON workspaces
FOR EACH ROW
EXECUTE FUNCTION drop_task_partition();
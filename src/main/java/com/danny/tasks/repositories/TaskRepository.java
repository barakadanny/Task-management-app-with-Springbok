package com.danny.tasks.repositories;

import com.danny.tasks.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Task entities in the database.
 *
 * ✅ What This Interface Does:
 * - Extends JpaRepository<Task, UUID>, which gives full access to CRUD methods:
 *   save, findAll, findById, delete, etc.
 * - `Task` is the Entity, and `UUID` is the type of the primary key.
 * - Annotated with @Repository to make Spring recognize this as a bean and inject it when needed.
 *
 * ✅ How Spring Knows What to Do:
 * Spring Data JPA uses the method names to build queries automatically based on conventions.
 * These are known as **derived query methods**.
 *
 * 🧠 YES: Method Naming MATTERS.
 * The way you name methods like `findByTaskListId` or `findByTaskListIdAndId` must follow a
 * **specific grammar** so Spring can understand which fields and conditions to build the SQL for.
 *
 * ✅ You DO NOT have unlimited freedom in naming — you must follow naming conventions.
 *
 * ----------------------------------------------------------------
 * 📘 BASIC SYNTAX FOR METHOD NAMING
 * ----------------------------------------------------------------
 * Prefix → 'find', 'count', 'exists', 'delete'
 * +
 * Optional Modifiers → 'First', 'Top', 'Distinct'
 * +
 * 'By'
 * +
 * Entity Fields (CamelCase matched to your entity's fields)
 * +
 * Optional Keywords → 'And', 'Or', 'Between', 'Like', 'In', 'OrderBy' etc.
 *
 * EXAMPLES:
 * - findById(UUID id)
 * - findByTaskListId(UUID id)
 * - findByTitleContaining(String title)
 * - findByStatusAndPriority(String status, String priority)
 * - countByTaskListId(UUID id)
 * - existsByTitle(String title)
 *
 * 📘 You can also use nested properties like:
 * - findByAssignedUser_Username(String username)
 *   → Navigates from Task → AssignedUser → Username
 *
 * ----------------------------------------------------------------
 * ❓ WHAT IF YOU WANT CUSTOM SQL?
 * ----------------------------------------------------------------
 * You can use the @Query annotation when:
 * - You need joins, custom logic, or database-specific functions.
 * - You want to write native SQL or JPQL (Java Persistence Query Language).
 *
 * Example:
 * @Query("SELECT t FROM Task t WHERE t.status = :status")
 * List<Task> findByCustomStatus(@Param("status") String status);
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Finds all tasks that belong to a given task list by its ID.
     *
     * This method name tells Spring:
     *   "Select * from Task where taskList.id = ?"
     */
    List<Task> findByTaskListId(UUID taskListId);

    /**
     * Finds a specific task by its ID, but also checks if it belongs to a specific TaskList.
     *
     * This is useful for validation or permission checks.
     *
     * Spring reads this as:
     *   "SELECT * FROM Task WHERE taskList.id = ? AND id = ?"
     */
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);
}

package dev.kbd.xylem.client.model;

/**
 * Represents a fundamental unit of execution in the Xylem distributed tracing
 * system.
 * <p>
 * This structure combines <b>Hierarchy</b> (for navigation), <b>Time</b> (for
 * profiling),
 * and <b>Semantics</b> (for filtering).
 *
 * @param execId       The global unique identifier for the entire
 *                     trace/transaction.
 * @param prevSpanId   The <b>Horizontal Link</b> (Sequence). Points to the
 *                     previous span at this level.
 * @param parentSpanId The <b>Vertical Link</b> (Containment). Points to the
 *                     owner of this span.
 *
 * @param level        <b>The Importance Level.</b>
 *                     Allows users to filter noise (e.g., "Show me only
 *                     ERRORs").
 *                     When filtering by level, the UI should preserve the
 *                     parent hierarchy to show the <i>path</i> to the error.
 * @param message      <b>The Human Narrative.</b>
 *                     A concise description of the event (e.g., "Payment
 *                     Processed", "DB Connection Failed").
 *
 * @param createdAt    Creation Time (Queue/Wait check).
 * @param startedAt    Execution Start (Processing check).
 * @param endedAt      Execution End (Processing check).
 * @param returnedAt   Response Sent (Overhead check).
 */
public record Span(
        String execId,
        String prevSpanId,
        String parentSpanId,
        Level level,
        String message,
        long createdAt,
        long startedAt,
        long endedAt,
        long returnedAt) {
    /**
     * Standard industry log levels to categorize span importance.
     */
    public enum Level {
        /** Fine-grained informational events (loops, variable states). */
        TRACE,
        /** Debugging events useful for developers. */
        DEBUG,
        /** Normal lifecycle events (Start/Stop, Requests). */
        INFO,
        /** Potential issues that did not stop execution. */
        WARN,
        /** Errors that failed an operation but kept the service alive. */
        ERROR,
        /** Severe errors that crashed the service/application. */
        FATAL
    }
}
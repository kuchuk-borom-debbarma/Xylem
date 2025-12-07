# Xylem

Xylem is a structured logging and distributed tracing framework designed to visualize the internal flow of your applications. It turns disconnected logs into a unified, hierarchical narrative, allowing you to trace the path of execution through your system—from the root request to the deepest service call.

## Features

### 1. High-Level View
See the big picture. Xylem allows you to trace execution at a high level. This view is perfect for understanding the overall flow without getting lost in the details.

### 2. In-Depth View
Drill down when you need to. Xylem supports expanding traces to see in-depth details, allowing you to see internals and granular steps.

### 3. Contextual Filtering
From a user's perspective, this is where Xylem shines compared to a standard "Log File". Standard log viewers give you a flat list of errors, losing the context. Xylem gives you the **Story of the Error**.

When you filter for "Errors Only", Xylem doesn't just show the failure; it automatically shows the **Parent Chain** leading to it. Irrelevant successful siblings are hidden, leaving a clean, direct line to the problem:

`Service A (Ghosted) -> Service B (Ghosted) -> DB Query (RED)`

This enables **Instant Blame Assignment** and **Root Cause Analysis** by revealing the architectural path of the crash, rather than just a stack trace.

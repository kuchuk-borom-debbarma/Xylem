# simplified data structure definition for Xylem.

# Xylem Data Structure: Hierarchical Linked List

### 1\. The Core Schema (`Span`)

Every unit of execution—whether a microservice, a database call, or a function—is stored as a flat `Span` object with the following fields:

| Field | Type | Description |
| :--- | :--- | :--- |
| **`spanId`** | `UUID` | Unique identifier for this specific span. |
| **`parentSpanId`** | `UUID` | **Vertical Link (Containment).** <br>If `null`, this is a Top-Level flow (Architecture Layer). <br>If set, this span exists *inside* the parent. |
| **`prevSpanId`** | `UUID` | **Horizontal Link (Sequence).** <br>Points to the *immediately preceding* span at the **same nesting level**. <br>If `null`, this is the first step in the current container. |
| **`data`** | `JSON` | Payload containing `name`, `timestamp`, `service_name`, and attributes. |

-----

### 2\. The Relationships

This structure creates a "Folder & File" system where navigation is strictly controlled by level.

#### Vertical Relationship (`parentSpanId`)

  * Defines **Depth**.
  * Used to "Enter" a node (Drill Down).
  * *Query:* `SELECT * FROM spans WHERE parentSpanId = 'X'` (Fetch Children).

#### Horizontal Relationship (`prevSpanId`)

  * Defines **Time/Order**.
  * Used to "Next" a node (Sequence).
  * *Query:* The UI reconstructs the chain by following the `prevSpanId` pointers within a fetched list.

-----

### 3\. Structural Example

#### Scenario

**Service A** runs some internal validation logic, then calls **Service B**.

#### A. High Level Representation (Architecture)

*These spans have `parentSpanId: null`. They ignore internal noise.*

```json
[
  {
    "spanId": "ServiceA",
    "parentSpanId": null,
    "prevSpanId": null,
    "name": "Service A"
  },
  {
    "spanId": "ServiceB",
    "parentSpanId": null,
    "prevSpanId": "ServiceA",  // Points to the previous High-Level block
    "name": "Service B"
  }
]
```

**Visual Structure:**

#### B. Low Level Representation (Internals)

*These spans have `parentSpanId: ServiceA`. They form an isolated chain inside A.*

```json
[
  {
    "spanId": "Val",
    "parentSpanId": "ServiceA", // I am inside A
    "prevSpanId": null,         // I am the first internal step
    "name": "Validation"
  },
  {
    "spanId": "Calc",
    "parentSpanId": "ServiceA", // I am inside A
    "prevSpanId": "Val",        // I happen after Validation
    "name": "Calculation"
  }
]
```

**Visual Structure:**

-----

### 4\. Key Structural Rules

1.  **Isolation:** A span in the "High Level" chain never points to a span in the "Low Level" chain via `prevSpanId`.
      * *Correct:* `Service B.prev` $\to$ `Service A`
      * *Incorrect:* `Service B.prev` $\to$ `Service A Internal Logic`
2.  **Implicit Ordering:** The order of execution is determined solely by traversing the `prevSpanId` linked list. Timestamps are secondary data, not structural keys.
3.  **Lazy Scalability:** The structure allows fetching the "High Level" view (2 items) without ever touching or loading the "Low Level" view (potentially thousands of items).
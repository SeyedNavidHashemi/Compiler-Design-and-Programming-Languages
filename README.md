# CPY Language Compiler: A Three-Phase Implementation

An in-depth, semester-long project for a **Compiler Design** course, focused on building a complete compiler for the custom **CPY programming language**. This project demonstrates a strong understanding of fundamental compiler stages, including intermediate representation (AST), semantic analysis, code optimization, and security vulnerability detection.

## Project Overview

This repository is structured around three core phases of compiler construction, each implemented as a dedicated stage to process the input code.

| Phase | Core Focus | Key Concepts Demonstrated |
| :--- | :--- | :--- |
| **Phase 1** | **Lexical & Syntax Analysis** | Tokenization, ANTLR Grammar, Abstract Syntax Tree (AST) Construction. |
| **Phase 2** | **Name Analysis & Optimization** | Symbol Table Management, Scope Handling, Dead Code Elimination (DCE). |
| **Phase 3** | **Type Checking & Security Analysis** | Semantic Error Detection, Memory Leak Detection, Uninitialized Variable Checks. |

***

## 🛠️ Technology Stack

* **Language:** `Java`
* **Tools:** **ANTLR** (ANother Tool for Language Recognition) for automated Lexer and Parser generation.
* **Paradigm:** Visitor Pattern for traversing the Abstract Syntax Tree (AST) in each analysis phase.
* **Build System:** `Maven`

***

## 🔬 Detailed Phase Analysis

### **Phase 1: Lexical Analysis, Parsing, and AST**

This phase establishes the foundational structure for code processing.

* **Parser & Lexer Implementation:** Utilized ANTLR to define the CPY language grammar, generating the necessary components to convert source code into tokens and then a Parse Tree.
* **Abstract Syntax Tree (AST) Generation:** Implemented a robust structure to transform the Parse Tree into a more abstract and semantically useful AST, which is the primary data structure for all subsequent phases.
* **AST Metrics & Evaluation:** Developed visitor methods to assess the complexity and structure of the input code, including:
    * Calculating the total **Statement Count** within different scopes (functions, loops, `if`/`else` blocks).
    * Determining the maximum **Expression Depth** for binary and unary expressions.

### **Phase 2: Name Analysis and Optimization**

Focuses on scope management, identifier resolution, and improving code efficiency.

* **Hierarchical Symbol Table:** Designed and implemented a Name Analyzer using the Visitor pattern to traverse the AST. It successfully managed a scope-aware **Symbol Table** to store and resolve names for functions, variables, and patterns.
* **Name Error Detection:** Identified and reported critical name-related errors, such as:
    * Usage of **Undefined Variables** or **Undefined Functions**.
    * Redefinition of identifiers within the same scope.
* **Code Optimization (Dead Code Elimination):** Implemented a conservative data-flow analysis based on **Reachability Analysis** starting from the `main` function. This process effectively:
    * Identified and removed all **unreachable functions** and code blocks.
    * Removed unused variable declarations and assignments.
    * Eliminated ineffective/no-op statements.

### **Phase 3: Type Checking and Security Analysis**

This final phase ensures semantic correctness and performs a basic security audit.

* **Type Checker Implementation:** Developed a Type Checker visitor to enforce the type system of CPY and detect semantic errors, including:
    * **Argument Type Mismatch:** Ensuring function call arguments match the declared parameter types.
    * **Non-Same Operands:** Flagging operations (e.g., addition) on incompatible types (e.g., `integer + string`).
    * **Return Type Mismatch:** Verifying that a function's returned value type aligns with its declared return type.
* **Vulnerability Analysis (Security Audit):** Implemented a specialized `VulnAnalysis` visitor to identify common C/C++ style memory and security risks:
    * **Memory Leak Detection:** Checked for instances of dynamic memory allocation (`malloc`) without a corresponding **`free`** operation.
    * **Uninitialized Variable Usage:** Detected variables being accessed before a value was explicitly assigned to them.
    * **User-Controlled Malloc:** Identified potential buffer overflow vulnerabilities where the size of a memory allocation (`malloc` argument) is directly derived from unchecked user input.

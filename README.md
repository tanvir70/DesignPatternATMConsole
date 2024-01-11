# ATM Simulation

Welcome to the ATM Simulation project! This Java application demonstrates an ATM (Automated Teller Machine) simulator that employs various design patterns to manage ATM operations and transactions. It provides a user-friendly command-line interface for users to interact with the ATM.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Design Patterns](#design-patterns)


## Overview

This ATM simulation project is designed to showcase the implementation of several key design patterns in Java, making it an excellent educational resource for software developers and enthusiasts. It encompasses the following design patterns:

- **Strategy Pattern**: Used for transaction strategies (Withdrawal and Deposit).
- **Command Pattern**: Implemented for ATM operations (Insert Card, Eject Card, Enter PIN, etc.).
- **Observer Pattern**: Employs observers to update the ATM user interface when the ATM state changes.
- **Factory Method Pattern**: Provides factories for creating different transaction objects.
- **State Pattern**: Represents various states of the ATM (Idle, CardInserted, PINEntered) and handles operations accordingly.

## Features

- Interactive ATM interface with options to:
  - Insert and eject cards.
  - Enter a PIN.
  - Withdraw cash.
  - Deposit cash.
  - Check balance.
- Enforces PIN-based authentication.
- Supports different transaction strategies (Withdrawal and Deposit) with customizable limits.
- State-based operation handling for secure and logical transitions.
- User-friendly error handling and feedback.

## Design Patterns

### Strategy Pattern

The Strategy pattern is applied to transaction strategies, allowing for easy addition of new strategies.

### Command Pattern

The Command pattern separates commands from their execution, enabling the addition of new commands without modifying existing code.

### Observer Pattern

The Observer pattern updates the ATM user interface when the ATM state changes, promoting decoupling between the UI and ATM logic.

### Factory Method Pattern

Transaction factories implement a Factory Method to create specific transaction objects with different strategies.

### State Pattern

The State pattern represents various ATM states and controls operations based on the current state.




# Task Tracker CLI

A simple command-line application for managing tasks, built with Java.

Task CLI allows you to create, update, delete, track, and list tasks directly from your terminal.

## Features

- **Add tasks:** Create new tasks with a description.
- **Update tasks:** Modify the description of an existing task.
- **Delete tasks:** Remove tasks by their ID.
- **Track progress:** Mark tasks as `IN-PROGRESS` or `DONE`.
- **List tasks:** Display all tasks or filter them by status.
- **Data persistence:** Automatically save tasks to a JSON file.

## Requirements

- Windows
- Java 25 or later

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/eduardorbs1/Task-Tracker-CLI
cd task-cli
```

### 2. Configure the PATH

Add the absolute path of the `dist` directory to your Windows `PATH` environment variable.

For example:

```text
C:\Users\YourUser\task-cli\dist
```

This allows you to execute `task-cli` from any terminal directory.

### 3. Verify the installation

```bash
task-cli list
```

## Usage

### Add a task

```bash
task-cli add "Buy groceries"
```

### Update a task

```bash
task-cli update 1 "Buy groceries and cook dinner"
```

### Delete a task

```bash
task-cli delete 1
```

### Mark a task as in progress

```bash
task-cli mark-in-progress 1
```

### Mark a task as done

```bash
task-cli mark-done 1
```

### List all tasks

```bash
task-cli list
```

### Filter tasks by status

```bash
task-cli list todo
task-cli list in-progress
task-cli list done
```

## Data Storage

Tasks are automatically saved in a JSON file located in the user's home directory:

```text
~/.task-cli/tasks.json
```

The data directory is created automatically when the application starts.

## Technologies

- Java 25
- Java NIO (`java.nio.file`)


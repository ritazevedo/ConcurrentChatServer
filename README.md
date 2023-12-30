# Concurrent Chat Server 💬

This project implements a concurrent chat server where clients can connect via Netcat using port 7075. The chat supports interactions between participants, allowing messaging to everyone and specific commands.

## Features

- **Chat Connection:** Users must provide a name upon connecting to join the chat.
- **Public Messages:** Any message sent is broadcasted to all connected clients.
- **Available Commands:**
    - `/whisper-<name>-<message>`: Sends a private message to the specified user.
    - `/list`: Lists the names of people in the chat.
    - `/quit`: Allows exiting the chat and notifies other users about the departure.

## How to Use

### Running the Chat Program

#### Server Setup (Java)

1. **Compile Java Code:**
   Ensure you have the Java Development Kit (JDK) installed.
   ```bash
   javac ChatServer.java
   ```
This command compiles the Java code, generating the necessary .class files.

2. **Start the Server:**
Run the compiled Java server program.
    ```bash
    java ChatServer
   ```
This initiates the server, ready to accept connections on port 7075.

#### Connecting to the Chat (Using Terminal)

1. **Open a Terminal:**
Open a new terminal window or tab.

2. **Connect as a Client (Using Netcat):**
Use the following command to connect to the chat server:
    ```bash
    nc localhost 7075
   ```
This command connects you to the chat server, allowing interaction with other users.

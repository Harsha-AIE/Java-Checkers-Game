# ♟️ Java Checkers Game

![Banner](assets/banner.png)
![License](https://img.shields.io/github/license/username/Java-Checkers-Game)

![Stars](https://img.shields.io/github/stars/username/Java-Checkers-Game)

![Forks](https://img.shields.io/github/forks/username/Java-Checkers-Game)

![Issues](https://img.shields.io/github/issues/username/Java-Checkers-Game)

![Pull Requests](https://img.shields.io/github/issues-pr/username/Java-Checkers-Game)

![Java](https://img.shields.io/badge/Java-17-orange)

![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue)

![AWT](https://img.shields.io/badge/AWT-Graphics-success)

![Docker](https://img.shields.io/badge/Docker-Ready-blue)

![GitHub Actions](https://img.shields.io/badge/CI-GitHub%20Actions-success)

![Open Source](https://badgen.net/badge/Open%20Source/Yes/green)

![Made with Love](https://img.shields.io/badge/Made%20With-❤-red)

![Visitors](https://visitor-badge.laobi.icu/badge?page_id=username.Java-Checkers-Game)

![Version](https://img.shields.io/badge/version-v1.0.0-blue)

![Platform](https://img.shields.io/badge/platform-Windows%20%7C%20Linux%20%7C%20macOS-success)
> A modern implementation of the classic Checkers (Draughts) game using Java Swing with intelligent gameplay, clean Object-Oriented Design, and an interactive graphical interface.

---

## ⭐ Overview

Java Checkers Game is a desktop application developed using Java Swing and AWT that recreates the traditional Checkers board game with modern software engineering practices.

The project demonstrates:

- Object-Oriented Programming
- GUI Development
- Event Handling
- Game Logic
- AI Opponent
- State Management
- Undo Operations

It is designed as both a playable desktop game and an educational Java project.

---

# Why this Project?

Many beginner Java games focus only on visuals.

This project focuses equally on:

✔ Software Architecture

✔ Clean OOP Design

✔ User Experience

✔ Maintainable Code

✔ Real Game Rules

✔ Intelligent Computer Player

---

# Features

✅ Player vs Player

✅ Player vs Computer

✅ Interactive Java Swing GUI

✅ Undo Last Move

✅ Move Highlighting

✅ King Promotion

✅ Automatic Capture Detection

✅ Legal Move Validation

✅ Game State Management

✅ Responsive Mouse Controls

✅ Clean Desktop UI

---

# Architecture

```mermaid
flowchart TD

A[Player Input]

B[GUI Layer]

C[Game Controller]

D[Board Manager]

E[Move Validator]

F[Game Rules]

G[AI Opponent]

H[Board Renderer]

A --> B

B --> C

C --> D

D --> E

E --> F

F --> H

F --> G

G --> D
```

---

# Project Structure

```
Java-Checkers-Game/

│

├── src/

│ ├── Checkers.java

│ ├── Board.java

│ ├── GameLogic.java

│ ├── AI.java

│ └── Utils.java

│

├── assets/

│ ├── screenshots/

│ └── banner.png

│

├── docs/

├── tests/

├── examples/

├── README.md

├── LICENSE

└── .gitignore
```

---

# Tech Stack

| Category | Technology |
|------------|------------|
| Language | Java |
| GUI | Swing |
| Graphics | AWT |
| IDE | IntelliJ / Eclipse / NetBeans |
| Paradigm | Object-Oriented Programming |
| Version Control | Git |
| Build Tool | javac |

---

# Screenshots

```
assets/screenshots/gameplay.png

assets/screenshots/menu.png

assets/screenshots/player-vs-computer.png
```

---

# Demo

> 🎥 Demo GIF Here

```
assets/demo.gif
```

---

# Installation

```bash
git clone https://github.com/username/Java-Checkers-Game.git

cd Java-Checkers-Game

javac src/*.java

java Checkers
```

---

# Requirements

- Java JDK 17+
- Git

---

# Environment Variables

No environment variables required.

---

# Run Locally

```bash
git clone <repo>

cd Java-Checkers-Game

javac src/*.java

java Checkers
```

---

# Docker

```bash
docker build -t java-checkers .

docker run java-checkers
```

---

# Usage

Launch the application

↓

Select Game Mode

↓

Choose your move

↓

Capture opponent pieces

↓

Win the game

---

# Game Rules

- Diagonal movement
- Mandatory captures
- King promotion
- Player turns
- Win detection
- Undo support

---

# AI

Current AI:

- Rule-based opponent
- Prioritizes captures
- Random legal move selection

Future:

- Minimax
- Alpha-Beta Pruning
- Difficulty Levels

---

# Performance

| Metric | Value |
|---------|-------|
| Startup | <1 sec |
| Memory | Low |
| Board Size | 8×8 |
| Players | PvP / PvC |

---

# Roadmap

- Better AI

- Multiplayer

- Online Mode

- Sound Effects

- Animations

- Themes

- Save Games

- Difficulty Levels

---

# Known Limitations

- Basic AI

- Desktop only

- No networking

- No save/load

---

# Testing

✔ Manual Testing

✔ Gameplay Validation

✔ Rule Verification

✔ Undo Testing

---

# Deployment

Desktop Java Application

Compatible with:

- Windows

- Linux

- macOS

---

# Security

No external data collection.

Runs completely offline.

---

# Contributing

Contributions are welcome!

Please open an Issue before submitting major changes.

---

# Versioning

Semantic Versioning (SemVer)

---

# FAQ

### Does it support AI?

Yes.

### Can two players play?

Yes.

### Is Undo available?

Yes.

### Is this beginner friendly?

Absolutely.

---

# License

MIT License

---

# Author

G Harshavardhan

AI Engineering Undergraduate

Amrita Vishwa Vidyapeetham

---

# Support

⭐ Star this repository if you found it useful.

🍴 Fork it to contribute.

🐞 Open an Issue for bugs.

---

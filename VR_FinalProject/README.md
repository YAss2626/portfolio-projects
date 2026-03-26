# Immersive VR Exploration of Mars

An educational virtual reality experience that allows users to explore Mars, complete scientific missions, and learn through interactive gameplay.

---

## Project Overview

This project is a VR-based educational simulation designed to promote active learning through exploration and interaction.

### Player Objectives

* Start from a research base
* Complete four scientific missions
* Explore the Martian environment
* Identify and examine minerals
* Engage with interactive quiz-based learning

The core idea behind this project is learning through direct interaction rather than passive observation.

---

## Features

### Implemented Features

* Mission-based progression system
* Interactive quiz system integrated into gameplay
* Point and reward system to encourage exploration
* Basic accessibility improvements
* Optimized VR interactions for better usability

### Partially Implemented / Future Features

* Multiple planets
* Advanced gravity system
* Expanded points of interest
* Achievements system
* Exploration vehicles (rovers)

---

## Technical Challenges

### Performance Constraints

* Large 3D models caused performance issues on Meta Quest 3
* Frequent lag and crashes during development
* Limitations of the A-Frame framework

### Interaction Issues

* Teleportation mechanics caused clipping and instability
* Object collision and phasing issues
* Scaling and positioning of large models (e.g., Olympus Mons)

### Solutions Implemented

* Replaced heavy 3D models with optimized versions
* Simplified interaction systems
* Switched teleportation to fixed positions for stability
* Focused on performance optimization within A-Frame instead of switching to Unity

---

## Technologies Used

* A-Frame (WebVR framework)
* JavaScript
* Web-based VR deployment
* Git for version control

---

## Demo

Video demonstration:
https://youtu.be/lYZd_KFn7E0

---

## Key Learnings

* Large assets significantly impact VR performance
* Simpler interactions improve both usability and learning outcomes
* Testing in VR environments is essential (desktop testing is not sufficient)
* Team coordination is critical when working with multiple contributors
* Gamification (missions and rewards) enhances user engagement

---

## Team

* Justin
* Denis
* Yacine
* Hannielle

---

## Setup Instructions

1. Clone the repository

```bash
git clone https://github.com/YAss2626/portfolio-projects.git
```

2. Navigate to the project folder

```bash
cd VR_FinalProject
```

3. Install dependencies

```bash
npm install
```

4. Run the project

```bash
npm start
```

---

## Notes

This project was developed as part of an academic course and focuses on combining software development with immersive educational experiences in virtual reality.

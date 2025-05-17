# Minecraft Damage Calculator

A Java-based application for calculating damage in Minecraft, considering weapons, armor, and enchantments. The project features a console application and an Electron-based GUI (work in progress), with data stored in an SQLite database.

## Features

- **Accurate Minecraft Combat Mechanics:** Calculates damage using real Minecraft formulas, including armor and toughness.
- **Database Integration:** Uses SQLite to store mobs, weapons, armor, materials, effects, and enchantments.
- **Console Application:** Interactive CLI for selecting mobs, weapons, and armor, and calculating damage.
- **Electron GUI:** Modern graphical interface (in development).
- **Extensible Models:** Easily add new mobs, weapons, armor, effects, and enchantments.

## Getting Started

### Prerequisites

- Java 17+ (for the console app)
- Node.js & npm (for the Electron GUI)
- Python 3 (for database setup scripts)

### Setup

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/MinecraftCalc.git
   cd MinecraftCalc/MinecraftCalculator
   ```

2. **Set up the database:**

   ```sh
   cd scripts
   python dbCreator.py
   cd ..
   ```

   This creates `minecraftDB.sqlite` and populates it with sample data.

3. **Run the console application:**

   ```sh
   javac -d out src/**/*.java
   java -cp out MinecraftConsoleApp
   ```

4. **Run the GUI (optional, in development):**
   ```sh
   cd gui
   npm install
   npm start
   ```

## Usage

- **Console App:** Follow the menu prompts to select mobs, weapons, and armor, and calculate damage.
- **GUI:** (Work in progress) Launches a graphical interface for the same functionality.

## Database

The database schema and initial data are defined in [`scripts/setup.sql`](scripts/setup.sql) and loaded by [`scripts/dataLoader.py`](scripts/dataLoader.py).

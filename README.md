# Qwirkle Command-Line Game - Java Project

## Description

This project is a Java implementation of the popular tile-based game **Qwirkle**. The game is played in the command-line interface, and supports multiple players. The goal is to place tiles on the board in rows or columns, matching either the color or shape, to score points. The player with the highest score at the end of the game wins.

This project demonstrates object-oriented programming concepts, including class design, game logic implementation, and player interaction using a command-line interface.

## Rules of Qwirkle

1. **Tiles**: Each tile has one of six shapes and one of six colors. No two tiles have the same combination of color and shape.
2. **Placing Tiles**: Players take turns placing 1-6 tiles on the board. Tiles must be placed in a line, either matching the color or shape with the adjacent tiles.
3. **Scoring**: Points are awarded for each tile placed, as well as any new rows or columns formed. Completing a set of six tiles in a row or column (a "Qwirkle") awards bonus points.
4. **Winning**: The game ends when the bag of tiles is empty and one player has placed all their tiles. The player with the most points wins.

## Features

- **Command-Line Interface**: The game is entirely text-based, with prompts for player input.
- **Multiple Players**: Supports 2-4 players.
- **Turn-Based Gameplay**: Players take turns placing tiles and receiving points based on the game rules.
- **Score Calculation**: Points are automatically calculated based on the placement of tiles and the formation of rows or columns.
- **Random Tile Bag**: Tiles are drawn from a randomized bag, ensuring each game is unique.

## Technologies Used

- **Java**: The game is implemented using standard Java, including object-oriented principles like inheritance and polymorphism.
- **JUnit**: For unit testing of game components.
- **Maven**: For dependency management and project structure (optional).

## Prerequisites

To run or develop this project, you will need:

- **Java 8 or higher**
- **Maven** (optional, but recommended for managing dependencies)

## Installation and Setup

To set up and run the project locally, follow these steps:

1. **Clone the Repository**

```bash
git clone https://github.com/jawad-elHarrasi/Qwirkle.git
cd Qwirkle

# ImagePuzzle 
[![Circle CI](https://circleci.com/gh/cooperkong/ImagePuzzle.svg?style=svg)](https://circleci.com/gh/cooperkong/ImagePuzzle)
[![Coverage Status](https://coveralls.io/repos/github/cooperkong/ImagePuzzle/badge.svg?branch=master)](https://coveralls.io/github/cooperkong/ImagePuzzle?branch=master)

A image puzzle project where user can **click/swipe/drag and drop** an available tile to solve the puzzle.


Outline
====
This project is created on top of an existing open source [project](https://github.com/davidvavra/Android-Slider-Puzzle) 4 years ago.It contains several bug fixes, optimizations, better coding structure and UI tests.
Screenshot
====
![](https://raw.githubusercontent.com/cooperkong/ImagePuzzle/master/app/example.gif)

Develpoment
====
1. Android Studio + Gradle
2. Design pattern
 * MVP to separate the view and logic layer
3. BDD using Cucumber
 * Feature files with tests associated
4. Testing
 * Espresso test
 * Unit test logic between view and presenter
5. CI
 * Circle CI with UI automation tests running every build
6. Code coverage
 * Coveralls

Known issues
====
Drag and drop has view defects related to tile orders.

Future thoughts
====
1. Create shuffle algorithms to shuffle tiles
2. algorithm needs to be smart to not create unsolvable puzzles
3. test to solve puzzl automatically.

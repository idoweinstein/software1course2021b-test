## Sanity
### ALL FUNCTIONS
* Load resources/hw10/ten.txt<br>
  Score is zero<br>
  Pass is enabled<br>
  50-50 is enabled
* Choose 'RIGHT'<br>
  Score is 3<br>
  50-50 button is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Choose 'WRONG1'<br>
  Score is 1<br>
  50-50 button is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Choose 'WRONG2'<br>
  Score is -1<br>
  50-50 button is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Click on Pass<br>
  Score is -1<br>
  50-50 button is enabled<br>
  Pass button is disabled<br>
  A different number appears as a question
* Choose 'RIGHT'<br>
  Score is 2<br>
  50-50 button is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Click on 50-50<br>
  Score is 2<br>
  50-50 is disabled<br>
  Pass button is enabled<br>
  Question number doesn't change<br>
  Two 'WRONG' buttons are disabled
* CHOOSE the remaining 'WRONG'<br>
  Score is 0<br>
  50-50 is disabled<br>
  Pass button is disabled<br>
  A different number appears as a question
* Choose 'RIGHT'<br>
  Score is 3<br>
  50-50 is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Click on 50-50<br>
  Score is 3 or 2<br>
  50-50 is disabled<br>
  Pass button is enabled<br>
  Question number doesn't change<br>
  Two 'WRONG' buttons are disabled
* Click on Pass<br>
  Score is 1<br>
  50-50 is enabled<br>
  Pass button is enabled<br>
  A different number appears as a question
* Click on Pass<br>
  Score is 0<br>
  50-50 is disabled<br>
  Pass button is disabled<br>
  A different number appears as a question
* Click on 'RIGHT'
  Score is 3<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 3 after 7 questions.
  ```

### TEN RIGHT
* Load resources/hw10/ten.txt<br>
  Score is zero<br>
  Pass is enabled<br>
  50-50 is enabled
* Peform the following 10 times:
  * Choose 'RIGHT'<br>
    Score increased by 3<br>
    50-50 button is enabled<br>
    Pass button is enabled<br>
    Different number appears as a question
* After the 10th time:<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 30 after 10 questions.
  ```

### TEN ALTERNATING
* Load resources/hw10/ten.txt<br>
  Score is zero<br>
  Pass is enabled<br>
  50-50 is enabled
* Peform the following 5 times:
  * Choose 'RIGHT'<br>
    Score increased by 3<br>
    50-50 button is enabled<br>
    Pass button is enabled<br>
    Different number appears as a question
  * Choose one of the 'WRONG'<br>
    Score decreased by 2<br>
    50-50 button is enabled<br>
    Pass button is enabled<br>
    Different number appears as a question
* Then:<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 5 after 10 questions.
  ```

## Funtional
### ONE RIGHT
* Load resources/hw10/one.txt<br>
  Score is zero<br>
  Pass is enabled<br>
  50-50 is enabled
* Choose 'RIGHT'
  Score is 3<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 3 after 1 questions.
  ```

### ONE WRONG
* Load resources/hw10/one.txt
* Choose 'WRONG1'<br>
  Score is 0<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is -2 after 1 questions.
  ```
* Load resources/hw10/one.txt
* Choose 'WRONG2'<br>
  Score is 0<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is -2 after 1 questions.
  ```
* Load resources/hw10/one.txt
* Choose 'WRONG3'<br>
  Score is 0<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is -2 after 1 questions.
  ```

### ONE 50-50
* Load resources/hw10/one.txt
* Click on 50-50<br>
  Score is 0<br>
  Two 'WRONG' answers are disabled<br>
  50-50 button is disabled<br>
  Pass button is enabled
* Choose 'RIGHT'<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 3 after 1 questions.
  ```

### THREE WRONG
* Load resources/hw10/ten.txt
* Perform the following 3 times
  * Choose one of the 'WRONG'<br>
    Score is 0<br>
    50-50 button is enabled<br>
    Pass button is enabled<br>
    Different number appears as a question
* After the 3rd time:<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is -6 after 3 questions.
  ```

## Edge cases
### EMPTY
* Load resources/hw10/empty.txt<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 0 after 0 questions.
  ```

### ONE PASS
* Load resources/hw10/one.txt
* Click on Pass<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is 0 after 0 questions.
  ```

### THREE WRONG WITH PASS
* Load resources/hw10/ten.txt
* Perform the following 2 times
  * Choose one of the 'WRONG'<br>
    Score is 0<br>
    50-50 button is enabled<br>
    Pass button is enabled<br>
    Different number appears as a question
* Click on PASS
* Choose 'WRONG3'<br>
  A dialog appears:
  ```
  Title: GAME OVER
  Content: Your final score is -6 after 3 questions.
  ```

## Strict (skipped)
### NON EXISTENT FILE
* In the textbox, write nonexistentfile.txt
* Click on Play!
  A dialog appears:
  ```
  Title: Error
  Content: File nonexistentfile.txt not found
  ```

### DIFFERENT FILE FORMAT
* Load resources/hw10/tests.md
  A dialog appears:
  ```
  Title: Error
  Content: Trivia file format error: Trivia file row must containing a question and four answers, seperated by tabs. (row 1)
  ```

### INVALID LINE
* Load resources/hw10/invalidline.txt
  A dialog appears:
  ```
  Title: Error
  Content: Trivia file format error: Trivia file for must containing a question and four answers, seperated by tabs. (row 2)
  ```

### EXIT BROWSE DIALOG
* Click on Browse
* Click on the X button to close the window<br>
  Program is still running<br>
  Textbox content doesn't change

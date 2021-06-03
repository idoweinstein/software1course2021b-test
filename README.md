# software1course2021b-test
Tel Aviv University / Software 1, 0368-2157 common test repository
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This is a pubilc repository! <br>
>⚠️&nbsp;&nbsp;In this repository we share tests only <br>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Homework solution should be commited to your private repository
## JUnit
The tests in this repository require JUnit.

### JUnit installation
#### Eclipse
* download JUnit tools : https://marketplace.eclipse.org/content/junit-tools <br>
* Open eclipse, click Project -> properties -> Java Build Path -> Classpath -> Add Library -> JUnit, pick JUnit 5 <br>
* You can run the test normally now
#### IntelliJ
IntelliJ: https://www.jetbrains.com/help/idea/testing.html <br>
See: Add testing libraries if there's a problem with importing.
#### VSCode
VSCode: https://youtu.be/LRkqvZs857c?t=176
#### Nova
* Log in to Nova
* Set your github username and exercise number. <br>
  For example, if your username is `israelaisraelit` and the exercise number is `20`, run the following commands:
  ```
  setenv USERNAME israelaisraelit
  setenv EX_NO 20
  ```
* Clone the tests:
  ```
  mkdir -p git/$EX_NO && cd git/$EX_NO
  git clone https://github.com/idoweinstein/software1course2021b-test.git --depth 1
  cd software1course2021b-test
  ```
* Run tests (enter your github password if asked for):
  ```
  make run
  ```
* (Optional) Re-run tests after updating your code on github: 
  ```
  make clean
  make update
  make run
  ``` 

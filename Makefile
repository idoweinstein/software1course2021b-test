JAVAC = javac
JAVA = java

JAVAC_FLAGS = -Xlint:all -Werror

EX_NO := ${EX_NO}
USERNAME := ${USERNAME}
TESTS_DIR := software1course2021b-test
CODE_DIR := hw$(EX_NO)-$(USERNAME)
EXERCISE_PATH := src/il/ac/tau/cs/sw1/ex$(EX_NO) 

JUNIT_URL := https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.2/junit-platform-console-standalone-1.7.2.jar
LIBS := junit-platform-console-standalone-1.7.2.jar

.ONESHELL:

.PHONY: all
all: run

merge:
	@rm -rf .git
	@echo "Cloning your project from github"
	@git clone https://$(USERNAME)@github.com/software1course2021b/$(CODE_DIR).git
	@echo "Merging your project with the tests"
	@cp -rT $(CODE_DIR) .
	@rm -rf $(CODE_DIR)
	@touch merge

junit-platform-console-standalone-1.7.2.jar:
	@echo Downloading JUnit
	@wget $(JUNIT_URL)

build: merge $(LIBS)
	@echo Building the project
	@mkdir $@
	find ./src/il/ac/tau/cs/sw1/ex$(EX_NO) -name '*.java' | xargs $(JAVAC) -d $@ -cp $(LIBS)

.PHONY: run
run: build
	@echo Running the tests
	@$(JAVA) -jar $(LIBS) --class-path $^ --include-classname=.\* --scan-class-path

.PHONY: clean
clean:
	@echo Removing latest build
	@rm -rf build

.PHONY:
update: merge 
	@echo Pulling updates from the github
	@git pull


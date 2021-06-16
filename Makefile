JAVAC = javac
JAVA = java

BUILD_DIR := bin

EX_NO := ${EX_NO}
USERNAME := ${USERNAME}
CODE_DIR := hw$(EX_NO)-$(USERNAME)

ifneq ($(EX_NO),10)
	EXERCISE_TEST_PATH := src/il/ac/tau/cs/sw1/ex$(EX_NO)
	EXERCISE_CODE_PATH := $(CODE_DIR)/src/il/ac/tau/cs/sw1/ex$(EX_NO)
else
# Exercise 10 requires specific configuration
	EXERCISE_TEST_PATH := src/enumRiddles src/riddles src/il/ac/tau/cs/sw1/ex9/TesterUtil.java
	EXERCISE_CODE_PATH := $(CODE_DIR)/src/enumRiddles $(CODE_DIR)/src/riddles

	SWT_URL := https://www.eclipse.org/downloads/download.php?file=/eclipse/downloads/drops4/R-4.14-201912100610/swt-4.14-gtk-linux-x86_64.zip
	SWT_ZIP := swt.zip

	APP_NAME := trivia.jar
	TRIVIA_CODE_PATH := $(CODE_DIR)/src/il/ac/tau/cs/sw1/trivia
	MAIN_CLASS := swt.jar il.ac.tau.cs.sw1.trivia.TriviaMain

	TRIVIA_SOURCES := $(shell find $(TRIVIA_CODE_PATH) -name '*.java' 2>/dev/null)

	TESTS_MD_URL := TBD
endif

JUNIT_URL := https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.2/junit-platform-console-standalone-1.7.2.jar
TEST_LIBS := junit-platform-console-standalone-1.7.2.jar

JAVAC_FLAGS := -Xlint:all -Werror

CODE_SOURCES := $(shell find $(EXERCISE_CODE_PATH) -name '*.java' 2>/dev/null)
TEST_SOURCES := $(shell find $(EXERCISE_TEST_PATH) -name '*.java' 2>/dev/null)


.PHONY: all
all: run

.PHONY: checkenv
checkenv:
	@if test "$(EX_NO)" = "" ; then \
          echo "EX_NO not set"; \
          exit 1; \
	fi

	@if test "$(USERNAME)" = "" ; then \
          echo "USERNAME not set"; \
          exit 1; \
	fi

$(CODE_DIR):
	@rm -rf .git
	@echo "Cloning your project from github"
	@git clone https://$(USERNAME)@github.com/software1course2021b/$(CODE_DIR).git

junit-platform-console-standalone-1.7.2.jar:
	@echo Downloading JUnit
	@wget $(JUNIT_URL)

$(BUILD_DIR):
	@mkdir $(BUILD_DIR)

.PHONY: build
build: checkenv $(BUILD_DIR) $(CODE_DIR) $(TEST_LIBS)
	@echo Building the project
	@echo Compiling your code
	@SOURCES=$$(echo $(CODE_SOURCES)) ;\
	 [ -z "$$SOURCES" ] && SOURCES=$$(find $(EXERCISE_CODE_PATH) -name '*.java') ;\
	 $(JAVAC) $(JAVAC_FLAGS) -d $(BUILD_DIR) $$SOURCES

	@echo Compiling tests code
	@$(JAVAC) $(JAVAC_FLAGS) -d $(BUILD_DIR) -cp $(TEST_LIBS):$(BUILD_DIR) $(TEST_SOURCES)

.PHONY: run
run: build $(TEST_LIBS) $(BUILD_DIR)
	@TMPDIR=$$(mktemp -d);\
	 chmod 700 $$TMPDIR ;\
	 cp -rT $(CODE_DIR) $$TMPDIR ;\
	 cp -rT . $$TMPDIR ;\
	 echo Running the tests ;\
	 cd $$TMPDIR ;\
	 $(JAVA) -jar $(TEST_LIBS) --class-path $(BUILD_DIR) --include-classname=.\* --scan-class-path ;\
	 rm -rf $$TMPDIR

.PHONY: clean
clean:
	@echo Removing latest build
	@rm -rf $(BUILD_DIR) $(APP_NAME)

.PHONY: update
update: checkenv $(CODE_DIR)
	@echo Pulling updates from the github
	@cd $(CODE_DIR) && git pull

# Exercise 10
ifeq ($(EX_NO),10)
	.PHONY: build_trivia
	build_trivia: $(BUILD_DIR) $(CODE_DIR) swt.jar
		@echo Building trivia
		@echo Compiling your code
		@SOURCES=$$(echo $(TRIVIA_SOURCES)) ;\
		 [ -z "$$SOURCES" ] && SOURCES=$$(find $(TRIVIA_CODE_PATH) -name '*.java') ;\
		 $(JAVAC) $(JAVAC_FLAGS) -cp swt.jar -d $(BUILD_DIR) $$SOURCES

	$(SWT_ZIP):
		@echo Downloading SWT library
		@wget -O $(SWT_ZIP) $(SWT_URL)

	swt.jar: $(SWT_ZIP)
		@echo Extracting SWT library
		@unzip -p $(SWT_ZIP) swt.jar > swt.jar

	$(APP_NAME): $(SWT_ZIP) build_trivia
		@echo Packing trivia app
		@unzip -p $(SWT_ZIP) swt.jar > $(APP_NAME)
		@jar ufe $(MAIN_CLASS) -C $(BUILD_DIR)/trivia . -J-Xmx1M

	.PHONY: trivia
	trivia: $(APP_NAME)
		@echo Opening tests manual
		@-firefox $(TESTS_MD_URL) & disown
		@echo Running the trivia app
		@java -jar $(APP_NAME)
endif
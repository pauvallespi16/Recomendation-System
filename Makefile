TARGET = EXE

CP = $(TARGET)/classes/
CP_TESTS = $(TARGET)/test-classes/
CP_DRIVERS = $(TARGET)/driver-classes/

DIRS = $(CP) $(CP_TESTS) $(CP_DRIVERS)

JFLAGS = -g
JC = javac
JAVA = java
JAR = jar

MAIN = presentacion.Main
MAIN_TERMINAL = presentacion.MainTerminal
VERSION = 3.0
JAR_FILE = $(TARGET)/SistemaRecomendacion-$(VERSION).jar

UI_JAR = libs/forms_rt.jar

TEST_JARS = libs/junit-jupiter-5.7.0.jar:libs/junit-jupiter-api-5.7.0.jar:libs/apiguardian-api-1.1.0.jar:libs/hamcrest-all-1.3.jar

JAVA_FILES = $(shell find FONTS/dominio/clases -type f -name '*.java') FONTS/dominio/controladores/*.java FONTS/persistencia/*.java FONTS/presentacion/*.java libs/inout.java

TEST_FILES = $(shell find FONTS/dominio/controladores/Tests -type f -name '*.java')

DRIVER_FILES = $(shell find FONTS/dominio/controladores/drivers -type f -name '*.java')

build: $(JAVA_FILES)
	$(JC) -d $(CP) -cp $(UI_JAR) $(JAVA_FILES)

clean:
	rm -r $(TARGET)

run:
	@$(JAVA) -cp $(CP):$(UI_JAR) $(MAIN)

run_terminal:
	@$(JAVA) -cp $(CP) $(MAIN_TERMINAL)

dirs:
	@mkdir -p $(DIRS)

build_test:
	make build
	$(JC) -d $(CP_TESTS) -cp $(CP):$(TEST_JARS) $(TEST_FILES)

run_test:
	@$(JAVA) -jar libs/junit-platform-console-standalone-1.7.0.jar -classpath $(CP):$(CP_TESTS) --scan-class-path

jar: $(JAR_FILE)

$(JAR_FILE): $(JAVA_FILES)
	make build
	$(JAR) -cfe $(JAR_FILE) $(MAIN) -C $(CP) .

runjar:
	@$(JAVA) -cp $(JAR_FILE) $(MAIN)

build_drivers: dirs
	make
	$(JC) -d $(CP_DRIVERS) -cp $(CP) $(DRIVER_FILES)

run_DK:
	$(JAVA) -cp $(CP):$(CP_DRIVERS) DriverKMeans

run_DN:
	$(JAVA) -cp $(CP):$(CP_DRIVERS) DriverNearestNeighbors

run_DS:
	$(JAVA) -cp $(CP):$(CP_DRIVERS) DriverSlopeOne

run_DRs:
	$(JAVA) -cp $(CP):$(CP_DRIVERS) DriverRecomendaciones

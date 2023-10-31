# Constants

COLOR_BLUE=\033[0;34m
COLOR_NONE=\033[0m

GRADLE=./gradlew

# Targets

help:
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "$(COLOR_BLUE)%s|$(COLOR_NONE)%s\n", $$1, $$2}' \
		| column -t -s '|'

.PHONY: clean
clean: ## Remove all built artefacts
	@echo 'Removing artefacts'
	@$(GRADLE) clean

.PHONY: test
test: ## Run tests
	@echo 'Running tests'
	@$(GRADLE) test

dist: test ## Build the distribution
	@echo 'Building the distribution'
	@$(GRADLE) shadowJar

run: dist ## Build the distribution and run it
	@echo 'Running application'
	@java -jar ./build/libs/reminder-hackery-all.jar

coverage: ## Run a test coverage report
	@$(GRADLE) koverHtmlReport
	@open './build/reports/kover/html/index.html'


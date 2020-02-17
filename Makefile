.PHONY: build_grammar test clean
all: build_grammar test

build_grammar:
	java -jar sablecc.jar src/grammaireL.sablecc

test:
	@echo "### Starting Tests..."
	@cd test; ./evaluate.py
	@echo "### Finished Tests."

clean:
	@echo "### Cleaning build files in test/input/"
	@rm -f test/input/*.sc
	@rm -f test/input/*.sa
	@rm -f test/input/*.ts

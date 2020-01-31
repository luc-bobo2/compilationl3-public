all: build_grammar

build_grammar:
	java -jar sablecc.jar src/grammaireL.sablecc


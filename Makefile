all: build_grammar

build_grammar:
	java -jar sablecc.jar src/grammaireL.sablecc

clean:
	rm -f test/input/*.sc
	rm -f test/input/*.sa

compile:
	./compile_all.sh

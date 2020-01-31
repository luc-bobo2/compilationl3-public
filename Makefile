all: build_grammar build_compiler

build_grammar:
	rm -rf src/sc
	java -jar sablecc.jar regles.sablecc && mv sc src/sc

build_compiler:
	javac -cp src src/Compiler.java

clean:
	rm --force ./test/input/*.sc
	rm --force src/Compiler.class
	rm --recursive --force src/sc

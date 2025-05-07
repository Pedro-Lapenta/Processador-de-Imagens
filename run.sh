cd ./src
javac ./ImageGUI.java
mv ./*.class ../bin
jar cmf ../bin/META-INF/MANIFEST.MF ProcessadorDeImagens.jar ./ ../img ../bin
mv ProcessadorDeImagens.jar ../bin
cd ../bin
java -jar ProcessadorDeImagens.jar
cd ../

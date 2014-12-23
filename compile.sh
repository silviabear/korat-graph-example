#!/bin/bash
cd ~/Software/korat
javac -cp korat.jar Graph.java
mv -b ~/Software/korat/Graph.class ~/Software/korat/test
mv -b ~/Software/korat/Graph$Vertex.class ~/Software/korat/test
jar cf test.jar test
rm output.txt
for((i = 0; i < 6; i++))
do	java -classpath korat.jar:test.jar -Xverify:none korat.Korat --class test.Graph --finitization finiteGraph --args $i >> output.txt
done


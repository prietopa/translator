#Translator

##Description

A Java console text/properties file translator.
A first implementation only **spanish to Galician**.

##Execution examples
1. Ejecucion en modo consola, para traducir textos sencillos: 
<p> **java -jar translator-text_0.0.1.jar "Es mucho mas sencillo"**. </p>
<blockquote><p> RESPUESTA: É moito mais sinxelo </p></blockquote>
2. Ejecucion en modo consola, para traducir ficheros con la extension property: 
<p> **java -jar translator-property_0.0.1.jar "/Users/xxx/long/path/Language.properties"** </p>
<blockquote><p> Sin respuesta, *mirar log*. </p></blockquote>

##Log file
A log file is created in **logs/translator.log**

##Assembly
In pom file there are two profiles:
- property: **mvn assembly:assembly -DdescriptorId=jar-with-dependencies -P property**
- text: **mvn assembly:assembly -DdescriptorId=jar-with-dependencies -P text**


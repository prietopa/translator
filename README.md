#Translator

##Description

A Java console text/properties file translator.
A first implementation only **spanish to Galician**.
NEW update: Add proxy configuration.

##Execution examples
1. Ejecucion en modo consola, para traducir textos sencillos: 
<p> **java -jar translator-text_0.0.1.jar "Es mucho mas sencillo"**. </p>
<p> **java -jar translator-text_0.0.1.jar "Es mucho mas sencillo" host port user pass**. </p>
<blockquote><p> ANSWER: É moito mais sinxelo </p></blockquote>
2. Ejecucion en modo consola, para traducir ficheros con la extension property: 
<p> **java -jar translator-property_0.0.1.jar "/Users/xxx/long/path/Language.properties"** </p>
<p> **java -jar translator-property_0.0.1.jar "/Users/xxx/long/path/Language.properties" host port user pass** </p>
<blockquote><p> NOT ANSWER, *you must show log*. </p></blockquote>

##Log file
A log file is created in **logs/translator.log**

##Assembly
In pom file there are two profiles:
- property: **mvn assembly:assembly -DdescriptorId=jar-with-dependencies -P property**
- text: **mvn assembly:assembly -DdescriptorId=jar-with-dependencies -P text**

##BUGS
- Problems with encoding of diferent OS.
- In long property files, you must revised. Problems with encoding.

##TODO
- Google translator is not implemented. 
- Add other idiom to translate.

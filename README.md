treipigmei-chess-ai
===================

1. Instalează utilitarul ant
      
      sudo apt-get install ant

2. Creează fișierul jar asociat proiectului. Mergi în rădăcina proiectului (unde e src) și apoi rulează
 
      ant -f build.xml

3. Rulează XBoard

      xboard -cp -fcp "java -jar ./jar/TreiPigMei.jar" -debug

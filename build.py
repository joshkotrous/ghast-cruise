"""Build against the user's legally installed Minecraft 26.3 and Fabric libraries."""
import argparse, os, shutil, subprocess, zipfile
from pathlib import Path
p=argparse.ArgumentParser()
p.add_argument("--install",type=Path,required=True)
p.add_argument("--java-home",type=Path,required=True)
a=p.parse_args();r=Path(__file__).resolve().parent;b=r/"build";classes=b/"classes"
classes.mkdir(parents=True,exist_ok=True)
cp=os.pathsep.join(str(f) for f in [a.install/"versions/26.3/26.3.jar",*(a.install/"libraries").rglob("*.jar")] if zipfile.is_zipfile(f))
javac=a.java_home/"bin/javac.exe";java=a.java_home/"bin/java.exe"
subprocess.run([str(javac),"-proc:none","--release","25","-cp",cp,"-d",str(classes),*map(str,(r/"src/main/java").rglob("*.java")),*map(str,(r/"src/test/java").rglob("*.java"))],check=True)
subprocess.run([str(java),"-cp",str(classes)+os.pathsep+cp,"dev.ghastcruise.CruiseTest"],check=True)
jar=b/"ghast-cruise-1.0.0+mc26.3.jar"
with zipfile.ZipFile(jar,"w",zipfile.ZIP_DEFLATED) as z:
 for f in classes.rglob("*.class"):
  if f.name not in ("CruiseTest.class","RuntimeTest.class"):z.write(f,f.relative_to(classes))
 for f in (r/"src/main/resources").rglob("*"):
  if f.is_file():z.write(f,f.relative_to(r/"src/main/resources"))
 z.write(r/"LICENSE","LICENSE")
print("Built",jar)

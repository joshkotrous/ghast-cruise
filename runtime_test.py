"""Check mixin transformation before opening a game window, in an isolated test directory."""
import argparse, json, shutil, subprocess, zipfile
from pathlib import Path
p=argparse.ArgumentParser()
p.add_argument("--java",type=Path,required=True)
p.add_argument("--classpath-file",type=Path,required=True)
p.add_argument("--output",type=Path,required=True)
p.add_argument("--mods",type=Path,help="Optional existing mod set to check alongside Ghast Cruise")
a=p.parse_args();r=Path(__file__).resolve().parent
if a.output.exists():p.error("Use a new isolated output directory")
jar=r/"build/ghast-cruise-1.0.0+mc26.3.jar"
if not jar.exists():p.error("Run build.py first")
mods=a.output/"mods";mods.mkdir(parents=True)
if a.mods:
 for source in a.mods.glob("*.jar"):
  if not source.name.startswith("ghast-cruise"):shutil.copy2(source,mods/source.name)
shutil.copy2(jar,mods/jar.name)
with zipfile.ZipFile(mods/"ghast-cruise-test.jar","w") as z:
 z.writestr("fabric.mod.json",json.dumps({"schemaVersion":1,"id":"ghastcruisetest","version":"1.0.0","environment":"client","entrypoints":{"preLaunch":["dev.ghastcruise.RuntimeTest"]}}))
 for name in ["RuntimeTest","CruiseTest"]:z.write(r/("build/classes/dev/ghastcruise/"+name+".class"),"dev/ghastcruise/"+name+".class")
subprocess.run([str(a.java.resolve()),"-Xmx1G","-cp",a.classpath_file.read_text().strip(),"net.fabricmc.loader.impl.launch.knot.KnotClient","--gameDir",str(a.output.resolve()),"--version","26.3","--accessToken","0","--username","GhastCruiseTest"],check=True,timeout=90)

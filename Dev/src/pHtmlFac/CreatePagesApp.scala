package ostrat
package pHtmlFac

object CreatePagesApp extends App
{
   import StratPage._
   def nFile(fileName: String, str: String, dir: String): Unit =
   {
      //val dir = ife(stage, stageDir, fastPagesDir)
      import java.io._
      val pw = new PrintWriter(new File(dir / fileName))
      pw.write(str)
      pw.close
   }
   import sys.process._
   ("cp" -- onlyCss -- fastPagesDir / cssVersionName).!
   Rcom.local.foreach (pg => nFile(pg.fullLink, pg.out(false), fastPagesDir))
   println("fastPages" :- Rcom.local.length.toString -- "pages successfuly created")
   
   ("cp" -- onlyCss -- stageDir / cssVersionName).!
   Rcom().foreach (pg => nFile(pg.fullLink, pg.out(true), stageDir))
   println("staging" :- Rcom().length.toString -- "pages successfuly created")
   Rcom.jsDeploy.foreach(p => ("cp /openstrat/target" / p.jsFile / "target" / "scala-2.12" / p.fastName -- stageDir / p.stageName).!)
   println(Rcom.jsDeploy.length.toString -- "js files successfuly copied into staging")
   println("Exiting PagesApp")
}
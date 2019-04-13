/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

package object pFx
{ val userHomeDir: String = System.getProperty("user.home")
  val openStratDir: String = userHomeDir / "AppData/Local/OpenStratData"
  val openStratDevDir: String = openStratDir / "Dev"
  lazy val generalDevSettings: EMon[String] = loadRsonFile(openStratDevDir / "GeneralDevSettings.rson")
  def findDevSettingElse[A: Persist](settingSym: Symbol, elseValue: => A): A = generalDevSettings.findSettingElse(settingSym, elseValue)
  def saveRsonFile(path: String, fileName: String, output: String): Unit =
  { import java.io._
    val dir = new File(path)
    if (!dir.exists) dir.mkdirs
    val pw = new PrintWriter(new File(path / fileName))
    pw.write(output)
    pw.close
  }
   
  def loadRsonFile(pathFileName: String): EMon[String] = eTry(io.Source.fromFile(pathFileName).mkString)
  def fromRsonFileFind[A: Persist](fileName: String): EMon[A] = loadRsonFile(fileName).findType[A]
  def fromRsonFileFindElse[A: Persist](fileName: String, elseValue: => A): A = fromRsonFileFind(fileName).getElse(elseValue)
  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls
   *  procedure (Unit returning function) with that object of type A */
  def fromRsonFileFindForeach[A: Persist](fileName: String, f: A => Unit): Unit = fromRsonFileFind(fileName).foreach(f)
  def settFromFile[A: Persist](settingSym: Symbol, fileName: String): EMon[A] = loadRsonFile(fileName).findSett[A](settingSym)
  def settFromFileElse[A: Persist](settingSym: Symbol, fileName: String, elseValue: A): A = settFromFile[A](settingSym, fileName).getElse(elseValue)
}

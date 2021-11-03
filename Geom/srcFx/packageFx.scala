/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** This package is for JavaFx code.*/
package object pFx
{ val userHomeDir: String = System.getProperty("user.home")
  val yourDir: String = userHomeDir -/- "AppData/Local/OpenStratData"

  /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
  lazy val devSettingsStatements: EMon[Statements] = statementsFromResource("DevSettings.rson")

  /** Find a setting of the given name and and return its Expr from the file DevSettings.rson. */
  def findDevSettingExpr(settingStr: String): EMon[Expr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson. */
  def findDevSettingT[A: Persist](settingStr: String): EMon[A] = devSettingsStatements.flatMap(_.findSettingT(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
  def findDevSettingElse[A: Persist](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSettingT(settingStr)).getElse(elseValue)

  def saveRsonFile(path: String, fileName: String, output: String): Unit =
  { import java.io._
    val dir = new File(path)
    if (!dir.exists) dir.mkdirs
    val pw = new PrintWriter(new File(path -/- fileName))
    pw.write(output)
    pw.close
  }
   
  def loadRsonFile(pathFileName: String): EMon[String] = eTry(io.Source.fromFile(pathFileName).mkString)
  def fromRsonFileFind[A: Persist](fileName: String): EMon[A] = loadRsonFile(fileName).findType[A]
  def fromRsonFileFindElse[A: Persist](fileName: String, elseValue: => A): A = fromRsonFileFind(fileName).getElse(elseValue)
  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls
   *  procedure (Unit returning function) with that object of type A */
  def fromRsonFileFindForeach[A: Persist](fileName: String, f: A => Unit): Unit = fromRsonFileFind(fileName).forGood(f)
  def settFromFile[A: Persist](settingStr: String, fileName: String): EMon[A] = loadRsonFile(fileName).findSetting[A](settingStr)
  def settFromFileElse[A: Persist](settingStr: String, fileName: String, elseValue: A): A = settFromFile[A](settingStr, fileName).getElse(elseValue)
}

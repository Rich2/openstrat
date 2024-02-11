/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** This package is for JavaFx code.*/
package object pjvm
{ val userHomeDir: String = System.getProperty("user.home")
  val yourDir: String = userHomeDir / "AppData/Local/OpenStratData"

  /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
  lazy val devSettingsStatements: EMon[RArr[Statement]] = statementsFromResource("DevSettings.rson")

  /** Find a setting of the given name and and return its Expr from the file DevSettings.rson. */
  def findDevSettingExpr(settingStr: String): EMon[AssignMemExpr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson. */
  def findDevSettingT[A: Unshow](settingStr: String): EMon[A] = devSettingsStatements.flatMap(_.findSetting(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
  def findDevSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSetting(settingStr)).getElse(elseValue)

  /** Saves text file to specified file at given path directory. */
  def saveTextFile(path: String, fileName: String, output: String): Unit =
  { import java.io._
    val dir = new File(path)
    if (!dir.exists) dir.mkdirs
    val pw = new PrintWriter(new File(path / fileName))
    pw.write(output)
    pw.close
  }
   
  /** Attempts to load text file into a [[String]]. */
  def loadTextFile(pathFileName: String): EMon[String] = eTry(scala.io.Source.fromFile(pathFileName).mkString)

  /** Attempts to load a value of the specified type from an RSON format file. */
  def fromRsonFileFind[A: Unshow](fileName: String): EMon[A] = loadTextFile(fileName).findType[A]

  /** Attempts to load a value of the specified type from an RSON format file, in case of failure returns the else default value. */
  def fromRsonFileFindElse[A: Unshow](fileName: String, elseValue: => A): A = fromRsonFileFind(fileName).getElse(elseValue)

  /** Attempts to find find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls
   *  procedure (Unit returning function) with that object of type A */
  def fromRsonFileFindForeach[A: Unshow](fileName: String, f: A => Unit): Unit = fromRsonFileFind(fileName).forGood(f)

  /** Attempts to load the value of a setting of the specified name from a file. */
  def settFromFile[A: Unshow](settingStr: String, fileName: String): EMon[A] = loadTextFile(fileName).findSetting[A](settingStr)

  /** Attempts to load the value of a setting of the specified name from a file, in case of failure returns the else default value. */
  def settFromFileElse[A: Unshow](settingStr: String, fileName: String, elseValue: A): A = settFromFile[A](settingStr, fileName).getElse(elseValue)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on
   * success. */
  def fileWrite(path: DirPathAbs, fileName: String, content: String): EMon[String] = fileWrite(path.str, fileName, content)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on
   *  success. */
  def fileWrite(path: String, fileName: String, content: String): EMon[String] =
  { import java.io._
    var eStr: String = ""
    var opw: Option[FileWriter] = None
    try {
      new File(path).mkdir()
      opw = Some(new FileWriter(new File(path / fileName)))
      opw.get.write(content)
    }

    catch { case e: Throwable => eStr = e.toString }
    finally{ opw.foreach(_.close()) }
    if (eStr == "") Good("Successfully written file to " + path / fileName) else Bad(StrArr(eStr))
  }

  def homeWrite(dir: String, fileName: String, str: String): EMon[String] =
  { val h = System.getProperty("user.home")
    fileWrite(h / dir , fileName, str)
  }

  /** Function object apply method to get statements from a Java build resource. */
  def statementsFromResource(fileName: String): EMon[RArr[Statement]] =
    eTry(scala.io.Source.fromResource(fileName).toArray).flatMap(pParse.srcToEStatements(_, fileName))

  /** Function object apply method to get FileStatements from a Java build resource. */
  def fileStatementsFromResource(fileName: String): EMon[FileStatements] = statementsFromResource(fileName).map(FileStatements(_))
}
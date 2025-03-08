/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*, java.io.*, java.nio.file.NotDirectoryException

/** This package is for Java byte code targets. */
package object utiljvm
{ /** [[String]] for the user's home directory. */
  val userHomeDir: String = System.getProperty("user.home")

  val yourDir: String = userHomeDir / "AppData/Local/OpenStratData"

  /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
  lazy val devSettingsStatements: ThrowMonRArr[Statement] = statementsFromResource("DevSettings.rson")

  /** Find a setting of the given name and return its Expr from the file DevSettings.rson. */
  def findDevSettingExpr(settingStr: String): ThrowMon[AssignMemExpr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson. */
  def findDevSetting[A: Unshow](settingStr: String): ThrowMon[A] = devSettingsStatements.flatMap(_.findSetting(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
  def findDevSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSetting(settingStr)).getElse(elseValue)

  /** Find the [[String]] for the Identifer value of o setting of the given name in the file DevSettings.rson. */
  def findDevSettingIdStr(settingStr: String): ThrowMon[String] = devSettingsStatements.flatMap(_.findSettingId(settingStr))

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def projPathDo(f: DirsAbs => Unit): Unit = findDevSetting[DirsAbs]("projPath").forFold{ err => deb(err.toString) }{ path => f(path) }

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def stagingPathDo(f: DirsAbs => Unit): Unit = findDevSetting[DirsAbs]("stagingPath").forFold { err => deb(err.toString) } { path => f(path) }

  /** Possible path to the openstrat directory, if it can be found in Dev/User/DevSettings.rson file. */
  def openstratPath(): ThrowMon[DirsAbs] = findDevSetting[DirsAbs]("projPath")

  /** Possible path to the staging directory for openstrat artifacts, if it can be found in Dev/User/DevSettings.rson file. */
  def stagingPathFind: ThrowMon[DirsAbs] = findDevSetting[DirsAbs]("staging")

  /** Needs removal. */
  def sbtDirPath(): ThrowMon[String] = openstratPath().map(_.asStr / "Dev/SbtDir")

  /** Saves text file to specified file at given path directory. */
  def saveTextFile(path: String, fileName: String, output: String): Unit =
  { val dir: File = new File(path)
    if (!dir.exists) dir.mkdirs
    val pw = new PrintWriter(new File(path / fileName))
    pw.write(output)
    pw.close
  }

  /** Attempts to load text file into a [[String]]. */
  def loadTextFile(pathFileName: String): ThrowMon[String] = eTry(scala.io.Source.fromFile(pathFileName).mkString)

  /** Attempts to load a value of the specified type from an RSON format file. */
  def fromRsonFileFind[A: Unshow](fileName: String): ThrowMon[A] = loadTextFile(fileName).findType[A]

  /** Attempts to load a value of the specified type from an RSON format file, in case of failure returns the else default value. */
  def fromRsonFileFindElse[A: Unshow](fileName: String, elseValue: => A): A = fromRsonFileFind(fileName).getElse(elseValue)

  /** Attempts to find and load file, attempts to parse the file, attempts to find object of type A. If all stages successful, calls procedure (Unit returning
   * function) with that object of type A */
  def fromRsonFileFindForeach[A: Unshow](fileName: String, f: A => Unit): Unit = fromRsonFileFind(fileName).forSucc(f)

  /** Attempts to load the value of a setting of the specified name from a file. */
  def settFromFile[A: Unshow](settingStr: String, fileName: String): ErrBi[Throwable, A] = loadTextFile(fileName).findSetting[A](settingStr)

  /** Attempts to load the value of a setting of the specified name from a file, in case of failure returns the else default value. */
  def settFromFileElse[A: Unshow](settingStr: String, fileName: String, elseValue: A): A = settFromFile[A](settingStr, fileName).getElse(elseValue)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. */
  def fileWrite(path: DirsAbs, fileName: String, content: String): ErrBi[IOExc, FileWritten] = fileWrite(path /% fileName, content)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. */
  def fileWrite(pathName: String, content: String): ErrBi[IOExc, FileWritten] =
  { var oErr: Option[IOExc] = None
    var opw: Option[FileWriter] = None
    try
    { opw = Some(new FileWriter(new File(pathName)))
      opw.get.write(content)
    }

    catch { case e: IOExc => oErr = Some(e) }
    finally { opw.foreach(_.close()) }
    oErr.fld(Succ(FileWritten(pathName)), FailIO(_))
  }

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[String]] for the path. */
  def htmlFileWrite(path: DirsAbs, fileName: String, content: String): ErrBi[IOExc, HtmlFileWritten] = htmlFileWrite(path /% fileName , content)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[DirAbs]] for the path. */
  def htmlFileWrite(pathName: String, content: String): ErrBi[IOExc, HtmlFileWritten] =
    fileWrite(pathName + ".html", content).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[String]] for the path. */
  def cssFileWrite(path: DirsAbs, fileName: String, content: String): ErrBi[IOExc, CssFileWritten] = cssFileWrite(path /% fileName, content)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[DirAbs]] for the path. */
  def cssFileWrite(pathName: String, content: String): ErrBi[IOExc, CssFileWritten] =
      fileWrite(pathName + ".css", content).map(fw => CssFileWritten(fw.detailStr))  

  def pomFileWrite(pathName: String, content: String): ErrBi[IOExc, PomFileWritten] =
    fileWrite(pathName + ".pom", content).map(fw => PomFileWritten(fw.detailStr))
  
  def fileCopy(fromStr:  String, toStr: String): ErrBi[Exception, FileWritten] =
  { import java.nio.file.*
    var oErr: Option[IOExc] = None
    try{ Files.copy(Paths.get(fromStr), Paths.get(toStr), StandardCopyOption.REPLACE_EXISTING) }
    catch { case e: IOExc => oErr = Some(e) }
    oErr.fld(Succ(FileWritten(toStr)), FailIO(_))
  }
  
  def jsFileCopy(fromStr:  String, toStr: String): ErrBi[Exception, JsFileWritten] = fileCopy(fromStr + ".js", toStr + ".js").map(fw => JsFileWritten(fw.detailStr))
  
  def mkDirExist(path: DirsAbs): ExcIOMon[DirExists] = mkDirExist(path.asStr)

  /** Confirm the location already exists as a directory or create the directory if the location does not exist. Fail isf the location already exists as a
   * file. */
  def mkDirExist(path: String): ExcIOMon[DirExists] =
  { val jp = new File(path)
    jp.exists match
    { case true => if (jp.isDirectory) Succ(DirExisted(path)) else Fail(new NotDirectoryException("path"))
      case false =>
      { var oExc: Option[IOExc] = None
        try{ jp.mkdir }
        catch{ case e: IOExc => oExc = Some(e) }
        oExc.fld(Succ(DirCreated(path)), Fail(_))
      }
    }
  }

  /** Write a [[String]] to a file in the subdirectory of the home directory. */
  def homeWrite(dir: String, fileName: String, str: String): ErrBi[IOExc, FileWritten] =
  { val h: String = System.getProperty("user.home")
    fileWrite(h / dir / fileName, str)
  }

  /** Function object apply method to get statements from a Java build resource. */
  def statementsFromResource(fileName: String): ThrowMonRArr[Statement] = eTry(io.Source.fromResource(fileName).toArray).flatMap(srcToEStatements(_, fileName))

  def strFromResource(fileName: String): ThrowMon[String] = eTry(io.Source.fromResource(fileName).toArray.mkString)

  /** Function object apply method to get FileStatements from a Java build resource. */
  def fileStatementsFromResource(fileName: String): ThrowMon[FileStatements] = statementsFromResource(fileName).map(FileStatements(_))

  def httpNow: String =
  { import java.time.*, java.time.format.*
    val time: ZonedDateTime = Instant.now().atZone(ZoneId.of("GMT"))
    time.format(DateTimeFormatter.RFC_1123_DATE_TIME)
  }

  implicit class DirPathAbsExtensions(val thisPath: DirsAbs)
  {
    def toJava: File = File(thisPath.asStr)

    /** Perform the side effecting procedure if the location exists and is a directory as opposed to a file. */
    def doIfDirExists(f: DirsAbs => Unit) =
    { val jd = thisPath.toJava
      if (jd.exists)
        if (jd.isDirectory) f(thisPath)
        else println(thisPath.notDirStr)
      else println(thisPath.noExistStr)
    }
  }
}
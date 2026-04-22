/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*, java.io.*

/** This package is for Java byte code targets. */
package object utiljvm
{ /** [[String]] for the user's home directory. */
  val userHomeDir: String = System.getProperty("user.home")

  val yourDir: String = userHomeDir / "AppData/Local/OpenStratData"
  
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
  def writeFile(pathName: String, content: String): ErrBi[IOExc, FileWritten] =
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

  /** Copies file from the full path-name of the first parameter to the full path-name of the second parameter. */
  def copyFile(fromStr:  String, toStr: String): ErrBi[Exception, FileWritten] =
  { import java.nio.file.*
    var oErr: Option[IOExc] = None
    try{ Files.copy(Paths.get(fromStr), Paths.get(toStr), StandardCopyOption.REPLACE_EXISTING) }
    catch { case e: IOExc => oErr = Some(e) }
    oErr.fld(Succ(FileWritten(toStr)), FailIO(_))
  }  

  /** Write a [[String]] to a file in the subdirectory of the home directory. */
  def homeWrite(dir: String, fileName: String, str: String): ErrBi[IOExc, FileWritten] =
  { val h: String = System.getProperty("user.home")
    writeFile(h / dir / fileName, str)
  }

  /** Function object apply method to get statements from a Java build resource. */
  def statementsFromResource(fileName: String): ThrowMonRArr[Statement] = eTry(io.Source.fromResource(fileName).toArray).flatMap(srcToEStatements(_, fileName))

  def strFromResource(fileName: String): ThrowMon[String] = eTry(io.Source.fromResource(fileName).toArray.mkString)

  /** Function object apply method to get FileStatements from a Java build resource. */
  def fileStatementsFromResource(fileName: String): ThrowMon[FileStatements] = statementsFromResource(fileName).map(FileStatements(_))

  /** The current GMT time as a [[String]] in [[RFC_1123_DATE_TIME]]. */
  def gmtNowStr: String =
  { import java.time.*, java.time.format.*
    val time: ZonedDateTime = Instant.now().atZone(ZoneId.of("GMT"))
    time.format(DateTimeFormatter.RFC_1123_DATE_TIME)
  }
}
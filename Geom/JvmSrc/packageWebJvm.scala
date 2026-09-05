/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb;
import pParse.*, java.io.File, scala.sys.process._

/** This package is for Java byte code targets. */
package object webjvm
{ /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
  lazy val devSettingsStatements: ThrowMonRArr[Statement] = utiljvm.statementsFromResource("DevSettings.rson")

  /** Find a setting of the given name and return its Expr from the file DevSettings.rson. */
  def findDevSettingExpr(settingStr: String): ThrowEither[AssignMemExpr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson. */
  def findDevSetting[A: Unshow](settingStr: String): ThrowEither[A] = devSettingsStatements.flatMap(_.findSetting(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
  def findDevSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSetting(settingStr)).getOrElse(elseValue)

  /** Find the [[String]] for the identifier value of o setting of the given name in the file DevSettings.rson. */
  def findDevSettingIdStr(settingStr: String): ThrowEither[String] = devSettingsStatements.flatMap(_.findSettingId(settingStr))

  /** Find the project path. */
  def projPathFind: ThrowEither[ScalaProjPath] = findDevSetting[DirsAbs]("projPath").map(_.projPath)

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def projPathDo(f: ScalaProjPath => Unit): Unit = projPathFind.fold { err => deb(err.toString) } { path => f(path) }

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def stagingPathDo(f: DirsAbs => Unit): Unit = findDevSetting[DirsAbs]("stagingPath").fold { err => deb(err.toString) } { path => f(path) }

  /** Possible path to the openstrat directory, if it can be found in Dev/User/DevSettings.rson file. */
  def openstratPath: ThrowEither[DirsAbs] = findDevSetting[DirsAbs]("projPath")

  /** Possible path to the staging directory for Openstrat artefacts, if it can be found in Dev/User/DevSettings.rson file. */
  def stagingPathFind: ThrowEither[DirsAbs] = findDevSetting[DirsAbs]("stagingPath")

  /** Copies file from the full path-name of the first parameter to the full path-name of the second parameter. */
  def copyFile(fromPath: DirsFileAbs, toPath: DirsFilePath): Either[Exception, FileWritten] = utiljvm.copyFile(fromPath.asStr, toPath.asStr)

  /** Copies file from the full path-name of the first parameter to the dir of the second parameter. */
  def copyFileTo(fromPath: DirsFileAbs, toPath: DirsPath): Either[Exception, FileWritten] = utiljvm.copyFile(fromPath.asStr, toPath.asStr / fromPath.fileNameStr)

  /** File copy that adds the ".js" [[String]] to the file source and file destination. */
  def jsFileCopy(fromStem: DirsAbsStem, toStem: DirsAbsStem): Either[Exception, JsFileWritten] =
    copyFile(fromStem ++ ".js", toStem ++ ".js").map(fw => JsFileWritten(fw.detailStr))

  /** File copy that adds the ".js.map" [[String]] to the file source and file destinations. */
  def jsMapFileCopy(fromStem: DirsAbsStem, toStem: DirsAbsStem): Either[Exception, JsFileWritten] =
    copyFile(fromStem ++ ".js.map", toStem ++ ".js.map").map(fw => JsFileWritten(fw.detailStr))
  
  /** File copy that adds the ".js" and ".js.map" [[String]]s to the file sources and file destinations. */
  def jsWithMapFileCopy(fromPath: DirsAbsStem, toPath: DirsAbsStem): Either[Exception, JsFileWritten] = 
  {
    
    val res1: Either[Exception, JsFileWritten] = utiljvm.copyFile(fromPath.asStr + ".js", toPath.asStr + ".js").map(fw => JsFileWritten(fw.detailStr))
    res1 match {
      case Right(jsfw) => utiljvm.copyFile(fromPath.asStr + ".js.map", toPath.asStr + ".js.map").map(fw => JsFileWritten(fw.detailStr)) match {
        case Left(_) => res1
        case Right(_) => Right(jsfw.withMap)
      }
      case fail => fail
    }
  }

  /** File copy that adds the ".js" and ".js.map" [[String]]s to the file sources and file destinations. */
  def jsWithMapFileRenameCopy(fromPath: DirsAbs, htmlDirPath: DirsAbs, fileNameStem: String): Either[Exception, JsFileWritten] =
  {
    val jsFile1: String = io.Source.fromFile(fromPath.asStr / "main.js").mkString
    val jsFile2 = jsFile1.replace("sourceMappingURL=main.js.map", "sourceMappingURL=" + fileNameStem + "js.map")    
    val jsMapFile1: String = io.Source.fromFile(fromPath.asStr / "main.js.map").mkString
    val jsMapFile2 = jsMapFile1.replace("""file\":\"main.js""", """file\":\"""" + fileNameStem + "js")    
    val destDir: DirsAbs = htmlDirPath / fileNameStem
    destDir.mkExist
    //val destStem: DirsAbsStem = destDir :-/ fileNameStem
    val res1: Either[Exception, JsFileWritten] = utiljvm.writeFile(destDir.asStr / fileNameStem + ".js", jsFile2).map(fw => JsFileWritten(fw.detailStr))
    res1 match {
      case Right(jsfw) => utiljvm.writeFile(destDir.asStr / fileNameStem + ".js.map", jsMapFile2).map(fw => JsFileWritten(fw.detailStr)) match {
        case Left(_) => res1
        case Right(_) => Right(jsfw.withMap)
      }
      case fail => fail
    }
  }

  /** Confirm the location already exists as a directory or create the directory if the location does not exist. Fail isf the location already exists as a
   * file. */
  def mkDirExist(path: String): IOExcEither[DirExists] =
  { val jp = new File(path)
    jp.exists match
    { case true if (jp.isDirectory) => Right(DirExisted.str(path))
      case true => Left(new java.nio.file.NotDirectoryException("path"))
      case _ =>
      { var oExc: Option[IOExc] = None
        try { jp.mkdir }
        catch { case e: IOExc => oExc = Some(e) }
        oExc.fld(Right(DirCreated.str(path)), Left(_))
      }
    }
  }

  /** Copies a jar file */
  def jarFileCopy(fromStr: String, toStr: String): Either[Exception, JarFileWritten] =
    utiljvm.copyFile(fromStr + ".jar", toStr + ".jar").map(fw => JarFileWritten(fw.detailStr))


  /** Write the content [[String]] to the given path. Method adds ".pom" extension. */
  def writePom(pathName: String, content: String): Either[IOExc, PomFileWritten] =
    utiljvm.writeFile(pathName + ".pom", content).map(fw => PomFileWritten(fw.detailStr))
}
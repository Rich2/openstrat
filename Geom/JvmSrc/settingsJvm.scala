/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import pParse.*, pweb.*, java.io.File, scala.sys.process.*, java.io.IOException

 /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
lazy val devSettingsStatements: ExcEither[RArr[Statement]] = utiljvm.statementsFromResource("DevSettings.rson")

/** Find a setting of the given name and return its Expr from the file DevSettings.rson. */
def findDevSettingExpr(settingStr: String): ExcEither[AssignMemExpr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

/** Find a setting of the given name and type from the file DevSettings.rson. */
def findDevSetting[A: Unshow](settingStr: String): ExcEither[A] = devSettingsStatements.flatMap(_.findSetting(settingStr))

/** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
def findDevSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSetting(settingStr)).getOrElse(elseValue)

/** Find the [[String]] for the identifier value of o setting of the given name in the file DevSettings.rson. */
def findDevSettingIdStr(settingStr: String): ExcEither[String] = devSettingsStatements.flatMap(_.findSettingId(settingStr))

/** Find the project path. */
def projPathFind: ExcEither[ScalaProjPath] = findDevSetting[DirPathAbs]("projPath").map(_.projPath)

/** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
def projPathDo(f: ScalaProjPath => Unit): Unit = projPathFind.fold { err => deb(err.toString) } { path => f(path) }

/** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
def stagingPathDo(f: DirPathAbs => Unit): Unit = findDevSetting[DirPathAbs]("stagingPath").fold { err => deb(err.toString) } { path => f(path) }

/** Possible path to the openstrat directory, if it can be found in Dev/User/DevSettings.rson file. */
def openstratPath: ExcEither[DirPathAbs] = findDevSetting[DirPathAbs]("projPath")

/** Possible path to the staging directory for Openstrat artefacts, if it can be found in Dev/User/DevSettings.rson file. */
def stagingPathFind: ExcEither[DirPathAbs] = findDevSetting[DirPathAbs]("stagingPath")

/** Copies file from the full path-name of the first parameter to the full path-name of the second parameter. */
def copyFile(fromPath: FilePathAbs, toPath: FilePathBase): IOExcEither[FileWritten] = utiljvm.copyFilebyStrs(fromPath.asStr, toPath.asStr)

/** Copies file from the full path-name of the first parameter to the dir of the second parameter. */
def copyFileTo(fromPath: FilePathAbs, toPath: DirPath): IOExcEither[FileWritten] = utiljvm.copyFilebyStrs(fromPath.asStr, toPath.asStr / fromPath.fileNameStr)

/** File copy that adds the ".js" [[String]] to the file source and file destination. */
def jsFileCopy(fromStem: FileStemPathAbs, toStem: FileStemPathAbs): IOExcEither[JsWritten] =
{ val toFilePath = toStem ++ ".js"
  copyFile(fromStem ++ ".js", toFilePath).map(_ => new JsWritten(toFilePath.arrayUnsafe))
}

/** File copy that adds the ".js.map" [[String]] to the file source and file destinations. */
def jsMapFileCopy(fromStem: FileStemPathAbs, toStem: FileStemPathAbs): IOExcEither[JsWritten] =
{ val toFilePath: FilePathAbs = toStem ++ ".js.map"
  copyFile(fromStem ++ ".js.map", toFilePath).map(_ => new JsWritten(toFilePath.arrayUnsafe))
}

/** File copy that adds the ".js" and ".js.map" [[String]]s to the file sources and file destinations. */
def jsWithMapFileCopy(fromStemPath: FileStemPathAbs, toStemPath: FileStemPathAbs): FileWrittenResults[IOExc, JsOrMapWritten] =
{ val toFilePath: FilePathAbs = toStemPath ++ ".js"
  val res1: IOExcEither[JsWritten] = utiljvm.copyFilebyStrs(fromStemPath.asStr + ".js", toFilePath.asStr).map(_ => new JsWritten(toFilePath.arrayUnsafe))
  res1 match
  {  case Right(jsfw) =>
    { val e2 = {
      val toPath: FilePathAbs = toStemPath ++ "js.map"
      utiljvm.copyFilebyStrs(fromStemPath.asStr + ".js.map", toPath.asStr).map(_ => new JsMapWritten(toPath.arrayUnsafe))
    }
      jsfw.withMap(e2)
    }
    case Left(err) => FileWrittenResults.errArray(Array(err), toStemPath.dirPath)
  }
}

/** File copy that adds the ".js" and ".js.map" [[String]]s to the file sources and file destinations. */
def jsWithMapFileRenameCopy(fromPath: DirPathAbs, htmlDirPath: DirPathAbs, fileNameStem: String): FileWrittenResults[IOException, JsOrMapWritten] =
{ val jsFile1: String = io.Source.fromFile(fromPath.asStr / "main.js").mkString
  val jsFile2 = jsFile1.replace("sourceMappingURL=main.js.map", "sourceMappingURL=" + fileNameStem + "js.map")    
  val jsMapFile1: String = io.Source.fromFile(fromPath.asStr / "main.js.map").mkString
  val jsMapFile2: String = jsMapFile1.replace("""file\":\"main.js""", """file\":\"""" + fileNameStem + "js")    
  val destDir: DirPathAbs = htmlDirPath / fileNameStem
  destDir.mkExist
  val destPath: FilePathAbs = destDir :/ (fileNameStem + ".js")
  val res1: IOExcEither[JsWritten] = utiljvm.pathStrWriteStr(destDir.asStr / fileNameStem + ".js", jsFile2).map(_ => new JsWritten(destPath.arrayUnsafe))
  res1 match {
    case Right(jsfw) =>
    { val destFilePath = destDir :/ (fileNameStem + ".js.map")
      val res2 = utiljvm.pathStrWriteStr(destFilePath.asStr, jsMapFile2).map(_ => new JsMapWritten(destFilePath.arrayUnsafe))
      jsfw.withMap(res2)      
    }
    case Left(err) => FileWrittenResults.errs(fromPath, err)
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

/** File copy that adds the ".js" [[String]] to the file source and file destination. */
def jarFileCopy(fromStem: FileStemPathAbs, toStem: FileStemPathAbs): IOExcEither[JarWritten] =
{ val filePath: FilePathAbs = toStem ++ ".jar"
  copyFile(fromStem ++ ".jar", filePath).map(_ => new JarWritten(filePath.arrayUnsafe))
}
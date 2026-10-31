/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pDoc.CssMain, pweb.*, utiljvm.*, pDoc.*

object MillDocsStage extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")

    val stagePathBi: ExcEither[DirExists] = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi: ExcEither[DirsAbs] = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    ossDirBi.foreach{ path =>
      val cssFiles = path.writesCss(CssMain, OnlyCss)
      deb(cssFiles.reportStr)    
    }
    ossDirBi.foreach{dir => stageDocDir(dir) }
    val docBi: ExcEither[DirsAbs] = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = Either.flatMap2(projPathFind, docBi) { (projPath, docDir) => stageDocumentationJs(projPath, docDir) }
    deb(res.reportStr)
  }
}
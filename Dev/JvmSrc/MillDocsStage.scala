/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*

object MillDocsStage extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    
    val stagePathBi: Either[Throwable, DirsAbs] = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi: Either[Throwable, DirsAbs] = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    ossDirBi.foreach{dir => stageDocDir(dir) }
    val docBi: Either[Throwable, DirsAbs] = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = Either.map2Acc(projPathFind, docBi) { (projPath, docDir) => stageDocumentationJs(projPath, docDir) }
    deb(res.errsSummary)
  }
}
/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*

object MillDocsStage extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    
    val stagePathBi: ErrBi[Throwable, DirsAbs] = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi: ErrBi[Throwable, DirsAbs] = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    ossDirBi.forSucc{dir => stageDocDir(dir) }
    val docBi: ErrBi[Throwable, DirsAbs] = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = ErrBi.map2Acc(projPathFind, docBi) { (projPath, docDir) => stageDocumentationJs(projPath, docDir) }
    deb(res.errsSummary)
  }
}
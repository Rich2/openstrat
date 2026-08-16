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
    val docBi = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = ErrBi.flatMap2(projPathFind, docBi) { (projPath, docDir) =>
      jsWithMapFileRenameCopy(projPath.millFullLinkDir("TomcatPageJs"), docDir, "tomcat")
      jsWithMapFileRenameCopy(projPath.millFullLinkDir("NewDevsPageJs"), docDir, "newdevs")
      jsWithMapFileRenameCopy(projPath.millFullLinkDir("PostgresPageJs"), docDir, "postgres")
    }
    deb(res.reportStr)
  }
}
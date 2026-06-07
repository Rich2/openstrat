/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.webjvm.*

object MillDocsStage
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    val stagePathBi = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    val docBi = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = ErrBi.flatMap2(projPathFind, docBi) { (projPath, docDir) =>
      jsWithMapFileCopy(projPath.outFullLink("TomcatPageJs"), docDir :-/ "tomcat")
      jsWithMapFileCopy(projPath.outFullLink("NewDevsPageJs"), docDir :-/ "newdevs")
    }
    deb(res.reportStr)
  }
}
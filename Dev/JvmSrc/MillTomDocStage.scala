/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillTomDocStage
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    val stagePathBi = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    val docBi = ossDirBi.flatMap(_.mkSubExist("Documentation"))
    val res = ErrBi.flatMap2(projPathFind, docBi) { (projPath, docDir) => jsWithMapFileCopy(projPath.outFullLink("DevDocJs"), docDir :-/ "tomcat") }
    deb(res.reportStr)
  }
}
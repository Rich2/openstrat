/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillTomDocStage
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    val res = projPathFind.flatMap { projPath =>
      stagingPathFind.flatMap { stagingPath1 =>
        stagingPath1.mkExist.flatMap { sp1 =>
          sp1.mkSubExist("OpenstratSite").flatMap { ossDir =>
            ossDir.mkSubExist("Documentation").flatMap { docDir =>
              jsWithMapFileCopy(projPath.outFullLink("DevDocJs"), docDir :-/ "tomcat")
            }
          }
        }
      }
    }
    debvar(res)
  }
}
/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object StagingMill
{
  def main(args: Array[String]): Unit =
  {
    deb("Starting StagingMill")
    stagingPathDo { path =>
      debvar(path)
      val res1 = fileCopy("/openstrat/out/AppJs/Diceless/fullLinkJS.dest/main.js", "/CommonSsd/Staging/earthgames/dicelessapp.js")
      debvar(res1)
    }
  }
}
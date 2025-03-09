/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm._

/** Application for producing Openstrat POM files. Takes the target folder as a paremter. */
object PomsApp
{
  def main(args: Array[String]): Unit =
  { val versionStr = "0.3.6snap"
    val oDir = args.headOption
    debvar(oDir)

    def stagePom(dirStr: String, name: String, versionStr: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
      pomFileWrite(dirStr / name + "-" + versionStr, OpenStratPomProject(name, versionStr, depStrs.toArr).out())

    oDir.foreach { dirStr =>
      val res: ErrBiAcc[Exception, PomFileWritten] = ErrBiAcc(
        stagePom(dirStr, "rutil", versionStr),
        stagePom(dirStr, "geom", versionStr, "rutil"),
        stagePom(dirStr, "tiling", versionStr, "rutil", "geom"),
        stagePom(dirStr, "egrid", versionStr, "rutil", "geom", "tiling"),
      )
     deb(res.msgErrsSummary(s"to $dirStr"))
    }
  }
}
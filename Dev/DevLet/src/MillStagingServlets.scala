/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*, wcode.*

/** Programme to create the folders for a Servlet and to copy the openstrat jars from the Mill out folder to Staging. Designed to be called from Mill when the
 * module jars have already been built. */
object MillStageServlets
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Staging Folder exists.")
        //       MillStageMainJars(stagingPath)
        val classesStage: DirsAbs = stagingPath / "ServletClasses"
        classesStage.mkExist
        projPathDo{ projPath =>
          val res1: ErrBiAcc[Exception, FileWritten] = MillJars.action(projPath, stagingPath)
          deb(res1.summaryLine)
          val classesPath = projPath / "out/DevLet/compile.dest/classes/ostrat/pDev"
          copyFileTo(classesPath :/ "GeomUser.class", classesStage)
          copyFileTo(classesPath :/ "HelloLet.class", classesStage)
          copyFileTo(classesPath :/ "LoginLet.class", classesStage)
        }
      }
    }    
  }
}
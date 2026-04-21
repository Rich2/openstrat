/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*

/** Programme to create the folders for a Servlet and to copy the openstrat jars from the Mill out folder to Staging. Designed to be called from Mill when the
 * module jars have already been built. */
object MillStagingServlet
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Staging Folder exists.")
//        MillStageMainJars(stagingPath)
        val cookPath = stagingPath / "Cookies1"
        cookPath.mkExist.forSucc { res1 => webInf(cookPath) }
      }
    }
    
    def webInf(path: DirsAbs): Unit =
    { val webInfPath = path / "WEB-INF"
      webInfPath.mkExist.forSucc { res1 => writeFile(webInfPath :/ "web.xml", WebXmlCookies1.out) }
      val libPath: DirsAbs = webInfPath / "lib"
      libPath.mkExist
      val classesPath: DirsAbs = webInfPath / "classes"
      classesPath.mkExist
    }
  }
}
/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillStagingServlet
{
  val versionStr: String = "0.3.5snap"
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Staging Folder exists.")
        commonLibs(stagingPath.asStr)
        val cookPath: String = stagingPath /% "Cookies1"
        mkDirExist(cookPath).forSucc { res1 => webInf(cookPath) }
      }
    }
    
    def webInf(path:String): Unit =
    { val webInfPath = path / "WEB-INF"
      mkDirExist(webInfPath).forSucc { res1 => fileWrite(webInfPath / "web.xml", WebXmlCookies1.out()) }
      val libPath = webInfPath / "lib"
      mkDirExist(libPath)
      val classesPath = webInfPath / "classes"
      mkDirExist(classesPath)
    }

    def commonLibs(stagingPath: String): Unit =
    {
      val commonPath: String = stagingPath / "libCommon"
      mkDirExist(commonPath).forSucc { res1 =>
        projPathDo { projPath =>
          val f1 = fileCopy(projPath.asStr / "out/Util/jar.dest/out.jar", commonPath / "rutil-" + versionStr + ".jar")
          debvar(f1)
        }
      }
    }
  }
}
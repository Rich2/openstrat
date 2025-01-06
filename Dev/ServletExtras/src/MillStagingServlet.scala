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
      val sharedPath: String = stagingPath / "libShared"
      mkDirExist(sharedPath).forSucc { res1 =>
        projPathDo { projPath =>
          def fc(srcStr: String, destStr: String): ErrBi[Exception, FileCopied] =
            fileCopy(projPath.asStr / "out" / srcStr / "jar.dest/out.jar", sharedPath / destStr + "-" + versionStr + ".jar")
          val f1 = ErrBiAcc(fc("Util", "rutil"), fc("Geom", "geom"), fc("Tiling", "tiling"), fc("EGrid", "egrid"))
          debvar(f1)
        }
      }
    }
  }
}
/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object StagingServlet
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Staging Folder exists.")
        val cookPath = stagingPath /% "Cookies1"
        mkDirExist(cookPath).forSucc{ res => fileWrite(cookPath / "web.xml", WebXmlCookies1.out()) }
      }
    }
  }
}
/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import pweb.*

trait ServletUtil
{
  def name: String
  def procArgs(args: Array[String]): Unit =
  {
    args.length match
    { case 0 => deb("No args")      
      case _ => dirs(DirPathAbs(args(0)))
    }
  }

  def dirs(dirPath: DirPathAbs): Unit =
  {
    dirPath.doIfDirExists{path =>
      val topPath = dirPath / name
      topPath.mkExist
      val wiPath = topPath / "WEB-INF"
      wiPath.mkExist
      val classesPath = wiPath / "classes"
      classesPath.mkExist
      val libPath = wiPath / "lib"
      libPath.mkExist
    }
    deb("directory path =" -- dirPath.asStr)
  }
}
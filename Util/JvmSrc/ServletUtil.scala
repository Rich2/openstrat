/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import java.io.*

trait ServletUtil
{
  def name: String
  def procArgs(args: Array[String]): Unit =
  {
    args.length match
    { case 0 => deb("No args")      
      case _ => dirs(DirsAbs(args(0)))
    }
  }

  def dirs(dirPath: DirsAbs): Unit =
  {
    dirPath.doIfDirExists{path =>
      val topPath = dirPath / name
      mkDirExist(topPath)
      val wiPath = topPath / "WEB-INF"
      mkDirExist(wiPath)
    }
    deb("directory path =" -- dirPath.asStr)
  }
}
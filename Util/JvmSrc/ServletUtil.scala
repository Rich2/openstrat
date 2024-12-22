/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import java.io.*

object ServletUtil
{
  def main(args: Array[String]): Unit =
  {
    args.length match
    { case 0 => deb("No args")
      case 1 => deb("2nd argument servlet name missing.")
      case _ => dirs(DirsAbs(args(0)), args(1))
    }
  }

  def dirs(dirPath: DirsAbs, name: String): Unit =
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
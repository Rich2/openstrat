/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm

object ServletUtil
{
  def main(args: Array[String]): Unit =
  { args.headOption.foreach(dirs)
  }
  def dirs(dirPath: String): Unit =
  {
    deb("directory path =" -- dirPath)
  }
}
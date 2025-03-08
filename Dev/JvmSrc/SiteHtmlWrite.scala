/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

/** Object for creating openstrat documentation. */
object SiteHtmlWrite extends StagingBuild
{
  def main(args: Array[String]): Unit = stagingPathDo { path =>
    debvar(path)
    stageBase(path)
  }
}
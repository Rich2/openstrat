/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

/** Object for creating openstrat documentation. */
object SiteHtmlWrite extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { deb("Starting SiteHtmlWrite")

    stagingPathDo { path =>
      debvar(path)
      fileWrite(path, "index.html", IndexPage.out)
      AppPage.all.foreach(page => fileWrite(path / page.dirStr, page.htmlFileName, page.out))
      stageDocs(path)
    }
  }

}
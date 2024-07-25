/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevHtmls extends App
{
  deb("Starting DevHtmls")
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    deb(path.str)
  }{
    strArr => deb(strArr.mkStr(","))
  }
}

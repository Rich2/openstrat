/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZero
import prid.phex._
/** Scrap pad console app for the Tiling module. */
object TilingTryApp  extends App
{
  deb("Hello TilingTryApp")
  val scs = DblArr(1300, 1000, 640, 460, 320, 220, 160, 120, 80)
  val res = scs.map(d => (d, tileScaleToArea(d)))
  res.foreach{ p =>
    val str = p._1.str.appendCommas(p._2.str)
    println(str)
  }
}
/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZero
import prid.phex._
/** Scrap pad console app for the Tiling module. */
object TilingTryApp  extends App
{
  deb("Hello TilingTryApp")
  val scs = DblArr(1300, 1000, 640, 460, 320, 220, 160, 120, 80)

  val isl = DblArr(16, 13, 10, 9, 8, 7, 6, 5.5, 5, 4.5, 4, 3.5, 3, 2.5, 2, 1.5, 1)
  val sc: Int = 460
  val a460 = tileScaleToArea(sc)
  println(sc + "km")
  val isl1 = isl.map{ i =>(i,  i.squared * a460 / 256) }.map (p => p._1 + ", " + p._2)
  isl1.foreach(println)
}
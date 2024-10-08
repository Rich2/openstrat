/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZero
import prid.phex._

/** Scrap pad console app for the Tiling module. */
object TilingTryApp
{
  @main def run(inp: Int): Unit =
  {
    deb("Hello TilingTryApp")
    val scs = DblArr(1300, 1000, 640, 460, 320, 220, 160, 120, 80)
    val sc: Double = scs.find(_ == inp).getOrElse(1000)
    val isl = DblArr(16, 13.5, 12.5, 11.5, 10.5, 9.5, 8.5, 7.5, 6.5, 5.5, 4.5, 3.5, 2.5)

    val a460 = tileScaleToArea(sc)
    println("Tile areas for different island sizes, for given tile radius of " + sc.str + "km")
    val isl1 = isl.map{ i => (i, i.squared * a460 / 256) }.map(p => p._1.str + ", " + p._2 + "km²")
    isl1.foreach(println)
  }
}
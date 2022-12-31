/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._

/** 20North, 0 East */
object EuropeNW extends EArea1("EuropeNW", 20 ll 0)
{
  override val a2Arr: RArr[EArea2] = RArr(Ireland, England, Scotland, OuterHebrides, Shetland, Faroe, JanMayen, Frankia, BelgLux, Alsace, Netherlands, Jutland, Zealand, Funen,
    Germania, Alpsland, Polandia, Baltland, Ukraine, SwedenSouth, SwedenNorth)
}

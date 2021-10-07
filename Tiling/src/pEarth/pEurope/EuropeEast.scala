/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._

object EuropeEast extends EarthLevel1("EuropeEast", 60 ll 60)
{ override val a2Arr: Arr[EarthLevel2] = Arr(Balkans, Finlandia, Gotland, Saaremaa, Hiiumaa, Crimea, Anatolia, Caucasus)
}
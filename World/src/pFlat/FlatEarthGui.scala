/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pEarth
package pFlat
import geom._, pCanv._

case class FlatEarthGui(canv: CanvasPlatform) extends MapGui("Flat Earth")
{ 
  override def mapObjs: Arr[GraphicElem] = Arr()
  override def eTop(): Unit = reTop(Arr(status))
  eTop()
}
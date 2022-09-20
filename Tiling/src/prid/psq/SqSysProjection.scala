/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

trait SqSysProjection extends TSysProjection
{ type GridT <: SqGridSys
}

case class SqSysProjectionFlat(gridSys: SqGridSys, panel: Panel) extends SqSysProjection
{
  type GridT = SqGridSys

  override def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems = ???

  override val buttons: Arr[PolygonCompound] = Arr()

  override def tiles: PolygonArr = PolygonArr()

  override def tileActives: Arr[PolygonActive] = Arr()
}

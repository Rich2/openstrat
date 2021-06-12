/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class ArrLikeBaseExtensions[A](val al : ArrayLikeBase[A])
{
  def mapPolygonLL(f: A => LatLong): PolygonLL = {
    PolygonLL.uninitialised(al.elemsLen)
    ???
  }
}

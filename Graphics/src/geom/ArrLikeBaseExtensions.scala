/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class ArrLikeBaseExtensions[A](val al : ArrayLikeBase[A])
{ /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygon(f: A => Pt2): PolygonImp = PolygonImp.fromArrMap(al)(f)

  /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonLL(f: A => LatLong): PolygonLL = PolygonLL.fromArrMap(al)(f)

  /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonMs3(f: A => Metres3): PolygonMs3 = PolygonMs3.fromArrMap(al)(f)

}
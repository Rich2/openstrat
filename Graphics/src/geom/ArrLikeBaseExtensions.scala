/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class ArrLikeBaseExtensions[A](val al : ArrayLikeBase[A])
{ /** Map this collection elements to [[Pt2]]s building a [[PolygonImp]]. */
  def mapPolygon(f: A => Pt2): PolygonImp = PolygonImp.fromArrMap(al)(f)

  /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonLL(f: A => LatLong): PolygonLL = PolygonLL.fromArrMap(al)(f)

  /** Map this collection elements to [[PtMs3]]s building a [[PolygonMs3]]. */
  def mapPolygonMs3(f: A => PtMs3): PolygonMs3 = PolygonMs3.fromArrMap(al)(f)

  /** Map this collection elements to [[Metres2]]s building a [[PolygonM2]]. */
  def mapPolygonMs(f: A => Metres2): PolygonMs = PolygonMs.fromArrMap(al)(f)
}
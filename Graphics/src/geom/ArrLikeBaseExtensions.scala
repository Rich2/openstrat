/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class ArrLikeBaseExtensions[A](val al : ArrayLikeBase[A])
{ /** Map this collection elements to [[Pt2]]s building a [[PolygonImp]]. */
  def mapPolygon(f: A => Pt2): PolygonImp = PolygonImp.fromArrMap(al)(f)

  /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonLL(f: A => LatLong): PolygonLL = PolygonLL.fromArrMap(al)(f)

  /** Map this collection elements to [[Pt3M]]s building a [[PolygonM3]]. */
  def mapPolygonM3(f: A => Pt3M): PolygonM3 = PolygonM3.fromArrMap(al)(f)

  /** Map this collection elements to [[Pt2M]]s building a [[PolygonM2]]. */
  def mapPolygonM(f: A => Pt2M): PolygonM = PolygonM.fromArrMap(al)(f)

  /** Map this collection elements to [[Pt2M]]s building a [[Pt2MArr]]. */
  def mapPt2MArr(f: A => Pt2M): Pt2MArr = Pt2MArr.fromArrMap(al)(f)
}
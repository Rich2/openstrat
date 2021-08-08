/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class DataGenExtensions[A](val al : DataGen[A])
{ /** Map this collection elements to [[Pt2]]s building a [[PolygonGen]]. */
  def mapPolygon(f: A => Pt2): PolygonGen = PolygonGen.fromDataGenMap(al)(f)

  /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonLL(f: A => LatLong): PolygonLL = PolygonLL.fromDataGenMap(al)(f)

  /** Map this collection elements to [[PtMetre3]]s building a [[PolygonMetre3]]. */
  def mapPolygonMetre3(f: A => PtMetre3): PolygonMetre3 = PolygonMetre3.fromDataGenMap(al)(f)

  /** Map this collection elements to [[PtMetre2]]s building a [[PolygonM2]]. */
  def mapPolygonMetre(f: A => PtMetre2): PolygonMetre = PolygonMetre.fromDataGenMap(al)(f)

  /** Map this collection elements to [[PtMetre2]]s building a [[Pt2MArr]]. */
  def mapPt2MetreArr(f: A => PtMetre2): Pt2MArr = Pt2MArr.fromDataGenMap(al)(f)
}
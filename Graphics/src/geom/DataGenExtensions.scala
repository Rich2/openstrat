/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class DataGenExtensions[A](val al : DataGen[A])
{
  /** Map this collection elements to [[PtMetre2]]s building a [[PolygonM2]]. */
  def mapPolygonMetre(f: A => PtMetre2): PolygonMetre = PolygonMetre.fromDataGenMap(al)(f)

  /** Map this collection elements to [[PtMetre2]]s building a [[Pt2MArr]]. */
  def mapPt2MetreArr(f: A => PtMetre2): Pt2MArr = Pt2MArr.fromDataGenMap(al)(f)

  /** Map this collection of data elements to PolygonLike class of type BB. */
  def mapPolygon[B <: ElemValueN, BB <: PolygonLike[B]](f: A => B)(implicit build: PolygonBuilder[B, BB]): BB =
  {
    val res = build.newPolygonT(al.elemsNum)
    al.dataIForeach((a, i) => build.arrSet(res, i, f(a)))
    res
  }
}
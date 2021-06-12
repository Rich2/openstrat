/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class ArrLikeBaseExtensions[A](val al : ArrayLikeBase[A])
{ /** Map this collection elements to [[LatLong]]s building a [[PolygonLL]]. */
  def mapPolygonLL(f: A => LatLong): PolygonLL =
  { val res = PolygonLL.uninitialised(al.elemsLen)
    al.iForeach{(a, i) => res.unsafeSetElem(i, f(a)) }
    res
  }
}
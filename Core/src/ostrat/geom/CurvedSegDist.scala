/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class CurveSegDists(val arr: Array[Double]) extends AnyVal with DoubleProduct6s[CurveSegDist]
{
   override def typeName: Symbol = 'CurvedSegDists
   override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): CurveSegDist = new CurveSegDist(d1, d2, d3, d4, d5, d6)
}

object CurveSegDists extends Double6sMaker[CurveSegDist, CurveSegDists]
{
   implicit val factory: Int => CurveSegDists = i => new CurveSegDists(new Array[Double](i * 6))
}
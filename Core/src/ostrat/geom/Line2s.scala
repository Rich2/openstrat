/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class Line2s(val arr: Array[Double]) extends AnyVal with DoubleProduct4s[Line2] with Transable[Line2s]
{
   override def typeName: Symbol = 'Line2s
   override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): Line2 = new Line2(d1, d2, d3, d4)
   override def fTrans(f: Vec2 => Vec2): Line2s = pMap(orig => Line2(f(orig.pStart), f(orig.pEnd)))
   def ptInPolygon(pt: Vec2): Boolean =
   {
      val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
      num.isOdd      
   }
   def ++ (operand: Line2s): Line2s =
   {
      val res = Line2s(length + operand.length)
      this.iForeach((elem, i) => res.setElem(i, elem))
      operand.iForeach((elem, i) => res.setElem(i + length, elem))
      res
   }
}

object Line2s extends Double4sMaker[Line2, Line2s]
{
   implicit val factory: Int => Line2s = i => new Line2s(new Array[Double](i * 4))
}

/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Cood represents an integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while triangles is the
  * third possible regular tile system. A SqCood represents either a tile centre, a tile side or a tile vertex. This system allows river and naval
  * units to move along the tile sides. */
final case class Cood(val c: Int, val y: Int) extends ProdInt2
{ //def typeSym = 'Cood
  def _1 = c
  def _2 = y
  def xyStr: String = c.toString + ", " + y.toString
  def yxStr: String = y.toString + ", " + c.toString
  def fXY[A](f: (Int, Int) => A): A = f(c, y)
  def canEqual(a: Any) = a.isInstanceOf[Cood]
  def eqXY(x: Int, y: Int): Boolean = this == Cood(x, y)
  def +(operand: Cood): Cood = Cood(c + operand.c, y + operand.y)
  def -(operand: Cood): Cood = Cood(c - operand.c, y - operand.y)
  def *(operand: Int): Cood = Cood(c * operand, y * operand)
  def /(operand: Int): Cood = Cood(c / operand, y / operand)

  def addXY(xOff: Int, yOff: Int): Cood = Cood(c + xOff, y + yOff)
  def subXY(xOff: Int, yOff: Int): Cood = Cood(c - xOff, y - yOff)
  def addX(operand: Int): Cood = Cood(c + operand, y)
  def addY(operand: Int): Cood = Cood(c, y + operand)
  def subX(operand: Int): Cood = Cood(c - operand, y)
  def subY(operand: Int): Cood = Cood(c, y - operand)
  def toSqVec2: Vec2 = Vec2(c, y)
  /** x.isOdd & y.isOdd | x.isEven & y.isEven */
  def evenSum: Boolean = c.isOdd & y.isOdd | c.isEven & y.isEven
  /** x.isOdd & y.isEven | x.isEven & y.isOdd */
  def oddSum: Boolean = c.isOdd & y.isEven | c.isEven & y.isOdd
}

object Cood
{ def apply(x: Int, y: Int): Cood = new Cood(x, y)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.c, tc.y))
  def list(inp: (Int, Int)*): List[Cood] = inp.toList.map(p => Cood(p._1, p._2))
  implicit object CoodPersist extends PersistInt2[Cood]("Cood", "x", _.c, "y", _.y, apply)

  implicit val cood2sBuildImplicit: ArrBuild[Cood, Coods] = new ArrProdInt2sBuild[Cood, Coods]
  { type BuffT = CoodsBuff
    override def fromIntArray(array: Array[Int]): Coods = new Coods(array)
    override def fromIntBuffer(inp: Buff[Int]): CoodsBuff = new CoodsBuff(inp)
  }
}

/** An array[Int] based collection for Cood. */
class Coods(val array: Array[Int]) extends AnyVal with ArrProdInt2[Cood]
{ type ThisT = Coods
  override def unsafeFromArray(array: Array[Int]): Coods = new Coods(array)
  override def typeStr: String = "Coods"
  override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)

  def filter(f: Cood => Boolean): Coods =
  { val tempArr = new Array[Int](array.length)
    var count = 0
    var lengthCounter = 0
    while (count < length)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = array(count * 2)
        tempArr(lengthCounter * 2 + 1) = array(count * 2 + 1)
        lengthCounter += 1
      }
      count += 1
    }
    val finalArr = new Array[Int](lengthCounter * 2)
    count = 0
    while (count < lengthCounter * 2){ finalArr(count) = tempArr(count); count += 1 }
    new Coods(finalArr)
  }
  /*def flatMapNoDuplicates(f: Cood => Coods): Coods =
  {
    val buff = new CoodsBuff()
    foreach{ el =>
      val newVals = f(el)
      //newVals.foreach{ n => if(buff.co)}
    }
    new Coods(buff.toArray)
  }*/
}

class CoodsBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with BuffProdInt2[Cood, Coods]
{ type ArrT = Coods
}

object Coods extends ProductI2sCompanion[Cood, Coods]
{
  override def buff(initialSize: Int): CoodsBuff = new CoodsBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Coods = new Coods(array)

  implicit object PersistImplicit extends ProdInt2sBuilder[Cood, Coods]("Coods")
  {
    override def fromArray(value: Array[Int]): Coods = new Coods(value)
  }
}





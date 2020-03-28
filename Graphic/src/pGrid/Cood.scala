/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Cood represents a 2 dimensional integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while
 *  triangles is the third possible regular tile system. A SqGrid Cood represents either a tile centre, a tile side or a tile vertex. This is the same
 *  for a Hex Grid except that not all values are legal Cood values on a HexGrid. This system allows river and naval units to move along the tile
 *  sides. The axis are named xi and yi to distinguish them from the x and y of a Vec2. On a Hex grid there is not a simple 1 to 1 mapping between the
 *  Cood components and the Vec2 components. */
final class Cood(val xi: Int, val yi: Int) extends ProdInt2
{ //def typeSym = 'Cood
  def _1 = xi
  def _2 = yi
  def xyStr: String = xi.toString + ", " + yi.toString
  def yxStr: String = yi.toString + ", " + xi.toString
  def fXY[A](f: (Int, Int) => A): A = f(xi, yi)

  override def equals(that: Any): Boolean = that match
  { case op: Cood if xi == op.xi & yi == op.yi => true
    case _ => false
  }
  
  def canEqual(a: Any) = a.isInstanceOf[Cood]
  def eqXY(x: Int, y: Int): Boolean = this == Cood(x, y)
  def +(operand: Cood): Cood = Cood(xi + operand.xi, yi + operand.yi)
  def -(operand: Cood): Cood = Cood(xi - operand.xi, yi - operand.yi)
  def *(operand: Int): Cood = Cood(xi * operand, yi * operand)
  def /(operand: Int): Cood = Cood(xi / operand, yi / operand)

  def addXY(xOff: Int, yOff: Int): Cood = Cood(xi + xOff, yi + yOff)
  def subXY(xOff: Int, yOff: Int): Cood = Cood(xi - xOff, yi - yOff)
  def addX(operand: Int): Cood = Cood(xi + operand, yi)
  def addY(operand: Int): Cood = Cood(xi, yi + operand)
  def subX(operand: Int): Cood = Cood(xi - operand, yi)
  def subY(operand: Int): Cood = Cood(xi, yi - operand)
  def toSqVec2: Vec2 = Vec2(xi, yi)
  /** x.isOdd & y.isOdd | x.isEven & y.isEven */
  def evenSum: Boolean = xi.isOdd & yi.isOdd | xi.isEven & yi.isEven
  /** x.isOdd & y.isEven | x.isEven & y.isOdd */
  def oddSum: Boolean = xi.isOdd & yi.isEven | xi.isEven & yi.isOdd
}

object CoodNew
{ def apply(yi: Int, xi: Int): Cood = new Cood(yi, xi)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.xi, tc.yi))
}

object Cood
{ def apply(x: Int, y: Int): Cood = new Cood(x, y)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.xi, tc.yi))
  def list(inp: (Int, Int)*): List[Cood] = inp.toList.map(p => Cood(p._1, p._2))
  implicit object CoodPersist extends PersistInt2[Cood]("Cood", "x", _.xi, "y", _.yi, apply)

  implicit val cood2sBuildImplicit = new ArrProdInt2Build[Cood, Coods]
  { type BuffT = CoodBuff
    override def fromIntArray(array: Array[Int]): Coods = new Coods(array)
    override def fromIntBuffer(inp: Buff[Int]): CoodBuff = new CoodBuff(inp)
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

  def flatMapNoDuplicates(f: Cood => Coods): Coods =
  {
    val buff = new CoodBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new Coods(buff.toArray)
  }
}

object Coods extends ProductI2sCompanion[Cood, Coods]
{
  override def buff(initialSize: Int): CoodBuff = new CoodBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Coods = new Coods(array)

  implicit object PersistImplicit extends ProdInt2sBuilder[Cood, Coods]("Coods")
  { override def fromArray(value: Array[Int]): Coods = new Coods(value)
  }

  implicit val arrArrayImplicit: ArrFlatBuild[Coods] = Cood.cood2sBuildImplicit
}

class CoodBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with BuffProdInt2[Cood, Coods]
{ type ArrT = Coods
  override def intsToT(i1: Int, i2: Int): Cood = Cood(i1, i2)
}
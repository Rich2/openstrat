/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

class HVert private(val bLong: Long) extends AnyVal with HCoord with ProdInt2
{
  @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  override def _1: Int = r
  override def _2: Int = c
  override def typeStr: String = "HVert"
  override def canEqual(that: Any): Boolean = ???

  override def toVec: Vec2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Vec2(c / Sqrt3, r + 1.0 / 3)
    case _ => Vec2(c / Sqrt3, r - 1.0 / 3)
  }

  override def toPt2: Pt2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Pt2(c / Sqrt3, r + 1.0 / 3)
    case _ => Pt2(c / Sqrt3, r - 1.0 / 3)
  }

  def + (hCen: HCen): HVert = HVert(r + hCen.r, c + hCen.c)
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven)
    new HVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

  implicit object persistImplicit extends PersistInt2[HVert]("Rood", "r", _.r, "c", _.c, apply)

  implicit val HVertsBuildImplicit = new ArrProdInt2Build[HVert, HVerts]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVerts = new HVerts(array)
    override def fromIntBuffer(inp: Buff[Int]): HVertBuff = new HVertBuff(inp)
  }
}

/** An array[Int] based collection for HVert. */
class HVerts(val arrayUnsafe: Array[Int]) extends AnyVal with ArrProdInt2[HVert]
{ type ThisT = HVerts
  override def fElemStr: HVert => String = _.str
  override def unsafeFromArray(array: Array[Int]): HVerts = new HVerts(array)
  override def typeStr: String = "HVerts" + foldLeft("")(_ + "; " + _.rcStr)
  override def newElem(i1: Int, i2: Int): HVert = HVert.apply(i1, i2)

  /*def filter(f: HVert => Boolean): HVerts =
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
    new HVerts(finalArr)
  }

  def flatMapNoDuplicates(f: HVert => HVerts): HVerts =
  {
    val buff = new HVertBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new HVerts(buff.toArray)
  }*/
}

object HVerts extends ProductI2sCompanion[HVert, HVerts]
{
  override def buff(initialSize: Int): HVertBuff = new HVertBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HVerts = new HVerts(array)

  implicit object PersistImplicit extends ProdInt2sBuilder[HVert, HVerts]("HVerts")
  { override def fromArray(value: Array[Int]): HVerts = new HVerts(value)
  }

  implicit val arrArrayImplicit: ArrFlatBuild[HVerts] = HVert.HVertsBuildImplicit
}

class HVertBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with BuffProdInt2[HVert, HVerts]
{ type ArrT = HVerts
  override def intsToT(i1: Int, i2: Int): HVert = HVert(i1, i2)
}
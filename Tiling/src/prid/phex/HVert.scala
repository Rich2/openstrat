/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A hex tile vertex coordinate. */
class HVert private(val bLong: Long) extends AnyVal with HCoord with TCoord
{ @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  override def typeStr: String = "HVert"
  override def canEqual(that: Any): Boolean = ???

  override def toVecReg: Vec2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Vec2(c, r * Sqrt3 + 1.0/Sqrt3)
    case _ => Vec2(c , r * Sqrt3 - 1.0/Sqrt3)
  }

  override def toPt2Reg: Pt2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Pt2(c, r  * Sqrt3 + 1.0/Sqrt3)
    case _ => Pt2(c, r * Sqrt3 - 1.0/Sqrt3)
  }

  def + (hCen: HCen): HVert = HVert(r + hCen.r, c + hCen.c)

  def hexIsUp: Boolean = r %% 4 match
  { case 1 if c %% 4 == 0 => true
    case 3 if c %% 4 == 2 => true
    case _ => false
  }

  def adjHCens: HVDirnArr = ife(hexIsUp, HVDirnArr(HVUp, HVDR, HVDL), HVDirnArr(HVUR, HVDn, HVUL))
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven)
    new HVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

 // implicit object persistImplicit extends Persist2Ints[HVert]("Rood", "r", "c", apply)

  implicit val hVertsBuildImplicit: Int2ArrMapBuilder[HVert, HVertArr] = new Int2ArrMapBuilder[HVert, HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait HVertSeqLike extends Any with Int2SeqLike[HVert]
{ override def newElem(int1: Int, int2: Int): HVert = HVert.apply(int1, int2)
  override def fElemStr: HVert => String = _.str
  def vertNum: Int = unsafeArray.length / 2
}

/** An array[Int] based collection for HVert. */
class HVertArr(val unsafeArray: Array[Int]) extends AnyVal with HVertSeqLike with Int2Arr[HVert]
{ type ThisT = HVertArr
  override def fromArray(array: Array[Int]): HVertArr = new HVertArr(array)
  override def typeStr: String = "HVerts" + foldLeft("")(_ + "; " + _.rcStr)

  def toPolygon: PolygonHC = new PolygonHC(unsafeArray)
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

object HVertArr extends Int2SeqLikeCompanion[HVert, HVertArr]
{ def fromArray(array: Array[Int]): HVertArr = new HVertArr(array)

  implicit object PersistImplicit extends PersistArrInt2s[HVert, HVertArr]("HVerts")
  { override def fromArray(value: Array[Int]): HVertArr = new HVertArr(value)

    override def showDecT(obj: HVertArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[HVertArr] = new Int2ArrFlatBuilder[HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

class HVertBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[HVert]
{ type ArrT = HVertArr
  override def typeStr: String = "HVertBuff"
  override def newElem(i1: Int, i2: Int): HVert = HVert(i1, i2)
}
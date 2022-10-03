/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait SqVertSeqDef extends Any with Int2SeqDef[SqVert]
{ override def sdElem(int1: Int, int2: Int): SqVert = SqVert.apply(int1, int2)
  override def fElemStr: SqVert => String = _.str
  def vertNum: Int = unsafeArray.length / 2
}

/** An array[Int] based collection for SqVert. */
class SqVertArr(val unsafeArray: Array[Int]) extends AnyVal with SqVertSeqDef with Int2Arr[SqVert]
{ type ThisT = SqVertArr
  override def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
  override def typeStr: String = "SqVerts" + foldLeft("")(_ + "; " + _.rcStr)
  def toPolygon: PolygonSqC = new PolygonSqC(unsafeArray)
  //def toPolygon: PolygonSC = new PolygonSC(unsafeArray)
  /*def filter(f: SqVert => Boolean): SqVerts =
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
    new SqVerts(finalArr)
  }

  def flatMapNoDuplicates(f: SqVert => SqVerts): SqVerts =
  {
    val buff = new SqVertBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new SqVerts(buff.toArray)
  }*/
}

object SqVertArr extends Int2SeqDefCompanion[SqVert, SqVertArr]
{ def fromArray(array: Array[Int]): SqVertArr = new SqVertArr(array)

  implicit object PersistImplicit extends PersistArrInt2s[SqVert, SqVertArr]("SqVerts")
  { override def fromArray(value: Array[Int]): SqVertArr = new SqVertArr(value)

    override def showDecT(obj: SqVertArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[SqVertArr] = new Int2ArrFlatBuilder[SqVert, SqVertArr]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }
}

class SqVertBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[SqVert]
{ type ArrT = SqVertArr
  override def typeStr: String = "SqVertBuff"
  override def intsToT(i1: Int, i2: Int): SqVert = SqVert(i1, i2)
}
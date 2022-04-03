/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait SqVertsLike extends Any with Int2SeqDef[SqVert]
{ override def sdElem(int1: Int, int2: Int): SqVert = SqVert.apply(int1, int2)
  override def fElemStr: SqVert => String = _.str
  def vertNum: Int = unsafeArray.length / 2
}

/** An array[Int] based collection for SqVert. */
class SqVerts(val unsafeArray: Array[Int]) extends AnyVal with SqVertsLike with Int2Arr[SqVert]
{ type ThisT = SqVerts
  override def fromArray(array: Array[Int]): SqVerts = new SqVerts(array)
  override def typeStr: String = "SqVerts" + foldLeft("")(_ + "; " + _.rcStr)

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

object SqVerts extends Int2SeqDefCompanion[SqVert, SqVerts]
{ def fromArray(array: Array[Int]): SqVerts = new SqVerts(array)

  implicit object PersistImplicit extends PersistArrInt2s[SqVert, SqVerts]("SqVerts")
  { override def fromArray(value: Array[Int]): SqVerts = new SqVerts(value)

    override def showDecT(obj: SqVerts, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[SqVerts] = new Int2ArrFlatBuilder[SqVert, SqVerts]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVerts = new SqVerts(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }
}

class SqVertBuff(val unsafeBuffer: ArrayBuffer[Int] = buffInt()) extends AnyVal with Int2Buff[SqVert]
{ type ArrT = SqVerts
  override def typeStr: String = "SqVertBuff"
  override def intsToT(i1: Int, i2: Int): SqVert = SqVert(i1, i2)
}
/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Common trait for [[Hverts]] and [[PolygonHVert]] */
trait HVertsLike extends Any with Int2sSeq[HVert]
{ override def newElem(i1: Int, i2: Int): HVert = HVert.apply(i1, i2)
  override def fElemStr: HVert => String = _.str
  def vertNum: Int = arrayUnsafe.length / 2
}

/** An array[Int] based collection for HVert. */
class HVerts(val arrayUnsafe: Array[Int]) extends AnyVal with HVertsLike
{ type ThisT = HVerts
  override def unsafeFromArray(array: Array[Int]): HVerts = new HVerts(array)
  override def typeStr: String = "HVerts" + foldLeft("")(_ + "; " + _.rcStr)

  def toPolygon: PolygonHVert = new PolygonHVert(arrayUnsafe)
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

object HVerts extends Int2sArrCompanion[HVert, HVerts]
{
  //override def buff(initialSize: Int): HVertBuff = new HVertBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HVerts = new HVerts(array)

  implicit object PersistImplicit extends Int2sArrPersist[HVert, HVerts]("HVerts")
  { override def fromArray(value: Array[Int]): HVerts = new HVerts(value)

    override def showT(obj: HVerts, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: SeqFlatBuilder[HVerts] = new Int2sArrFlatBuilder[HVert, HVerts]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVerts = new HVerts(array)
    override def fromIntBuffer(inp: Buff[Int]): HVertBuff = new HVertBuff(inp)
  }
}

class HVertBuff(val unsafeBuff: Buff[Int] = buffInt()) extends AnyVal with Int2sBuffer[HVert, HVerts]
{ type ArrT = HVerts
  override def intsToT(i1: Int, i2: Int): HVert = HVert(i1, i2)
}
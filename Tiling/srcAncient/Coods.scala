/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid

/** An array[Int] based collection for Cood. To be replaced by [[prid.TileCoord]]. */
class Coods(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2s[Cood]
{ type ThisT = Coods
  override def fElemStr: Cood => String = _.str
  override def unsafeFromArray(array: Array[Int]): Coods = new Coods(array)
  override def typeStr: String = "Coods"
  override def dataElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)

  def filter(f: Cood => Boolean): Coods =
  { val tempArr = new Array[Int](unsafeArray.length)
    var count = 0
    var lengthCounter = 0
    while (count < dataLength)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = unsafeArray(count * 2)
        tempArr(lengthCounter * 2 + 1) = unsafeArray(count * 2 + 1)
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

object Coods extends DataInt2sCompanion[Cood, Coods]
{
  //override def buff(initialSize: Int): CoodBuff = new CoodBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Coods = new Coods(array)

  implicit object PersistImplicit extends PersistArrInt2s[Cood, Coods]("Coods")
  { override def fromArray(value: Array[Int]): Coods = new Coods(value)

    override def showDecT(obj: Coods, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[Coods] = new ArrInt2sFlatBuilder[Cood, Coods]
  { type BuffT = CoodBuff
    override def fromIntArray(array: Array[Int]): Coods = new Coods(array)
    override def fromIntBuffer(buffer: Buff[Int]): CoodBuff = new CoodBuff(buffer)
  }
}

class CoodBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with Int2Buff[Cood]
{ type ArrT = Coods
  override def typeStr: String = "CoodBuff"
  override def intsToT(i1: Int, i2: Int): Cood = Cood(i1, i2)
}
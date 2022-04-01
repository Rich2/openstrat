/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid

/** An array[Int] based collection for [[Roord]]. */
class Roords(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[Roord]
{ type ThisT = Roords
  override def fElemStr: Roord => String = _.toString
  override def fromArray(array: Array[Int]): Roords = new Roords(array)
  override def typeStr: String = "Roords" + foldLeft("")(_ + "; " + _.ycStr)
  override def sdElem(int1: Int, int2: Int): Roord = Roord.apply(int1, int2)

  /*def filter(f: Roord => Boolean): Roords =
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
    new Roords(finalArr)
  }

  def flatMapNoDuplicates(f: Roord => Roords): Roords =
  {
    val buff = new RoordBuff()
    foreach{ el =>
      val newVals = f(el)
      newVals.foreach{ newVal => if( ! buff.contains(newVal)) buff.grow(newVal) }
    }
    new Roords(buff.toArray)
  }*/
}

object Roords extends Int2SeqDefCompanion[Roord, Roords]
{
  //override def buff(initialSize: Int): RoordBuff = new RoordBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Roords = new Roords(array)

  implicit object PersistImplicit extends PersistArrInt2s[Roord, Roords]("Roords")
  { override def fromArray(value: Array[Int]): Roords = new Roords(value)

    override def showDecT(obj: Roords, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[Roords] = new Int2ArrFlatBuilder[Roord, Roords]
  { type BuffT = RoordBuff
    override def fromIntArray(array: Array[Int]): Roords = new Roords(array)
    override def fromIntBuffer(buffer: Buff[Int]): RoordBuff = new RoordBuff(buffer)
  }
}

class RoordBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with Int2Buff[Roord]
{ type ArrT = Roords
  override def typeStr: String = "RoordBuff"
  override def intsToT(i1: Int, i2: Int): Roord = Roord(i1, i2)
}
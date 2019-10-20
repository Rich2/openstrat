/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** An array[Int] based collection for Cood. */
class Coods(val array: Array[Int]) extends AnyVal with ProductI2s[Cood]
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
}

class CoodsBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with ProductI2sBuff[Cood, Coods]
{ override def unBuff: Coods = new Coods(toArray)
}

object Coods extends ProductI2sCompanion[Cood, Coods]
{
  override def buff(initialSize: Int): CoodsBuff = new CoodsBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): Coods = new Coods(array)

  implicit object PersistImplicit extends ProductI2sBuilder[Cood, Coods]("Coods")
  {
    override def fromArray(value: Array[Int]): Coods = new Coods(value)
  }
}
/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class Coods(val arr: Array[Int]) extends AnyVal with ProductInt2s[Cood]//(length) 
{ override def typeName: Symbol = 'Coods
  override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)
   
  def filter(f: Cood => Boolean): Coods =
  { val tempArr = new Array[Int](arr.length)
    var count = 0
    var lengthCounter = 0
    while (count < length)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = arr(count * 2)
        tempArr(lengthCounter * 2 + 1) = arr(count * 2 + 1)
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

object Coods extends Int2Companion[Cood, Coods]
{ 
  implicit val factory: Int => Coods = i => new Coods(new Array[Int](i * 2))
  
  implicit object CoodsPersistImplicit extends ProductInt2sBuilder[Cood, Coods]('Coods)
  {
    override def fromArray(value: Array[Int]): Coods = new Coods(value)
  }
}
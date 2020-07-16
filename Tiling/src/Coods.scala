package ostrat
package pGrid

/** An array[Int] based collection for Cood. */
class Coods(val arrayUnsafe: Array[Int]) extends AnyVal with ArrProdInt2[Cood]
{ type ThisT = Coods
  override def unsafeFromArray(array: Array[Int]): Coods = new Coods(array)
  override def typeStr: String = "Coods"
  override def newElem(i1: Int, i2: Int): Cood = Cood.apply(i1, i2)

  def filter(f: Cood => Boolean): Coods =
  { val tempArr = new Array[Int](arrayUnsafe.length)
    var count = 0
    var lengthCounter = 0
    while (count < length)
    {
      if (f(this.apply(count)))
      { tempArr(lengthCounter * 2) = arrayUnsafe(count * 2)
        tempArr(lengthCounter * 2 + 1) = arrayUnsafe(count * 2 + 1)
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

  implicit val arrArrayImplicit: ArrFlatBuild[Coods] = Cood.coodsBuildImplicit
}

class CoodBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with BuffProdInt2[Cood, Coods]
{ type ArrT = Coods
  override def intsToT(i1: Int, i2: Int): Cood = Cood(i1, i2)
}
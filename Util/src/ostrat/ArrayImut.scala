package ostrat

trait ArrImut[+A] extends Any with ArrayBased[A]
{
  def buildThis(length: Int): ThisT

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = buildThis(length - 1)
      iUntilForeach(0, n)(i => newArr.setUnsafe(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.setUnsafe(i - 1, apply(i)))
      newArr
    }
  }
}

trait BuffArr[A] extends Any with ArrayBased[A]


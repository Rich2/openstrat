package ostrat

trait BaseArr[A] extends Any
{ def length: Int
  def apply(index: Int): A

  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  def iForeach[U](f: (A, Int) => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count), count)
      count = count + 1
    }
  }

  def map[B, M <: ArrT[B]](f: A => B)(ev: ArrBuilder[B, M]): M#G =
  { val res = ev.newImut(length)
    iForeach((a, i) => ev.setImut(res, i, f(a)))
    res
  }
}

trait ArrT[A]
{ type G <: ImutArr[A]
  type H <: BuffArr[A]
  type J <: MutArr[A]
}

trait ImutArr[A] extends Any with BaseArr[A]
trait BuffArr[A] extends Any with BaseArr[A]
trait MutArr[A] extends Any with BaseArr[A]

trait ArrBuilder[B, M <: ArrT[B]]
{ def newImut(length: Int): M#G
  def newBuff(length: Int = 4): M#H
  def newMut(length: Int): M#J
  def setImut(arr: M#G, index: Int, value: B): Unit
}

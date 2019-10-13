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

  /** foreach with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach[U](f: (A, Int) => U, startIndex: Int = 0): Unit =
  { val endIndex = length + startIndex
    var i: Int = startIndex
    while(i < endIndex ) { f(apply(i), i); i = i + 1 }
  }

  def map[B, M <: ArrT[B]](f: A => B)(ev: ArrBuilder[B, M]): M#G =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def flatMap[B, M <: ArrT[B]](f: A => M#G)(ev: ArrBuilder[B, M]): M#G =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffAppends(buff, f(a)))
    ev.buffImut(buff)
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
{ def imutNew(length: Int): M#G
  def imutSet(arr: M#G, index: Int, value: B): Unit
  def buffNew(length: Int = 4): M#H
  def buffAppend(buff: M#H, value: B): Unit
  def buffAppends(buff: M#H, values: M#G): Unit
  def buffImut(buff: M#H): M#G
  def mutNew(length: Int): M#J
}

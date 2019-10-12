package ostrat

trait BaseArr[A] extends Any
{ def length: Int
  def apply(index: Int): A
  def foreach[U](f: A => U): Unit =
  {
    var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }
  //def map[B, ](f: )
}


trait ImutArr[A] extends Any with BaseArr[A]
trait BuffArr[A] extends Any with BaseArr[A]
trait MutArr[A] extends Any with BaseArr[A]

trait ArrBuilder[B, K <: ImutArr[B], L <: BuffArr[B], M <: MutArr[B]]
{ def newImut(length: Int): K
  def newBuff(length: Int = 0): L
  def newMut(length: Int): M
  def setImut(arr: K, index: Int, value: B): Unit
}
